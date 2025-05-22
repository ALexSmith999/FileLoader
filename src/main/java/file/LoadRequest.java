package file;

import java.nio.file.Path;
import java.sql.Connection;

public class LoadRequest {
    private Connection conn = null;
    private Path path = null;
    private int BATCH_SIZE = 0;

    public LoadRequest(Connection conn, Path path, int BATCH_SIZE) {
        this.conn = conn;
        this.path = path;
        this.BATCH_SIZE = BATCH_SIZE;
    }
    public Connection getConnection () {
        return conn;
    }
    public Path getPath () {
        return path;
    }
    public int getBatchSize () {
        return BATCH_SIZE;
    }
}
