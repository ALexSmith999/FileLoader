import components.LoadChainA;

public class LoadChainAplug extends LoadChainA implements Plug{
    public void solveRequest(String request) {
        if (request.contains("A")) {
            throw new RuntimeException("processed");
        }
        else {
            LoadChainBplug next = new LoadChainBplug();
            next.solveRequest(request);
        }
    }
}
