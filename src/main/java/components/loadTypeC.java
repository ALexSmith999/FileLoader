package components;

import database.BatchC;
import database.Insertion;
import file.Entities;
import file.LoadTypes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class loadTypeC implements LoadTypes {

    private Insertion db = null;
    private ValidationC validation = null;
    private ParserC parser = null;
    private BatchC gatherBatch = null;
    private String query = "";

    public loadTypeC(){
        db = new Insertion();
        validation = new ValidationC();
        parser = new ParserC();
        gatherBatch = new BatchC();
        query = db.returnQuery(Entities.TYPEC);
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
                    continue;
                }
                List<List<String>> lines = parser.parse(line);
                gatherBatch.save(stmnt, lines);
                if (linesCounter >= batchSize) {
                    stmnt.executeBatch();
                    stmnt.clearParameters();
                    conn.commit();
                    linesCounter = 0;
                }
                linesCounter++;
            }
            if (linesCounter > 0) {
                stmnt.executeBatch();
                conn.commit();
            }
            conn.setAutoCommit(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


