import components.LoadChainC;

public class LoadChainCplug implements Plug {
    public void solveRequest(String request) {
        if (request.contains("C")) {
            throw new RuntimeException("processed");
        }
    }
}
