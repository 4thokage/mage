package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.BlocksCreatureTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Hiddevb
 */
public final class HighRiseSawjack extends CardImpl {

    public HighRiseSawjack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever High-Rise Sawjack blocks a creature with flying, High-Rise Sawjack gets +2/+0 until end of turn.
        this.addAbility(new BlocksCreatureTriggeredAbility(new BoostSourceEffect(2, 0, Duration.EndOfTurn), StaticFilters.FILTER_CREATURE_FLYING, false));
    }

    private HighRiseSawjack(final HighRiseSawjack card) {
        super(card);
    }

    @Override
    public HighRiseSawjack copy() {
        return new HighRiseSawjack(this);
    }
}
