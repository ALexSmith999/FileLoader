import components.loadTypeA;
import file.LoadRequest;
import file.LoadTypes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoadTypeATests {
    private static final int BATCH_SIZE = 20000;
    private static final String CORRECT_FILE = "src/test/resources/typeA.txt";
    private static final String INCORRECT_FILE = "src/test/resources/typeA_incorrect.txt";
    private static final String PARTIAL_FILE = "src/test/resources/typeA_partial.txt";

    @BeforeAll
    static void setup() throws SQLException {
        H2databasePlug.initializeDatabase();
    }

    @BeforeEach
    void setUop(){
        try (Connection conn = H2databasePlug.getConnection();){
            PreparedStatement stmnt = conn.prepareStatement("truncate table typeA");
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void assertLoadsIntoDBforTypeA (){
        try (BufferedReader buffer = new BufferedReader(new FileReader(CORRECT_FILE));
             Connection conn = H2databasePlug.getConnection();
             PreparedStatement stmnt = conn.prepareStatement("SELECT date, amount FROM typeA WHERE ID = ?")
        ) {
            String line;
            while ((line = buffer.readLine()) != null) {

                String[] parts = line.trim().split(" ");
                String id = parts[0];
                String expectedDate = parts[1];
                String expectedAmount = parts[2];

                stmnt.setString(1, id);
                try (ResultSet rs = stmnt.executeQuery()) {
                    assertAll(
                            () -> assertTrue(rs.next(), "Expected a row for ID: " + id),
                            () -> assertEquals(expectedDate, rs.getString("date"), "Mismatch for date at ID: " + id),
                            () -> assertEquals(expectedAmount, rs.getString("amount"), "Mismatch for amount at ID: " + id)
                    );
                }
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void LoadTypeASucceedstest () throws SQLException {
        try (Connection conn = H2databasePlug.getConnection();
        ) {
            loadTypeA load = new loadTypeA(new LoadRequest(conn, Path.of(CORRECT_FILE), BATCH_SIZE));
            load.loadTheFile();
            assertLoadsIntoDBforTypeA();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Test
    void LoadTypeFailsAtest() throws SQLException {
        try (Connection conn = H2databasePlug.getConnection()) {
            loadTypeA load = new loadTypeA(new LoadRequest(conn, Path.of(INCORRECT_FILE), BATCH_SIZE));;
            load.loadTheFile();

            try (PreparedStatement stmnt = conn.prepareStatement("SELECT COUNT(*) AS total FROM typeA");
                 ResultSet rs = stmnt.executeQuery()
            ) {
                assertTrue(rs.next(), "Expected a result row from COUNT query");
                assertEquals(0, rs.getInt("total"), "Expected no rows in typeA after loading incorrect file");
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
    @Test
    void LoadTypeAPartiallyLoadstest() throws SQLException {
        try (Connection conn = H2databasePlug.getConnection()) {
            loadTypeA load = new loadTypeA(new LoadRequest(conn, Path.of(PARTIAL_FILE), BATCH_SIZE));
            load.loadTheFile();
            try (PreparedStatement stmnt = conn.prepareStatement("SELECT COUNT(*) AS total FROM typeA");
                 ResultSet rs = stmnt.executeQuery()
            ) {
                assertTrue(rs.next(), "Expected a result row from COUNT query");
                assertEquals(2, rs.getInt("total"), "Expected no rows in typeA after loading incorrect file");
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
