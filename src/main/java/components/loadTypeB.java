package components;

import database.BatchB;
import database.Insertion;
import file.Entities;
import file.LoadRequest;
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

public class loadTypeB implements LoadTypes {
    /*
    Each file has its own loading algorithm.
    The algorithm is implemented in a way that is flexible, independent, and extensible.
    Nevertheless, each algorithm has the next common intermediate steps:
    - A File Validation
    - A File Parsing
    - Load into a predefined database
    **/

    private Insertion db = null;
    private ValidationB validation = null;
    private ParserB parser = null;
    private BatchB gatherBatch = null;
    private String query = "";
    private static final Logger logger = LogManager.getLogger(loadTypeB.class);
    private LoadRequest request;

    public loadTypeB(LoadRequest request){
        db = new Insertion();
        validation = new ValidationB();
        parser = new ParserB();
        gatherBatch = new BatchB();
        query = db.returnQuery(Entities.TYPEB);
        this.request = request;
    }
    public void loadTheFile (){
        loadFile(request.getConnection()
                , request.getPath()
                , request.getBatchSize()
                , query
                , validation
                , parser
                , gatherBatch
        );
    }
}

