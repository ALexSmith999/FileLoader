package entry;
import database.Connection;
import file.LoadChain;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static java.nio.file.StandardWatchEventKinds.*;

public class FileLoaderLaunch {

    public static Properties customProperties = new Properties();
    static {
        try (var file = new java.io.FileInputStream("src/main/resources/config.properties")) {
            customProperties.load(file);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open the property file or it does not exist", e);
        }
    }
    private static final Logger logger = LogManager.getLogger(FileLoaderLaunch.class);
    public static void main(String[] args) throws IOException {
        /*
        TO DO : add pools to divide the execution process into separate steps
        that can be executed concurrently : validation - parse - load
        after getting a file complete two commands that inherit the callable interface
        whereas the database load depends on the successful completion of previous two
        **/
        int batchSize;
        Path dir;
        try {
            batchSize = Integer.parseInt(customProperties.getProperty(ProjectProperties.BATCH_SIZE.label));
            dir = Path.of(customProperties.getProperty(ProjectProperties.FILES_DIRECTORY.label));
        } catch (Exception e) {
            logger.error("Failed to load configuration properties", e);
            return;
        }

        try (WatchService watch = FileSystems.getDefault().newWatchService();
             var conn = Connection.returnInstance().connect()) {

            LoadChain load = new LoadChain();
            Initializer.start(customProperties);

            dir.register(watch, ENTRY_CREATE);

            logger.info("Type 'exit' and press Enter to stop the program.");

            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
            boolean keepRunning = true;

            while (keepRunning) {
                // Poll watch service with timeout (e.g., 500ms)
                WatchKey key = watch.poll(500, java.util.concurrent.TimeUnit.MILLISECONDS);

                if (key != null) {
                    for (WatchEvent<?> event : key.pollEvents()) {
                        if (event.kind() == OVERFLOW) continue;

                        WatchEvent<Path> ev = (WatchEvent<Path>) event;
                        Path filename = ev.context();
                        Path filePath = dir.resolve(filename);

                        logger.info("The file {} is ready to be loaded", filename);
                        load.loadFile(conn, filePath, batchSize);
                    }

                    if (!key.reset()) {
                        logger.warn("WatchKey no longer valid, stopping watcher");
                        break;
                    }
                }

                // Check if user input is available (non-blocking)
                if (reader.ready()) {
                    String line = reader.readLine();
                    if (line != null && line.trim().equalsIgnoreCase("exit")) {
                        logger.info("Exit command received, stopping...");
                        keepRunning = false;
                    }
                }
            }

        } catch (IOException | InterruptedException | SQLException e) {
            logger.error("Error in watcher service or database connection", e);
            Thread.currentThread().interrupt();
        }

        Initializer.stop(customProperties);
    }
}
