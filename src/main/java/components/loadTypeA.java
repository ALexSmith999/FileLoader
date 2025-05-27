package components;

import database.BatchA;
import database.Insertion;
import entry.FileLoaderLaunch;
import file.Entities;
import file.LoadTypes;
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

public class loadTypeA implements LoadTypes {
    /*
    Each file has its own loading algorithm.
    The algorithm is implemented in a way that is flexible, independent, and extensible.
    Nevertheless, each algorithm has the next common intermediate steps:
    - A File Validation
    - A File Parsing
    - Load into a predefined database
    **/

    private Insertion db = null;
    private ValidationA validation = null;
    private ParserA parser = null;
    private BatchA gatherBatch = null;
    private String query = "";
    private static final Logger logger = LogManager.getLogger(loadTypeA.class);

    public loadTypeA(){
        db = new Insertion();
        validation = new ValidationA();
        parser = new ParserA();
        gatherBatch = new BatchA();
        query = db.returnQuery(Entities.TYPEA);
    }

    @Override
    public void loadFile(Connection conn, Path path, int batchSize) {
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
