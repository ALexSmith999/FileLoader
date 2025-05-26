import components.*;
import file.Validations;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ValidationsTests {
    static Validations valA;
    static Validations valB;
    static Validations valC;
    @BeforeAll
    static void setup() {
        valA = new ValidationA();
        valB = new ValidationB();
        valC = new ValidationC();
    }

    @DisplayName("fails if conditions for A are not met")
    @Test
    public void assertValidationAfails() {
        assertAll(
                () -> assertFalse(valA.isValidRow(""))
                ,() -> assertFalse(valA.isValidRow(" "))
                ,() -> assertFalse(valA.isValidRow("asa dsd"))
                ,() -> assertFalse(valA.isValidRow("asa"))
                ,() -> assertFalse(valA.isValidRow("asa   "))
                ,() -> assertFalse(valA.isValidRow("asa ddd  "))
                ,() -> assertFalse(valA.isValidRow(" asa ddd  "))
        );
    }

    @DisplayName("succeeds otherwise for A")
    @Test
    public void assertValidationAsucceded() {
        assertAll(
                () -> assertTrue(valA.isValidRow("asasx ascasc asxasx"))
                , () -> assertTrue(valA.isValidRow("asasx ascasc asxasx sdc"))
                , () -> assertTrue(valA.isValidRow(" asa ddd ggg "))
                , () -> assertTrue(valA.isValidRow(" asa ddd ggg ggg jjj "))
                , () -> assertTrue(valA.isValidRow(" asa ddd ggg  ggg  jjj "))
        );
    }

    @DisplayName("fails if conditions for B are not met")
    @Test
    public void assertValidationBfails() {
        assertAll(
                () -> assertFalse(valB.isValidRow(""))
                ,() -> assertFalse(valB.isValidRow(" "))
                ,() -> assertFalse(valB.isValidRow("asa dsd"))
                ,() -> assertFalse(valB.isValidRow("asa"))
                ,() -> assertFalse(valB.isValidRow("asa     "))
                ,() -> assertFalse(valB.isValidRow("asa ddd    "))
                ,() -> assertFalse(valB.isValidRow("asa  ddd dfdfd sds sdsd"))
                ,() -> assertFalse(valB.isValidRow("asa ddd  dfdfd sds sdsd"))
                ,() -> assertFalse(valB.isValidRow("asa ddd dfdfd  sds sdsd"))
                ,() -> assertFalse(valB.isValidRow("asa ddd dfdfd sds  sdsd"))
        );
    }

    @DisplayName("succeeds otherwise for B")
    @Test
    public void assertValidationBsucceded() {
        assertAll(
                () -> assertTrue(valB.isValidRow("asasx ascasc asxasx asasx ascasc"))
                , () -> assertTrue(valB.isValidRow(" asasx ascasc asxasx asasx ascasc "))
                , () -> assertTrue(valB.isValidRow("asasx ascasc asxasx sdc asasx ascasc"))
                , () -> assertTrue(valB.isValidRow(" asasx ascasc asxasx sdc asasx ascasc "))
                , () -> assertTrue(valB.isValidRow(" asasx ascasc asxasx sdc asasx  d  ascasc   "))
        );
    }

    @DisplayName("fails if conditions for C are not met")
    @Test
    public void assertValidationCfails() {
        assertAll(
                () -> assertFalse(valC.isValidRow(""))
                ,() -> assertFalse(valC.isValidRow(" "))
                ,() -> assertFalse(valC.isValidRow("asa dsd"))
                ,() -> assertFalse(valC.isValidRow("asa"))
                ,() -> assertFalse(valC.isValidRow("asa     "))
                ,() -> assertFalse(valC.isValidRow("asa ddd ggg  "))
                ,() -> assertFalse(valC.isValidRow("asa  ddd dfdfd dfdfd "))
                ,() -> assertFalse(valC.isValidRow("asa ddd  dfdfd dfdfd"))
                ,() -> assertFalse(valC.isValidRow("asa ddd dfdfd  dfdfd"))
        );
    }

    @DisplayName("succeeds otherwise for C")
    @Test
    public void assertValidationCsucceeds() {
        assertAll(
                () -> assertTrue(valC.isValidRow("asasx ascasc asxasx asasx"))
                , () -> assertTrue(valC.isValidRow("asasx ascasc asxasx asasx "))
                , () -> assertTrue(valC.isValidRow(" asasx ascasc asxasx asasx"))
                , () -> assertTrue(valC.isValidRow(" asasx ascasc asxasx asasx ascasc"))
                , () -> assertTrue(valC.isValidRow("asasx ascasc asxasx sdc asasx "))
                , () -> assertTrue(valC.isValidRow(" asasx ascasc asxasx sdc a  sasx   ascasc "))
        );
    }
}
