public class LoadChainBplug implements Plug{
    public void solveRequest(String request) {
        if (request.contains("B")) {
            throw new RuntimeException("processed");
        }
        else {
            LoadChainCplug next = new LoadChainCplug();
            next.solveRequest(request);
        }
    }
}
