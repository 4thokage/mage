package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.TributeNotPaidCondition;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.TributeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PharagaxGiant extends CardImpl {

    public PharagaxGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Tribute 2 (As this creature enters the battlefield, an opponent of your choice may place two +1/+1 counters on it.)
        this.addAbility(new TributeAbility(2));

        // When Pharagax Giant enters the battlefield, if tribute wasn't paid, Pharagax Giant deals 5 damage to each opponent.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DamagePlayersEffect(5, TargetController.OPPONENT)
        ).withInterveningIf(TributeNotPaidCondition.instance));
    }

    private PharagaxGiant(final PharagaxGiant card) {
        super(card);
    }

    @Override
    public PharagaxGiant copy() {
        return new PharagaxGiant(this);
    }
}
