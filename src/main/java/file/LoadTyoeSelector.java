package file;

import components.loadTypeA;
import components.loadTypeB;
import components.loadTypeC;

public class LoadTyoeSelector {
    public LoadTypes getLoader(Entities entity) {
        return switch (entity) {
            case TYPEA -> {yield new loadTypeA();}
            case TYPEB -> {yield  new loadTypeB();}
            case TYPEC -> {yield  new loadTypeC();}
        };
    }
}
