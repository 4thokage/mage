package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardTargetControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_ANOTHER_TARGET_CREATURE;

/**
 * @author LevelX2
 */
public final class TriadOfFates extends CardImpl {

    private static final FilterCreaturePermanent filterCounter = new FilterCreaturePermanent("creature that has a fate counter on it");

    static {
        filterCounter.add(CounterType.FATE.getPredicate());
    }

    public TriadOfFates(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {1}, {T}: Put a fate counter on another target creature.
        Ability ability = new SimpleActivatedAbility(new AddCountersTargetEffect(CounterType.FATE.createInstance()), new ManaCostsImpl<>("{1}"));
        ability.addCost(new TapSourceCost());
        Target target = new TargetPermanent(FILTER_ANOTHER_TARGET_CREATURE);
        ability.addTarget(target);
        this.addAbility(ability);

        // {W}, {T}: Exile target creature that has a fate counter on it, then return it to the battlefield under its owner's control.
        ability = new SimpleActivatedAbility(new ExileThenReturnTargetEffect(false, false), new ManaCostsImpl<>("{W}"));
        ability.addCost(new TapSourceCost());
        target = new TargetPermanent(filterCounter);
        ability.addTarget(target);
        this.addAbility(ability);

        // {B}, {T}: Exile target creature that has a fate counter on it. Its controller draws two cards.
        ability = new SimpleActivatedAbility(new ExileTargetEffect(), new ManaCostsImpl<>("{B}"));
        ability.addCost(new TapSourceCost());
        target = new TargetPermanent(filterCounter);
        ability.addTarget(target);
        ability.addEffect(new DrawCardTargetControllerEffect(2));
        this.addAbility(ability);
    }

    private TriadOfFates(final TriadOfFates card) {
        super(card);
    }

    @Override
    public TriadOfFates copy() {
        return new TriadOfFates(this);
    }
}
