package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RalsStaticaster extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPlaneswalkerPermanent(SubType.RAL, "you control a Ral planeswalker")
    );

    public RalsStaticaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Ral's Staticaster attacks, if you control a Ral planeswalker, Ral's Staticaster gets +1/+0 for each card in your hand until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(
                CardsInControllerHandCount.ANY, StaticValue.get(0), Duration.EndOfTurn
        ).setText("this creature gets +1/+0 for each card in your hand until end of turn")).withInterveningIf(condition));
    }

    private RalsStaticaster(final RalsStaticaster card) {
        super(card);
    }

    @Override
    public RalsStaticaster copy() {
        return new RalsStaticaster(this);
    }
}
