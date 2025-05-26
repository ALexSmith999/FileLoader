import database.Insertion;
import file.Entities;
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

public class BatchATests {
    Insertion ins;
    @BeforeAll
    static void setup() throws SQLException {
        H2databasePlug.initializeDatabase();
    }
    @BeforeEach
    void setUop(){
        ins = new Insertion();
        try (Connection conn = H2databasePlug.getConnection();){
            PreparedStatement stmnt = conn.prepareStatement("truncate table typeA");
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void assertBatchAisGathered() {
        List<List<String>> arr = new ArrayList<>();
        String query = ins.returnQuery(Entities.TYPEA);

        String row = "3 13/04/2023 4454";
        String row1 = "1 12/03/2024 1234";
        String row2 = "2 12/03/2024 43454";
        String row3 = "4 11/05/2024 100";

        arr.add(List.of(row.split(" ")));
        arr.add(List.of(row1.split(" ")));
        arr.add(List.of(row2.split(" ")));
        arr.add(List.of(row3.split(" ")));

        try (Connection conn = H2databasePlug.getConnection();
             PreparedStatement stmnt = conn.prepareStatement(query);
             PreparedStatement countStmt = conn.prepareStatement("SELECT COUNT(*) FROM typeA");

        ){
            for (List<String> currentClient : arr) {
                stmnt.setString(1, currentClient.get(0));
                stmnt.setString(2, currentClient.get(1));
                stmnt.setString(3, currentClient.get(2));
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
