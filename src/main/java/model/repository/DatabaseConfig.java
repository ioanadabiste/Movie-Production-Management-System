package model.repository;

public class DatabaseConfig {

    private static final String SERVER = "localhost";   // sau IP-ul serverului
    private static final String PORT = "1433";          // portul default SQL Server
    private static final String DATABASE = "MovieProductionDB";

    public static String getConnectionUrl() {
        // integratedSecurity=true pentru Windows Authentication
        return String.format(
                "jdbc:sqlserver://%s:%s;databaseName=%s;integratedSecurity=true;",
                SERVER, PORT, DATABASE
        );
    }

    // Nu mai avem username si password
    public static void testConnection() {
        try (java.sql.Connection conn = java.sql.DriverManager.getConnection(getConnectionUrl())) {
            System.out.println("Conexiune reușită la SQL Server!");
            System.out.println("  Server: " + SERVER + ":" + PORT);
            System.out.println("  Database: " + DATABASE);
        } catch (java.sql.SQLException e) {
            System.err.println("Eroare la conectare: " + e.getMessage());
        }
    }
}