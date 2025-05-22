package file;

import components.LoadChainA;

import java.nio.file.Path;
import java.sql.Connection;

public class LoadChain {
    public void loadFile(Connection conn, Path path, int BATCH_SIZE) {
        LoadRequest request = new LoadRequest(conn, path, BATCH_SIZE);
        RequestHandler first = new LoadChainA();
        first.solveRequest(request);
    }
}
