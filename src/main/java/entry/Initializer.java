package entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.Properties;

public class Initializer {
    private static final Logger logger = LogManager.getLogger(Initializer.class);
    static void start(Properties prop){
        logger.info("===== Application is running =====");
        logger.info("===== Working directory is {} =====", prop.getProperty(ProjectProperties.FILES_DIRECTORY.label));
        logger.info("===== Current Database is {} =====", prop.getProperty(ProjectProperties.DB_LINK.name()));
    }
    static void stop(Properties prop){
        logger.info("===== Application is shutting down =====");
    }
}
