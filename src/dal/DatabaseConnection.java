package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private final String url =
            "jdbc:mysql://mysql-hadtith-project-abdulsak2004-010a.g.aivencloud.com:18639/hadith_db?sslMode=REQUIRED";

    private final String user = System.getenv("HADITH_DB_USER");
    private final String password = System.getenv("HADITH_DB_PASSWORD");

    public DatabaseConnection() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
    }

    public Connection openConnection() throws SQLException {

        if (user == null || password == null) {
            throw new SQLException(
                "Set HADITH_DB_USER and HADITH_DB_PASSWORD environment variables."
            );
        }

        return DriverManager.getConnection(url, user, password);
    }
}