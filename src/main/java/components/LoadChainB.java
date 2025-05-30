package components;

import file.*;

public class LoadChainB extends RequestHandler {
    /*
    Implements the chain of responsibility design pattern.
    If the type of the file matches, the class attempts to complete the request,
    choosing the underlying distinct algorithm to load the file.
    Otherwise, it asks the next solver to complete the request.
    **/
    @Override
    public void solveRequest(LoadRequest request) {
        String fileName = request.getPath().getFileName().toString();
        if (fileName.contains("B")) {
            loadTypeB currentFile = new loadTypeB(request);
            currentFile.loadTheFile();
        }
        else {
            RequestHandler next = new LoadChainC();
            next.solveRequest(request);
        }
    }
}
