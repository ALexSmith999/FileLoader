package entry;

public enum ProjectProperties {
    DB_LINK("DB_LINK"),
    DB_USER("DB_USER"),
    DB_PASSWD("DB_PASSWD"),
    BATCH_SIZE("BATCH_SIZE"),
    FILES_DIRECTORY("FILES_DIRECTORY");

    public final String label;
    private ProjectProperties(String label) {
        this.label = label;
    }
}
