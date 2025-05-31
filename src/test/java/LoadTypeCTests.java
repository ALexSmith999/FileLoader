import components.ParserC;
import components.ValidationC;
import components.loadTypeA;
import components.loadTypeC;
import database.BatchC;
import database.DatabaseStatementsTypeC;
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

public class LoadTypeCTests {
    private static final int BATCH_SIZE = 20000;
    private static final String CORRECT_FILE = "src/test/resources/typeC.txt";
    private static final String INCORRECT_FILE = "src/test/resources/typeC_incorrect.txt";
    private static final String PARTIAL_FILE = "src/test/resources/typeC_partial.txt";

    @BeforeAll
    static void setup() throws SQLException {
        H2databasePlug.initializeDatabase();
    }

    @BeforeEach
    void setUop(){
        try (Connection conn = H2databasePlug.getConnection();){
            PreparedStatement stmnt = conn.prepareStatement("truncate table typeC");
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void assertLoadsIntoDBforTypeC (){
        try (BufferedReader buffer = new BufferedReader(new FileReader(CORRECT_FILE));
             Connection conn = H2databasePlug.getConnection();
             PreparedStatement stmnt = conn.prepareStatement("SELECT ID, name, surname, city FROM typeC WHERE ID = ?")
        ) {
            String line;
            while ((line = buffer.readLine()) != null) {

                String[] parts = line.trim().split(" ");
                String id = parts[0];
                String expectedName = parts[1];
                String expectedSurname = parts[2];
                String expectedCity = parts[3];

                stmnt.setString(1, id);
                try (ResultSet rs = stmnt.executeQuery()) {
                    assertAll(
                            () -> assertTrue(rs.next(), "Expected a row for ID: " + id),
                            () -> assertEquals(id, rs.getString("ID"), "Mismatch for date at ID: " + id),
                            () -> assertEquals(expectedName, rs.getString("name"), "Mismatch for name at ID: " + id),
                            () -> assertEquals(expectedSurname, rs.getString("surname"), "Mismatch for surname at ID: " + id),
                            () -> assertEquals(expectedCity, rs.getString("city"), "Mismatch for city at ID: " + id)
                    );
                }
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void LoadTypeCSucceedstest () throws SQLException {
        try (Connection conn = H2databasePlug.getConnection();
        ) {
            LoadRequest request = new LoadRequest(conn, Path.of(CORRECT_FILE), BATCH_SIZE);
            loadTypeC currentFile = new loadTypeC.Builder()
                    .withDatabase(new DatabaseStatementsTypeC())
                    .withValidation(new ValidationC())
                    .withParser(new ParserC())
                    .withBatch(new BatchC())
                    .withRequest(request)
                    .Build();
            currentFile.fulfillRequest();
            assertLoadsIntoDBforTypeC();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Test
    void LoadTypeCFailstest() throws SQLException {
        try (Connection conn = H2databasePlug.getConnection()) {

            LoadRequest request = new LoadRequest(conn, Path.of(INCORRECT_FILE), BATCH_SIZE);
            loadTypeC currentFile = new loadTypeC.Builder()
                    .withDatabase(new DatabaseStatementsTypeC())
                    .withValidation(new ValidationC())
                    .withParser(new ParserC())
                    .withBatch(new BatchC())
                    .withRequest(request)
                    .Build();
            currentFile.fulfillRequest();

            try (PreparedStatement stmnt = conn.prepareStatement("SELECT COUNT(*) AS total FROM typeC");
                 ResultSet rs = stmnt.executeQuery()
            ) {
                assertTrue(rs.next(), "Expected a result row from COUNT query");
                assertEquals(0, rs.getInt("total"), "Expected no rows in typeC after loading incorrect file");
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Test
    void LoadTypeCPartiallyLoadstest() throws SQLException {
        try (Connection conn = H2databasePlug.getConnection()) {

            LoadRequest request = new LoadRequest(conn, Path.of(PARTIAL_FILE), BATCH_SIZE);
            loadTypeC currentFile = new loadTypeC.Builder()
                    .withDatabase(new DatabaseStatementsTypeC())
                    .withValidation(new ValidationC())
                    .withParser(new ParserC())
                    .withBatch(new BatchC())
                    .withRequest(request)
                    .Build();
            currentFile.fulfillRequest();

            try (PreparedStatement stmnt = conn.prepareStatement("SELECT COUNT(*) AS total FROM typeC");
                 ResultSet rs = stmnt.executeQuery()
            ) {
                assertTrue(rs.next(), "Expected a result row from COUNT query");
                assertEquals(2, rs.getInt("total"), "Expected no rows in typeC after loading incorrect file");
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
