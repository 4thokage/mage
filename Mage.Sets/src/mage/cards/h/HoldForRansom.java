package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantAttackBlockAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author weirddan455
 */
public final class HoldForRansom extends CardImpl {

    public HoldForRansom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Removal));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature can't attack or block and has "{7}: Hold for Ransom's controller sacrifices it and draws a card. Activate only as a sorcery."
        Ability ability = new SimpleStaticAbility(new CantAttackBlockAttachedEffect(AttachmentType.AURA));
        ability.addEffect(new HoldForRansomGainEffect());
        this.addAbility(ability);
    }

    private HoldForRansom(final HoldForRansom card) {
        super(card);
    }

    @Override
    public HoldForRansom copy() {
        return new HoldForRansom(this);
    }
}

class HoldForRansomGainEffect extends GainAbilityAttachedEffect {

    HoldForRansomGainEffect() {
        super(new ActivateAsSorceryActivatedAbility(new HoldForRansomSacrificeEffect(), new GenericManaCost(7)), AttachmentType.AURA);
        this.staticText = "and has \"" + ability.getRule() + '"';
    }

    private HoldForRansomGainEffect(final HoldForRansomGainEffect effect) {
        super(effect);
    }

    @Override
    public HoldForRansomGainEffect copy() {
        return new HoldForRansomGainEffect(this);
    }

    @Override
    public void afterGain(Game game, Ability source, Permanent permanent, Ability addedAbility) {
        Permanent aura = game.getPermanent(source.getSourceId());
        addedAbility.getEffects().setTargetPointer(new FixedTarget(aura == null ? null : aura.getId(), game));
    }
}

class HoldForRansomSacrificeEffect extends OneShotEffect {

    HoldForRansomSacrificeEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "{this}'s controller sacrifices it and draws a card";
    }

    private HoldForRansomSacrificeEffect(final HoldForRansomSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public HoldForRansomSacrificeEffect copy() {
        return new HoldForRansomSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent aura = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (aura == null) {
            return false;
        }
        Player auraController = game.getPlayer(aura.getControllerId());
        aura.sacrifice(source, game);
        if (auraController != null) {
            auraController.drawCards(1, source, game);
        }
        return true;
    }
}
