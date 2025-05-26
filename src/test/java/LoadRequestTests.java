import file.LoadRequest;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
class LoadRequestTests {

    @Test
    void testLoadRequestInitializationAndGetters() {
        Path mockPath = Path.of("src/test/resources/typeA.txt");
        int batchSize = 1000;

        try (Connection conn = H2databasePlug.getConnection();
        ){
            LoadRequest request = new LoadRequest(conn, mockPath, batchSize);
            assertAll(
                    () -> assertSame(conn, request.getConnection(), "Connection should match the one passed to constructor"),
                    () -> assertEquals(mockPath, request.getPath(), "Path should match the one passed to constructor"),
                    () -> assertEquals(batchSize, request.getBatchSize(), "Batch size should match the one passed to constructor"),
                    () -> assertInstanceOf(LoadRequest.class, request, "Object should be an instance of LoadRequest")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

