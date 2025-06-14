package mage.cards.r;

import mage.abilities.condition.common.OpponentHasNoCardsInHandCondition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class RekindledFlame extends CardImpl {

    private static final Hint hint = new ConditionHint(
            OpponentHasNoCardsInHandCondition.instance, "An opponent has no cards in hand"
    );

    public RekindledFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{R}");

        // Rekindled Flame deals 4 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // At the beginning of your upkeep, if an opponent has no cards in hand, you may return Rekindled Flame from your graveyard to your hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                Zone.GRAVEYARD, TargetController.YOU, new ReturnSourceFromGraveyardToHandEffect(), true
        ).withInterveningIf(OpponentHasNoCardsInHandCondition.instance).addHint(hint));
    }

    private RekindledFlame(final RekindledFlame card) {
        super(card);
    }

    @Override
    public RekindledFlame copy() {
        return new RekindledFlame(this);
    }
}
