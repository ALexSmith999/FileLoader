

package entry;

import database.Connection;
import file.LoadChain;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.Map;
import java.util.Properties;

import static java.nio.file.StandardWatchEventKinds.*;

public class main {

    public static Properties customProperties;
    static {
        customProperties = new Properties();
        try (FileInputStream file =
                     new FileInputStream("src/main/resources/config.properties")) {
            customProperties.load(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<String, String> env = System.getenv();
        for (var entry : env.entrySet()) {
            customProperties.putIfAbsent(entry.getKey(), entry.getValue());
        }
    }
    public static void main(String[] args) throws IOException {

        int BATCH_SIZE = Integer.parseInt(customProperties.getProperty(ProjectProperties.BATCH_SIZE.label));
        Path dir = Path.of(customProperties.getProperty(ProjectProperties.FILES_DIRECTORY.label));
        WatchService watch = FileSystems.getDefault().newWatchService();
        LoadChain load = new LoadChain();
        java.sql.Connection conn = Connection.returnInstance().connect();

        try {
            //WatchKey key = dir.register(watch, ENTRstandardEventsArrayY_CREATE, ExtendedWatchEventModifier.FILE_TREE);
            WatchKey key = dir.register(watch, ENTRY_CREATE);
        }
        catch (IOException e) {
            e.printStackTrace();
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
