package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LilianasContract extends CardImpl {

    public LilianasContract(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");

        // When Liliana's Contract enters the battlefield, you draw four cards and you lose 4 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(4, true));
        ability.addEffect(new LoseLifeSourceControllerEffect(4).setText("and you lose 4 life"));
        this.addAbility(ability);

        // At the beginning of your upkeep, if you control four or more Demons with different names, you win the game.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new WinGameSourceControllerEffect())
                .withInterveningIf(LilianasContractCondition.instance));
    }

    private LilianasContract(final LilianasContract card) {
        super(card);
    }

    @Override
    public LilianasContract copy() {
        return new LilianasContract(this);
    }
}

enum LilianasContractCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Set<String> demonNames = new HashSet<>();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
            if (permanent == null
                    || !permanent.isControlledBy(source.getControllerId())
                    || !permanent.hasSubtype(SubType.DEMON, game)) {
                continue;
            }
            demonNames.add(permanent.getName());
            if (demonNames.size() > 3) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "you control four or more Demons with different names";
    }
}
