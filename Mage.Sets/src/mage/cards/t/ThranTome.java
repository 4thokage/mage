package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author arcox
 */
public final class ThranTome extends CardImpl {

    public ThranTome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Reveal the top three cards of your library. Target opponent chooses one of those cards. Put that card into your graveyard, then draw two cards.
        Ability ability = new SimpleActivatedAbility(new ThranTomeEffect(), new ManaCostsImpl<>("{5}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private ThranTome(final ThranTome card) {
        super(card);
    }

    @Override
    public ThranTome copy() {
        return new ThranTome(this);
    }
}

class ThranTomeEffect extends OneShotEffect {

    ThranTomeEffect() {
        super(Outcome.Benefit);
        this.staticText = "Reveal the top three cards of your library. Target opponent chooses one of those cards. Put that card into your graveyard, then draw two cards";
    }

    private ThranTomeEffect(final ThranTomeEffect effect) {
        super(effect);
    }

    @Override
    public ThranTomeEffect copy() {
        return new ThranTomeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // validate opponent and controller exist
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));

        if (opponent == null || controller == null) {
            return false;
        }

        // reveal the cards and choose one. put it in the graveyard
        Card cardToGraveyard;
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 3));
        controller.revealCards(source, cards, game);

        if (cards.size() == 1) {
            cardToGraveyard = cards.getRandom(game);
        } else {
            TargetCardInLibrary target = new TargetCardInLibrary(1, new FilterCard());
            opponent.chooseTarget(outcome, cards, target, source, game);
            cardToGraveyard = game.getCard(target.getFirstTarget());
        }

        // put the chosen card in the graveyard
        if (cardToGraveyard != null) {
            controller.moveCards(cardToGraveyard, Zone.GRAVEYARD, source, game);
            cards.remove(cardToGraveyard);
        }

        // draw 2
        controller.drawCards(2, source, game);
        return true;
    }
}
