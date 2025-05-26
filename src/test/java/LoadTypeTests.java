import components.loadTypeA;
import file.LoadTypes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoadTypeTests {
    static int BatchSize;
    @BeforeAll
    static void setup() throws SQLException {
        H2databasePlug.initializeDatabase();
        BatchSize = 20000;
    }

    @Nested
    class loadType {
        @Test
        void LoadTypeAtest () throws SQLException {
            try (Connection conn = H2databasePlug.getConnection();
            ) {
                LoadTypes load = new loadTypeA();
                load.loadFile(conn, Path.of("src/test/resources/typeA.txt"), BatchSize);
            } catch (SQLException e) {
                throw new SQLException(e);
            }
        }
        @Test
        void LoadTypeAresultstest () throws SQLException {
            try (Connection conn = H2databasePlug.getConnection();
                 Statement stmt = conn.createStatement();
            ) {
                ResultSet rs = stmt.executeQuery("SELECT date, amount FROM typeA WHERE ID = 3");
                assertAll(
                        () -> assertTrue(rs.next())
                        , () -> assertEquals("13/04/2023", rs.getString("date"))
                        , () -> assertEquals("4454", rs.getString("amount"))
                );
                ResultSet rs1 = stmt.executeQuery("SELECT date, amount FROM typeA WHERE ID = 1");
                assertAll(
                        () -> assertTrue(rs1.next())
                        , () -> assertEquals("12/03/2024", rs1.getString("date"))
                        , () -> assertEquals("1234", rs1.getString("amount"))
                );
                ResultSet rs2 = stmt.executeQuery("SELECT date, amount FROM typeA WHERE ID = 2");
                assertAll(
                        () -> assertTrue(rs2.next())
                        , () -> assertEquals("12/03/2024", rs2.getString("date"))
                        , () -> assertEquals("43454", rs2.getString("amount"))
                );
                ResultSet rs3 = stmt.executeQuery("SELECT date, amount FROM typeA WHERE ID = 4");
                assertAll(
                        () -> assertTrue(rs3.next())
                        , () -> assertEquals("11/05/2024", rs3.getString("date"))
                        , () -> assertEquals("100", rs3.getString("amount"))
                );

            } catch (SQLException e) {
                throw new SQLException(e);
            }
        }
    }
}
