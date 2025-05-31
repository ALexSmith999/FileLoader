import database.DatabaseStatementsTypeB;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BatchBTests {
    DatabaseStatementsTypeB ins;
    @BeforeAll
    static void setup() throws SQLException {
        H2databasePlug.initializeDatabase();
    }
    @BeforeEach
    void setUop(){
        ins = new DatabaseStatementsTypeB();
        try (Connection conn = H2databasePlug.getConnection();){
            PreparedStatement stmnt = conn.prepareStatement("truncate table typeB");
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void assertBatchBisGathered() {
        List<List<String>> arr = new ArrayList<>();
        String query = ins.returnBaseInsert();

        String row = "1 11/05/2024 100 2355 224";
        String row1 = "2 12/03/2024 110 255 2240";
        String row2 = "3 10/08/2024 11011 55 40";
        String row3 = "4 01/02/2024 17891 554 401";

        arr.add(List.of(row.split(" ")));
        arr.add(List.of(row1.split(" ")));
        arr.add(List.of(row2.split(" ")));
        arr.add(List.of(row3.split(" ")));

        try (Connection conn = H2databasePlug.getConnection();
             PreparedStatement stmnt = conn.prepareStatement(query);
             PreparedStatement countStmt = conn.prepareStatement("SELECT COUNT(*) FROM typeB");

        ){
            for (List<String> currentClient : arr) {
                stmnt.setString(1, currentClient.get(0));
                stmnt.setString(2, currentClient.get(1));
                stmnt.setString(3, currentClient.get(2));
                stmnt.setString(4, currentClient.get(3));
                stmnt.setString(5, currentClient.get(4));
                stmnt.addBatch();
            }
            int[] results = stmnt.executeBatch();
            assertEquals(arr.size(), results.length, "Mismatch in batch result length");
            for (int result : results) {
                assertTrue(result >= 0, "Batch execution failed for one or more entries");
            }
            ResultSet rs = countStmt.executeQuery();
            assertTrue(rs.next(), "Result set should contain a row");
            assertEquals(arr.size(), rs.getInt(1), "Mismatch in number of inserted rows");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
