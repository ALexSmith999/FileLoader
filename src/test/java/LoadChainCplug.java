import components.LoadChainB;
import components.LoadChainC;

public class LoadChainCplug extends LoadChainC implements Plug {
    public void solveRequest(String request) {
        if (request.contains("C")) {
            throw new RuntimeException("processed");
        }
    }
}
