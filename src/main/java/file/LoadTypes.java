package file;

import components.loadTypeA;
import database.Batch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface LoadTypes {
    static final Logger logger = LogManager.getLogger(LoadTypes.class);
    default void loadFile(
              Connection conn
            , Path path
            , int batchSize
            , String query
            , Validations validation
            , Parsers parser
            , Batch gatherBatch) {
        try (BufferedReader buffer = new BufferedReader(new FileReader(path.toFile()));
             PreparedStatement stmnt = conn.prepareStatement(query)
        ) {
            String line;
            int linesCounter = 0;
            conn.setAutoCommit(false);
            while ((line = buffer.readLine()) != null) {
                if (!validation.isValidRow(line)) {
                    logger.warn("The incorrect line number {} in the file {}. Uninterrupted"
                            , linesCounter + 1, path.toString());
                    linesCounter++;
                    continue;
                }
                List<List<String>> lines = parser.parse(line);
                gatherBatch.save(stmnt, lines);
                if (linesCounter >= batchSize) {
                    int [] arr = stmnt.executeBatch();
                    logger.info("The batch consisting of {} rows has been loaded into the database", arr.length);
                    stmnt.clearParameters();
                    conn.commit();
                    linesCounter = 0;
                }
                linesCounter++;
            }
            if (linesCounter > 0) {
                int [] arr = stmnt.executeBatch();
                logger.info("The batch consisting of {} rows has been loaded into the database", arr.length);
                conn.commit();
            }
            conn.setAutoCommit(true);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
