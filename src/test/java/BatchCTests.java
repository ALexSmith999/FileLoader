import database.DatabaseStatementsTypeC;
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

public class BatchCTests {
    DatabaseStatementsTypeC ins;
    @BeforeAll
    static void setup() throws SQLException {
        H2databasePlug.initializeDatabase();
    }
    @BeforeEach
    void setUop(){
        ins = new DatabaseStatementsTypeC();
        try (Connection conn = H2databasePlug.getConnection();){
            PreparedStatement stmnt = conn.prepareStatement("truncate table typeC");
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void assertBatchBisGathered() {
        List<List<String>> arr = new ArrayList<>();
        String query = ins.returnBaseInsert();

        String row = "1 Steve Jobes NewOrlean";
        String row1 = "2 John Smith NewYork";
        String row2 = "3 Samantha Fox LasVegas";
        String row3 = "4 Chirstina Richy Milan";

        arr.add(List.of(row.split(" ")));
        arr.add(List.of(row1.split(" ")));
        arr.add(List.of(row2.split(" ")));
        arr.add(List.of(row3.split(" ")));

        try (Connection conn = H2databasePlug.getConnection();
             PreparedStatement stmnt = conn.prepareStatement(query);
             PreparedStatement countStmt = conn.prepareStatement("SELECT COUNT(*) FROM typeC");
        ){
            for (List<String> currentClient : arr) {
                stmnt.setString(1, currentClient.get(0));
                stmnt.setString(2, currentClient.get(1));
                stmnt.setString(3, currentClient.get(2));
                stmnt.setString(4, currentClient.get(3));
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

