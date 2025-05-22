package components;

import file.*;

public class LoadChainC extends RequestHandler {

    @Override
    public void solveRequest(LoadRequest request) {
        String fileName = request.getPath().getFileName().toString();
        if (fileName.contains("C")) {
            LoadTyoeSelector construct = new LoadTyoeSelector();
            LoadTypes currentFile = construct.getLoader(Entities.TYPEC);
            currentFile.loadFile(request.getConnection(), request.getPath(), request.getBatchSize());
        }
    }
}
