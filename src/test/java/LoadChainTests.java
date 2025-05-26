import components.*;
import file.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class LoadChainTests {
    static LoadChainAplug LoadA;
    static LoadChainBplug LoadB;
    static LoadChainCplug LoadC;

    @BeforeAll
    static void setUp(){
        LoadA = new LoadChainAplug();
        LoadB = new LoadChainBplug();
        LoadC = new LoadChainCplug();
    }

    void assertProcessed(Plug value, String input) {
        RuntimeException e = assertThrows(RuntimeException.class, () -> value.solveRequest(input));
        assertEquals("processed", e.getMessage());
    }

    @DisplayName("A B C are loaded for A")
    @ParameterizedTest
    @ValueSource(strings = {"typeA.txt", "typeB.txt","typeC.txt"})
    void assertAthrowsSucceededTest(String input) {
        assertProcessed(LoadA, input);
    }

    @DisplayName("other types are failed for A")
    @ParameterizedTest
    @ValueSource(strings = {"typeF.txt", "typeG.txt","typeT.txt"})
    void assertAfailsToLoadTest(String input) {
        assertDoesNotThrow(() -> LoadA.solveRequest(input));
    }

    @DisplayName("B C are loaded for B")
    @ParameterizedTest
    @ValueSource(strings = {"typeB.txt","typeC.txt"})
    void assertBthrowsSucceededTest(String input) {
        assertProcessed(LoadB, input);
    }

    @DisplayName("other types are failed for B")
    @ParameterizedTest
    @ValueSource(strings = {"typeA.txt", "typeG.txt","typeT.txt"})
    void assertBfailsToLoadTest(String input) {
        assertDoesNotThrow(() -> LoadB.solveRequest(input));
    }

    @DisplayName("C are loaded for C")
    @ParameterizedTest
    @ValueSource(strings = {"typeC.txt"})
    void assertCthrowsSucceededTest(String input) {
        assertProcessed(LoadC, input);
    }

    @DisplayName("other types are failed for C")
    @ParameterizedTest
    @ValueSource(strings = {"typeA.txt", "typeB.txt","typeT.txt"})
    void assertCfailsToLoadTest(String input) {
        assertDoesNotThrow(() -> LoadC.solveRequest(input));
    }
}