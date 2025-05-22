package components;

import file.*;

public class LoadChainB extends RequestHandler {

    @Override
    public void solveRequest(LoadRequest request) {
        String fileName = request.getPath().getFileName().toString();
        if (fileName.contains("B")) {
            LoadTyoeSelector construct = new LoadTyoeSelector();
            LoadTypes currentFile = construct.getLoader(Entities.TYPEB);
            currentFile.loadFile(request.getConnection(), request.getPath(), request.getBatchSize());
        }
        else {
            RequestHandler next = new LoadChainC();
            next.solveRequest(request);
        }
    }
}
