
package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.HorrorXXBlackToken;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class FleshCarver extends CardImpl {

    public FleshCarver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Intimidate
        this.addAbility(IntimidateAbility.getInstance());
        // {1}{B}, Sacrifice another creature: Put two +1/+1 counters on Flesh Carver.
        Ability ability = new SimpleActivatedAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        this.addAbility(ability);

        // When Flesh Carver dies, create an X/X black Horror creature token, where X is Flesh Carver's power.
        this.addAbility(new FleshCarverAbility());
    }

    private FleshCarver(final FleshCarver card) {
        super(card);
    }

    @Override
    public FleshCarver copy() {
        return new FleshCarver(this);
    }
}

class FleshCarverAbility extends DiesSourceTriggeredAbility {

    public FleshCarverAbility() {
        super(new FleshCarverEffect(), false);
    }

    private FleshCarverAbility(final FleshCarverAbility ability) {
        super(ability);
    }

    @Override
    public FleshCarverAbility copy() {
        return new FleshCarverAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (permanent != null) {
                for (Effect effect : this.getEffects()) {
                    effect.setValue("power", permanent.getPower().getValue());
                }
                return true;
            }
        }
        return false;
    }
}

class FleshCarverEffect extends OneShotEffect {

    FleshCarverEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "create an X/X black Horror creature token, where X is {this}'s power";
    }

    private FleshCarverEffect(final FleshCarverEffect ability) {
        super(ability);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int xValue = (Integer) getValue("power");
            return new CreateTokenEffect(new HorrorXXBlackToken(xValue)).apply(game, source);
        }
        return false;
    }

    @Override
    public FleshCarverEffect copy() {
        return new FleshCarverEffect(this);
    }
}
