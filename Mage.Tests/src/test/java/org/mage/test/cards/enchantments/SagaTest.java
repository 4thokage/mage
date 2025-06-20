package org.mage.test.cards.enchantments;

import mage.abilities.common.SagaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class SagaTest extends CardTestPlayerBase {

    private static final String rite = "Rite of Belzenlok";
    private static final String vorinclex = "Vorinclex, Monstrous Raider";
    private static final String flicker = "Flicker";
    private static final String boomerang = "Boomerang";
    private static final String saga = "Urza's Saga";
    private static final String moon = "Blood Moon";

    @Test
    public void testRiteOfBelzenlok() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.HAND, playerA, rite);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rite);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertCounterCount(rite, CounterType.LORE, 1);
        assertPermanentCount(playerA, "Cleric Token", 2);

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertCounterCount(rite, CounterType.LORE, 2);
        assertPermanentCount(playerA, "Cleric Token", 2 + 2);

        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, rite, 1);
        assertPermanentCount(playerA, rite, 0);
        assertPermanentCount(playerA, "Cleric Token", 2 + 2);
        assertPermanentCount(playerA, "Demon Token", 1);
    }

    @Test
    public void testRiteOfBelzenlokFlicker() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, rite);
        addCard(Zone.HAND, playerA, flicker);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rite);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertCounterCount(rite, CounterType.LORE, 1);
        assertPermanentCount(playerA, "Cleric Token", 2);

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertCounterCount(rite, CounterType.LORE, 2);
        assertPermanentCount(playerA, "Cleric Token", 2 + 2);

        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, flicker, rite);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, rite, 0);
        assertPermanentCount(playerA, rite, 1);
        assertCounterCount(playerA, rite, CounterType.LORE, 1);
        assertPermanentCount(playerA, "Cleric Token", 2 + 2 + 2);
        assertPermanentCount(playerA, "Demon Token", 0);
    }

    @Test
    public void testRiteOfBelzenlokBounced() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, rite);
        addCard(Zone.HAND, playerA, boomerang);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rite);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertCounterCount(rite, CounterType.LORE, 1);
        assertPermanentCount(playerA, "Cleric Token", 2);

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertCounterCount(rite, CounterType.LORE, 2);
        assertPermanentCount(playerA, "Cleric Token", 2 + 2);

        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, boomerang, rite);
        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, rite, 1);
        assertPermanentCount(playerA, rite, 0);
        assertGraveyardCount(playerA, boomerang, 1);
        assertPermanentCount(playerA, "Cleric Token", 2 + 2);
        assertPermanentCount(playerA, "Demon Token", 1);
    }

    @Test
    public void testRiteOfBelzenlokVorinclex() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, vorinclex);
        addCard(Zone.HAND, playerA, rite);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rite);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertCounterCount(rite, CounterType.LORE, 2);
        assertPermanentCount(playerA, "Cleric Token", 2 + 2);

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, rite, 1);
        assertPermanentCount(playerA, rite, 0);
        assertPermanentCount(playerA, "Cleric Token", 2 + 2);
        assertPermanentCount(playerA, "Demon Token", 1);
    }

    @Test
    public void testUrzasSagaThenBloodMoon() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, saga);
        addCard(Zone.HAND, playerA, moon);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, saga);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, saga, 1);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, moon);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, saga, 0);
        assertAbilityCount(playerA, saga, ColorlessManaAbility.class, 1);
        assertAbilityCount(playerA, saga, RedManaAbility.class, 1);
        assertAbilityCount(playerA, saga, SagaAbility.class, 0);
        assertPermanentCount(playerA, moon, 1);
    }

    @Test
    public void testBloodMoonThenUrzasSaga() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, saga);
        addCard(Zone.HAND, playerA, moon);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, moon);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, moon, 1);

        playLand(1, PhaseStep.POSTCOMBAT_MAIN, playerA, saga);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, saga, 0);
        // TODO: This should be 0 but the ability still triggers due to blood moon issues
        // assertAbilityCount(playerA, saga, ColorlessManaAbility.class, 0);
        assertAbilityCount(playerA, saga, RedManaAbility.class, 1);
        assertAbilityCount(playerA, saga, SagaAbility.class, 0);
        assertPermanentCount(playerA, moon, 1);
    }

    @Ignore // TODO: fix this, related to blood moon etb issues
    @Test
    public void testBloodMoonThenUrzasSagaThenBounce() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, saga);
        addCard(Zone.HAND, playerA, moon);
        addCard(Zone.HAND, playerA, boomerang);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, moon);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, moon, 1);

        playLand(1, PhaseStep.POSTCOMBAT_MAIN, playerA, saga);
        // currently fails here, saga should immediately die from SBA but ability triggers anyway
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, boomerang, saga);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, saga, 0);
        assertGraveyardCount(playerA, saga, 1);
        assertHandCount(playerA, saga, 1);
        assertHandCount(playerA, boomerang, 1);
        assertPermanentCount(playerA, moon, 1);
    }

    private static final String triumph = "The Triumph of Anax";
    private static final String memnite = "Memnite";
    private static final String kraken = "Kraken Hatchling";

    @Test
    public void testLoreCounterCount() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, triumph);
        addCard(Zone.BATTLEFIELD, playerA, memnite);
        addCard(Zone.BATTLEFIELD, playerB, kraken);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, triumph);

        addTarget(playerA, memnite);
        checkPT("+1/+0", 1, PhaseStep.BEGIN_COMBAT, playerA, memnite, 1 + 1, 1);
        checkPT("next turn", 2, PhaseStep.BEGIN_COMBAT, playerA, memnite, 1, 1);

        addTarget(playerA, memnite);
        checkPT("+2/+0", 3, PhaseStep.BEGIN_COMBAT, playerA, memnite, 1 + 1 + 1, 1);

        addTarget(playerA, memnite);
        checkPT("+3/+0", 5, PhaseStep.BEGIN_COMBAT, playerA, memnite, 1 + 1 + 1 + 1, 1);

        addTarget(playerA, memnite);
        addTarget(playerA, kraken);

        setStrictChooseMode(true);
        setStopAt(7, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, triumph, 1);
        assertDamageReceived(playerB, kraken, 1);
        assertDamageReceived(playerA, memnite, 0);

    }

}
