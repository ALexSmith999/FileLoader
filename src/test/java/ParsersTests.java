import components.*;
import file.Parsers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParsersTests {

    static Parsers parsA;
    static Parsers parsB;
    static Parsers parsC;

    @BeforeAll
    static void setup() {
        parsA = new ParserA();
        parsB = new ParserB();
        parsC = new ParserC();
    }

    @DisplayName("assert parse operation for A is completed")
    @Test
    public void parseAtest() {
        String word = "aaa ddd fff";
        List<List<String>> res = parsA.parse(word);
        assertAll(
                () -> assertFalse(res.isEmpty()),
                () -> assertEquals("aaa", res.get(0).get(0)),
                () -> assertEquals("ddd", res.get(0).get(1)),
                () -> assertEquals("fff", res.get(0).get(2))
        );
        String word1 = " aaa ddd fff hhh ";
        List<List<String>> res1 = parsA.parse(word1);
        assertAll(
                () -> assertFalse(res1.isEmpty()),
                () -> assertEquals("aaa", res1.get(0).get(0)),
                () -> assertEquals("ddd", res1.get(0).get(1)),
                () -> assertEquals("fff", res1.get(0).get(2))
        );
    }
    @DisplayName("assert parse operation for B is completed")
    @Test
    public void parseBtest() {
        String word = "aaa ddd fff";
        List<List<String>> res = parsB.parse(word);
        assertAll(
                () -> assertFalse(res.isEmpty()),
                () -> assertEquals("aaa", res.get(0).get(0)),
                () -> assertEquals("ddd", res.get(0).get(1)),
                () -> assertEquals("fff", res.get(0).get(2))
        );
        String word1 = " aaa ddd fff hhh ";
        List<List<String>> res1 = parsB.parse(word1);
        assertAll(
                () -> assertFalse(res1.isEmpty()),
                () -> assertEquals("aaa", res1.get(0).get(0)),
                () -> assertEquals("ddd", res1.get(0).get(1)),
                () -> assertEquals("fff", res1.get(0).get(2))
        );
    }
    @DisplayName("assert parse operation for C is completed")
    @Test
    public void parseCtest() {
        String word = "aaa ddd fff";
        List<List<String>> res = parsC.parse(word);
        assertAll(
                () -> assertFalse(res.isEmpty()),
                () -> assertEquals("aaa", res.get(0).get(0)),
                () -> assertEquals("ddd", res.get(0).get(1)),
                () -> assertEquals("fff", res.get(0).get(2))
        );
        String word1 = " aaa ddd fff hhh ";
        List<List<String>> res1 = parsB.parse(word1);
        assertAll(
                () -> assertFalse(res1.isEmpty()),
                () -> assertEquals("aaa", res1.get(0).get(0)),
                () -> assertEquals("ddd", res1.get(0).get(1)),
                () -> assertEquals("fff", res1.get(0).get(2))
        );
    }
}
