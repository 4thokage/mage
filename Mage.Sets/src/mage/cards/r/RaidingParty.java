
package mage.cards.r;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CantBeTargetedSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.FilterStackObject;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author L_J
 */
public final class RaidingParty extends CardImpl {

    private static final FilterStackObject filterWhite = new FilterStackObject("white spells or abilities from white sources");
    private static final FilterControlledPermanent filterOrc = new FilterControlledPermanent(SubType.ORC, "an Orc");

    static {
        filterWhite.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public RaidingParty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Raiding Party can't be the target of white spells or abilities from white sources.
        this.addAbility(new SimpleStaticAbility(new CantBeTargetedSourceEffect(filterWhite, Duration.WhileOnBattlefield)));

        // Sacrifice an Orc: Each player may tap any number of untapped white creatures they control. For each creature tapped this way, that player chooses up to two Plains. Then destroy all Plains that weren't chosen this way by any player.
        this.addAbility(new SimpleActivatedAbility(new RaidingPartyEffect(), new SacrificeTargetCost(filterOrc)));
    }

    private RaidingParty(final RaidingParty card) {
        super(card);
    }

    @Override
    public RaidingParty copy() {
        return new RaidingParty(this);
    }
}

class RaidingPartyEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("untapped white creatures");
    private static final FilterPermanent filter2 = new FilterPermanent("Plains");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(new ColorPredicate(ObjectColor.WHITE));
        filter2.add(SubType.PLAINS.getPredicate());
    }

    RaidingPartyEffect() {
        super(Outcome.Detriment);
        staticText = "Each player may tap any number of untapped white creatures they control. For each creature tapped this way, that player chooses up to two Plains. Then destroy all Plains that weren't chosen this way by any player";
    }

    private RaidingPartyEffect(final RaidingPartyEffect effect) {
        super(effect);
    }

    @Override
    public RaidingPartyEffect copy() {
        return new RaidingPartyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourcePermanent != null) {
            Set<UUID> plainsToSave = new HashSet<>();
            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int countBattlefield = game.getBattlefield().getAllActivePermanents(filter, game.getActivePlayerId(), game).size();
                    int tappedCount = 0;
                    Target untappedCreatureTarget = new TargetPermanent(0, Integer.MAX_VALUE, filter, true);
                    if (player.choose(Outcome.Benefit, untappedCreatureTarget, source, game)) {
                        tappedCount = untappedCreatureTarget.getTargets().size();
                        for (UUID creatureId : untappedCreatureTarget.getTargets()) {
                            Permanent creature = game.getPermanentOrLKIBattlefield(creatureId);
                            if (creature != null) {
                                creature.tap(source, game);
                            }
                        }
                    }
                    if (tappedCount > 0) {
                        Target plainsToSaveTarget = new TargetPermanent(0, tappedCount * 2, filter2, true);
                        if (player.choose(Outcome.Benefit, plainsToSaveTarget, source, game)) {
                            for (UUID plainsId : plainsToSaveTarget.getTargets()) {
                                plainsToSave.add(plainsId);
                                Permanent plains = game.getPermanent(plainsId);
                                if (plains != null) {
                                    game.informPlayers(player.getLogName() + " chose " + plains.getLogName() + " to not be destroyed by " + sourcePermanent.getLogName());
                                }
                            }
                        }
                    }
                }
            }
            for (Permanent plains : game.getBattlefield().getActivePermanents(filter2, source.getControllerId(), source, game)) {
                if (!plainsToSave.contains(plains.getId())) {
                    plains.destroy(source, game, false);
                }
            }
            return true;
        }
        return false;
    }
}
