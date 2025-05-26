import components.loadTypeA;
import components.loadTypeB;
import components.loadTypeC;
import file.Entities;
import file.LoadTypeSelector;
import file.LoadTypes;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class LoadTypeSelectorTests {
    LoadTypeSelector selector = new LoadTypeSelector();
    @Test
    void assertTypesAreChosenCorrectly(){
        assertInstanceOf(loadTypeA.class, selector.getLoader(Entities.TYPEA), "loadTypeA class should be returned");
        assertInstanceOf(loadTypeB.class, selector.getLoader(Entities.TYPEB), "loadTypeB class should be returned");
        assertInstanceOf(loadTypeC.class, selector.getLoader(Entities.TYPEC), "loadTypeC class should be returned");
    }
}
