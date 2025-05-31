package components;

import database.BatchC;
import database.DatabaseStatementsTypeC;
import file.LoadRequest;
import file.LoadTypes;

public class loadTypeC implements LoadTypes {
    /*
    Each file has its own loading algorithm.
    The algorithm is implemented in a way that is flexible, independent, and extensible.
    Nevertheless, each algorithm has the next common intermediate steps:
    - A File Validation
    - A File Parsing
    - Load into a predefined database
    **/

    private final DatabaseStatementsTypeC db;
    private final ValidationC validation;
    private final ParserC parser;
    private final BatchC batch;
    private final LoadRequest request;

    public loadTypeC(Builder builder){
        this.db = builder.db;
        this.validation = builder.validation;
        this.parser = builder.parser;
        this.batch = builder.batch;
        this.request = builder.request;
    }
    public static class Builder{

        private DatabaseStatementsTypeC db;
        private ValidationC validation;
        private ParserC parser;
        private BatchC batch;
        private LoadRequest request;

        public Builder withDatabase(DatabaseStatementsTypeC db) {
            this.db = db;
            return this;
        }
        public Builder withValidation(ValidationC validation) {
            this.validation = validation;
            return this;
        }
        public Builder withParser(ParserC parser) {
            this.parser = parser;
            return this;
        }
        public Builder withBatch(BatchC batch) {
            this.batch = batch;
            return this;
        }
        public Builder withRequest(LoadRequest request) {
            this.request = request;
            return this;
        }
        public loadTypeC Build (){
            if (db == null || validation == null
                    || parser == null || batch == null || request == null) {
                throw new IllegalStateException("All dependencies should provided");
            }
            return new loadTypeC(this);
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


