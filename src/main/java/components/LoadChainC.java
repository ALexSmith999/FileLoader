package components;

import database.BatchC;
import database.DatabaseStatementsTypeC;
import file.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoadChainC extends RequestHandler {
    /*
    Implements the chain of responsibility design pattern.
    If the type of the file matches, the class attempts to complete the request,
    choosing the underlying distinct algorithm to load the file.
    Otherwise, it asks the next solver to complete the request.
    **/
    private static final Logger logger = LogManager.getLogger(LoadChainC.class);
    @Override
    public void solveRequest(LoadRequest request) {
        String fileName = request.getPath().getFileName().toString();
        if (fileName.contains("C")) {
            loadTypeC currentFile = new loadTypeC.Builder()
                    .withDatabase(new DatabaseStatementsTypeC())
                    .withValidation(new ValidationC())
                    .withParser(new ParserC())
                    .withBatch(new BatchC())
                    .withRequest(request)
                    .Build();
            currentFile.fulfillRequest();
        }
        else {
            logger.warn("The file {} will be skipped", request.getPath());
        }
    }
}
