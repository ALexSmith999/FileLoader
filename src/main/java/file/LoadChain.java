package file;

import components.LoadChainA;

import java.nio.file.Path;
import java.sql.Connection;

public class LoadChain {
    /*
    Attempts to load a file using the chain of intertwined classes.
    If the current class is not responsible for loading a defined file,
    the request is passed to the next class, etc...
    **/
    public void loadFile(Connection conn, Path path, int BATCH_SIZE) {
        LoadRequest request = new LoadRequest(conn, path, BATCH_SIZE);
        RequestHandler first = new LoadChainA();
        first.solveRequest(request);
    }
}
