package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author djbrez
 */
public final class Ragamuffyn extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a creature or land");

    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(), CardType.LAND.getPredicate()));
    }

    public Ragamuffyn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Hellbent - {tap}, Sacrifice a creature or land: Draw a card. Activate this ability only if you have no cards in hand.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new DrawCardSourceControllerEffect(1), new TapSourceCost(), HellbentCondition.instance
        );
        ability.addCost(new SacrificeTargetCost(filter));
        this.addAbility(ability.setAbilityWord(AbilityWord.HELLBENT));
    }

    private Ragamuffyn(final Ragamuffyn card) {
        super(card);
    }

    @Override
    public Ragamuffyn copy() {
        return new Ragamuffyn(this);
    }
}
