package components;

import database.BatchA;
import database.Insertion;
import entry.FileLoaderLaunch;
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
    private LoadRequest request;
    // TO DO : it is required to implement Builder pattern.
    // Must to make the construction of objects transparent and evident in Load Chains
    // SOLID : use dependency ejections instead of instantiating objects inside a particular one
    public loadTypeA(LoadRequest request){
        db = new Insertion();
        validation = new ValidationA();
        parser = new ParserA();
        gatherBatch = new BatchA();
        query = db.returnQuery(Entities.TYPEA);
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
