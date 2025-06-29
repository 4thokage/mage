package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.effects.common.SkipCombatStepEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author ilcartographer
 */
public final class RevenantPatriarch extends CardImpl {

    public RevenantPatriarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Revenant Patriarch enters the battlefield, if {W} was spent to cast it, target player skips their next combat phase.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new SkipCombatStepEffect(Duration.OneUse)
                        .setText("target player skips their next combat phase.")
        ).withInterveningIf(ManaWasSpentCondition.WHITE);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Revenant Patriarch can't block.
        this.addAbility(new CantBlockAbility());
    }

    private RevenantPatriarch(final RevenantPatriarch card) {
        super(card);
    }

    @Override
    public RevenantPatriarch copy() {
        return new RevenantPatriarch(this);
    }
}
