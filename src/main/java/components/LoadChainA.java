package components;

import file.*;

public class LoadChainA extends RequestHandler {

    @Override
    public void solveRequest(LoadRequest request) {
        String fileName = request.getPath().getFileName().toString();
        if (fileName.contains("A")) {
            LoadTyoeSelector construct = new LoadTyoeSelector();
            LoadTypes currentFile = construct.getLoader(Entities.TYPEA);
            currentFile.loadFile(request.getConnection(), request.getPath(), request.getBatchSize());
        }
        else {
            RequestHandler next = new LoadChainB();
            next.solveRequest(request);
        }
    }
}
