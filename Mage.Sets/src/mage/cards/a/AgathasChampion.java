package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.BargainedCondition;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.keyword.BargainAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class AgathasChampion extends CardImpl {

    public AgathasChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Bargain
        this.addAbility(new BargainAbility());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Agatha's Champion enters the battlefield, if it was bargained, it fights up to one target creature you don't control.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new FightTargetSourceEffect().setText("it fights up to one target creature you don't control")
        ).withInterveningIf(BargainedCondition.instance);
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.addAbility(ability);
    }

    private AgathasChampion(final AgathasChampion card) {
        super(card);
    }

    @Override
    public AgathasChampion copy() {
        return new AgathasChampion(this);
    }
}
