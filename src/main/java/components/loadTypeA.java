package components;

import database.BatchA;
import database.DatabaseStatementsTypeA;
import file.LoadRequest;
import file.LoadTypes;

public class loadTypeA implements LoadTypes {
    /*
    Each file has its own loading algorithm.
    The algorithm is implemented in a way that is flexible, independent, and extensible.
    Nevertheless, each algorithm has the next common intermediate steps:
    - A File Validation
    - A File Parsing
    - Load into a predefined database
    **/
    private final DatabaseStatementsTypeA db;
    private final ValidationA validation;
    private final ParserA parser;
    private final BatchA batch;
    private final LoadRequest request;

    public loadTypeA(Builder builder){
        this.db = builder.db;
        this.validation = builder.validation;
        this.parser = builder.parser;
        this.batch = builder.batch;
        this.request = builder.request;
    }

    public static class Builder {

        private DatabaseStatementsTypeA db;
        private ValidationA validation;
        private ParserA parser;
        private BatchA batch;
        private LoadRequest request;

        public Builder withDatabase(DatabaseStatementsTypeA db){
            this.db = db;
            return this;
        }
        public Builder withValidation(ValidationA validation){
            this.validation = validation;
            return this;
        }
        public Builder withParser(ParserA parser){
            this.parser = parser;
            return this;
        }
        public Builder withBatch(BatchA batch){
            this.batch = batch;
            return this;
        }
        public Builder withRequest(LoadRequest request){
            this.request = request;
            return this;
        }
        public loadTypeA Build(){
            if (db == null || validation == null
                    || parser == null || batch == null || request == null) {
                throw new IllegalStateException("All Dependencies must be provided");
            }
            return new loadTypeA(this);
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
