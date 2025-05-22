package file;

import java.nio.file.Path;
import java.sql.Connection;

public interface LoadTypes {
    void loadFile (Connection conn, Path path, int batchSize);
}
