package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTargetSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DepartedDeckhand extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(Predicates.not(SubType.SPIRIT.getPredicate()));
    }

    public DepartedDeckhand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.PIRATE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Departed Deckhand becomes the target of a spell, sacrifice it.
        this.addAbility(new BecomesTargetSourceTriggeredAbility(
                new SacrificeSourceEffect(),
                StaticFilters.FILTER_SPELL_A
        ));

        // Departed Deckhand can only be blocked by Spirits.
        Ability ability = new SimpleStaticAbility(
                new CantBeBlockedByCreaturesSourceEffect(
                        filter, Duration.WhileOnBattlefield
                ).setText("{this} can't be blocked except by Spirits")
        );
        this.addAbility(ability);

        // {3}{U}: Target creature you control can only be blocked by Spirits this turn.
        ability = new SimpleActivatedAbility(
                new CantBeBlockedTargetEffect(
                        filter, Duration.EndOfTurn
                ).setText("Another target creature you control can't be blocked this turn except by Spirits"),
                new ManaCostsImpl<>("{3}{U}")
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        this.addAbility(ability);
    }

    private DepartedDeckhand(final DepartedDeckhand card) {
        super(card);
    }

    @Override
    public DepartedDeckhand copy() {
        return new DepartedDeckhand(this);
    }
}
