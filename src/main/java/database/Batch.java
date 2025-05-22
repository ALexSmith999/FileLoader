package database;

import java.sql.PreparedStatement;
import java.util.List;

public interface Batch {
    void save(PreparedStatement stmnt, List<List<String>> arr);
}
