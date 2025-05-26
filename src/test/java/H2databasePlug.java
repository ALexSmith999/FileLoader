import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;




public class H2databasePlug {
    private static final String JDBC_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            //typeA (ID, date, amount)
            //typeB (ID, date, amount, amount1, amount2)
            //typeC(ID, name, surname, city)
            stmt.execute("CREATE TABLE IF NOT EXISTS typeA (ID VARCHAR(100), date VARCHAR(100), amount VARCHAR(100))");
            stmt.execute("CREATE TABLE IF NOT EXISTS typeB (ID VARCHAR(100), date VARCHAR(100), amount VARCHAR(100), amount1 VARCHAR(100), amount2 VARCHAR(100))");
            stmt.execute("CREATE TABLE IF NOT EXISTS typeC (ID VARCHAR(100), name VARCHAR(100), surname VARCHAR(100), city VARCHAR(100))");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
