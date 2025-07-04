
package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.condition.common.HateCondition;
import mage.abilities.dynamicvalue.common.BlockingCreatureCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.BlocksIfAbleTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.LifeLossOtherFromCombatWatcher;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class AsajjVentress extends CardImpl {

    public AsajjVentress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DATHOMIRIAN, SubType.SITH);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Double Strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // When Asajj Ventress becomes blocked, she gets +1/+1 until end of turn for each creature blocking her.
        Effect effect = new BoostSourceEffect(BlockingCreatureCount.SOURCE, BlockingCreatureCount.SOURCE, Duration.EndOfTurn);
        effect.setText("she gets +1/+1 until end of turn for each creature blocking her");
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(effect, false));

        // <i>Hate</i> &mdash; Whenever Asajj Ventress attacks, if an opponent lost life from a source other than combat damage this turn, target creature blocks this turn if able.
        Ability ability = new AttacksTriggeredAbility(new BlocksIfAbleTargetEffect(Duration.EndOfTurn), false)
                .withInterveningIf(HateCondition.instance).setAbilityWord(AbilityWord.HATE);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability, new LifeLossOtherFromCombatWatcher());
    }

    private AsajjVentress(final AsajjVentress card) {
        super(card);
    }

    @Override
    public AsajjVentress copy() {
        return new AsajjVentress(this);
    }
}
