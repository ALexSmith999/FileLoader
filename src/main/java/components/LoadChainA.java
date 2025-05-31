package components;

import database.BatchA;
import database.DatabaseStatementsTypeA;
import file.*;

public class LoadChainA extends RequestHandler {
    /*
    Implements the chain of responsibility design pattern.
    If the type of the file matches, the class attempts to complete the request,
    choosing the underlying distinct algorithm to load the file.
    Otherwise, it asks the next solver to complete the request.
    **/
    @Override
    public void solveRequest(LoadRequest request) {
        String fileName = request.getPath().getFileName().toString();
        if (fileName.contains("A")) {
            loadTypeA currentFile = new loadTypeA.Builder()
                    .withDatabase(new DatabaseStatementsTypeA())
                    .withValidation(new ValidationA())
                    .withParser(new ParserA())
                    .withBatch(new BatchA())
                    .withRequest(request)
                    .Build();
            currentFile.fulfillRequest();
        }
        else {
            RequestHandler next = new LoadChainB();
            next.solveRequest(request);
        }
    }
}
