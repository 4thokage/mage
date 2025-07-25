package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class HeartstabberMosquito extends CardImpl {

    public HeartstabberMosquito(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker {2}{B} (You may pay an additional {2}{B} as you cast this spell.)
        this.addAbility(new KickerAbility("{2}{B}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Heartstabber Mosquito enters the battlefield, if it was kicked, destroy target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), false).withInterveningIf(KickedCondition.ONCE);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private HeartstabberMosquito(final HeartstabberMosquito card) {
        super(card);
    }

    @Override
    public HeartstabberMosquito copy() {
        return new HeartstabberMosquito(this);
    }
}
