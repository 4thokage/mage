package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.filter.predicate.permanent.ControllerControlsIslandPredicate;
import mage.game.Game;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author noahg
 */
public final class DwarvenSeaClan extends CardImpl {

    private static final FilterPermanent filter = new FilterAttackingOrBlockingCreature("attacking or blocking creature whose controller controls an Island");

    static {
        filter.add(ControllerControlsIslandPredicate.instance);
    }

    public DwarvenSeaClan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.DWARF);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Choose target attacking or blocking creature whose controller controls an Island. Dwarven Sea Clan deals 2 damage to that creature at end of combat. Activate this ability only before the end of combat step.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new CreateDelayedTriggeredAbilityEffect(new AtTheEndOfCombatDelayedTriggeredAbility(
                        new DamageTargetEffect(2, true, "that creature")
                )).setText("Choose target attacking or blocking creature whose controller controls an Island. " +
                        "{this} deals 2 damage to that creature at end of combat."),
                new TapSourceCost(), BeforeEndCombatCondition.getInstance()
        );
        ability.addTarget(new TargetPermanent(filter));
        addAbility(ability);
    }

    private DwarvenSeaClan(final DwarvenSeaClan card) {
        super(card);
    }

    @Override
    public DwarvenSeaClan copy() {
        return new DwarvenSeaClan(this);
    }
}

class BeforeEndCombatCondition implements Condition {
    private static final BeforeEndCombatCondition instance = new BeforeEndCombatCondition();

    public static Condition getInstance() {
        return instance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        PhaseStep phaseStep = game.getTurnStepType();
        if (phaseStep.getIndex() < PhaseStep.END_COMBAT.getIndex()) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "before the end of combat step";
    }
}
