package components;

import database.BatchB;
import database.DatabaseStatementsTypeB;
import file.LoadRequest;
import file.LoadTypes;

import java.awt.geom.IllegalPathStateException;

public class loadTypeB implements LoadTypes {
    /*
    Each file has its own loading algorithm.
    The algorithm is implemented in a way that is flexible, independent, and extensible.
    Nevertheless, each algorithm has the next common intermediate steps:
    - A File Validation
    - A File Parsing
    - Load into a predefined database
    **/

    private final DatabaseStatementsTypeB db;
    private final ValidationB validation;
    private final ParserB parser;
    private final BatchB batch;
    private final LoadRequest request;

    public loadTypeB(Builder builder){
        this.db = builder.db;
        validation = builder.validation;
        parser = builder.parser;
        batch = builder.batch;
        this.request = builder.request;
    }

    public static class Builder{
        private DatabaseStatementsTypeB db;
        private ValidationB validation;
        private ParserB parser;
        private BatchB batch;
        private LoadRequest request;

        public Builder withDatabase (DatabaseStatementsTypeB db){
            this.db = db;
            return this;
        }
        public Builder withValidation (ValidationB validation){
            this.validation = validation;
            return this;
        }
        public Builder withParser (ParserB parser){
            this.parser = parser;
            return this;
        }
        public Builder withBatch (BatchB batch){
            this.batch = batch;
            return this;
        }

        public Builder withRequest(LoadRequest request) {
            this.request = request;
            return this;
        }
        public loadTypeB Build(){
            if (db == null || validation == null || parser == null
                    || batch == null || request == null) {
                throw new IllegalPathStateException("All Dependencies must be provided");
            }
            return new loadTypeB(this);
        }
    }

    public void fulfillRequest (){
        loadFile(request.getConnection()
                , request.getPath()
                , request.getBatchSize()
                , db.returnBaseInsert()
                , validation
                , parser
                , batch
        );
    }
}

