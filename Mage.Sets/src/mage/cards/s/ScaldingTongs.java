
package mage.cards.s;

import java.util.UUID;
import mage.abilities.TriggeredAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.target.common.TargetOpponentOrPlaneswalker;

/**
 * @author fireshoes
 */
public final class ScaldingTongs extends CardImpl {

    public ScaldingTongs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // At the beginning of your upkeep, if you have three or fewer cards in hand, Scalding Tongs deals 1 damage to target opponent.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new DamageTargetEffect(1));
        ability.addTarget(new TargetOpponentOrPlaneswalker());
        CardsInHandCondition condition = new CardsInHandCondition(ComparisonType.FEWER_THAN, 4);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                ability, condition,
                "At the beginning of your upkeep, if you have three or fewer cards in hand, "
                + "{this} deals 1 damage to target opponent or planeswalker."
        ));
    }

    private ScaldingTongs(final ScaldingTongs card) {
        super(card);
    }

    @Override
    public ScaldingTongs copy() {
        return new ScaldingTongs(this);
    }
}
