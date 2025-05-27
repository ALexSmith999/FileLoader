

package entry;

import database.Connection;
import file.LoadChain;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.Map;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static java.nio.file.StandardWatchEventKinds.*;

public class FileLoaderLaunch {

    public static Properties customProperties;
    static {
        customProperties = new Properties();
        try (FileInputStream file =
                     new FileInputStream("src/main/resources/config.properties")) {
            customProperties.load(file);
            Map<String, String> env = System.getenv();
            for (var entry : env.entrySet()) {
                customProperties.putIfAbsent(entry.getKey(), entry.getValue());
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot open the property file or it does not exists", e);
        }
    }
    private static final Logger logger = LogManager.getLogger(FileLoaderLaunch.class);
    public static void main(String[] args) throws IOException {

        int BATCH_SIZE = Integer.parseInt(customProperties.getProperty(ProjectProperties.BATCH_SIZE.label));
        Path dir = Path.of(customProperties.getProperty(ProjectProperties.FILES_DIRECTORY.label));
        WatchService watch = FileSystems.getDefault().newWatchService();
        LoadChain load = new LoadChain();
        java.sql.Connection conn = Connection.returnInstance().connect();
        Initializer.showInfo(customProperties);

        try {
            //WatchKey key = dir.register(watch, ENTRstandardEventsArrayY_CREATE, ExtendedWatchEventModifier.FILE_TREE);
            WatchKey key = dir.register(watch, ENTRY_CREATE);
        }
        catch (IOException e) {
            logger.warn("Failed to register the watcher service", e);
            throw new RuntimeException("Failed to register the watcher service", e);
        }

        while (true) {
            WatchKey key;
            try {
                key = watch.take();
                for (WatchEvent<?> event: key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    if (kind == OVERFLOW) {
                        continue;
                    }
                    WatchEvent<Path> ev = (WatchEvent<Path>)event;
                    Path filename = ev.context();
                    Path child = dir.resolve(filename);
                    logger.warn("The file {} is ready to be loaded", filename.toString());
                    load.loadFile(conn, child, BATCH_SIZE);
                }
                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
