package database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BatchC implements Batch {

    @Override
    public void save(PreparedStatement stmnt, List<List<String>> arr) {
        try {
            for (List<String> currentClient : arr) {
                stmnt.setString(1, currentClient.get(0));
                stmnt.setString(2, currentClient.get(1));
                stmnt.setString(3, currentClient.get(2));
                stmnt.setString(4, currentClient.get(3));
                stmnt.addBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
