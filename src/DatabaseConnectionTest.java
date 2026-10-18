
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseConnectionTest {

    public static void main(String[] args) {

        String host = "mysql-hadtith-project-abdulsak2004-010a.g.aivencloud.com";
        String port = "18639";
        String database = "hadith_db";

        String username = System.getenv("HADITH_DB_USER");
        String password = System.getenv("HADITH_DB_PASSWORD");

        String url = "jdbc:mysql://" + host + ":" + port + "/"
                + database + "?sslMode=REQUIRED";

        String testName = "Java Connection Test";

        try (Connection connection =
                DriverManager.getConnection(url, username, password)) {

            System.out.println("Connected to Aiven!");

            String insertSQL =
                    "INSERT INTO collections (collection_name, description) "
                    + "VALUES (?, ?)";

            try (PreparedStatement insert =
                    connection.prepareStatement(insertSQL)) {

                insert.setString(1, testName);
                insert.setString(2, "Temporary test record from Java");
                insert.executeUpdate();

                System.out.println("INSERT successful!");
            }

            String selectSQL =
                    "SELECT collection_id, collection_name, description "
                    + "FROM collections WHERE collection_name = ?";

            try (PreparedStatement select =
                    connection.prepareStatement(selectSQL)) {

                select.setString(1, testName);

                try (ResultSet result = select.executeQuery()) {
                    while (result.next()) {
                        System.out.println(
                                "ID: " + result.getInt("collection_id"));
                        System.out.println(
                                "Name: " + result.getString("collection_name"));
                        System.out.println(
                                "Description: "
                                + result.getString("description"));
                    }
                }
            }

            System.out.println("All database tests completed!");

        } catch (Exception e) {
            System.out.println("Database test failed:");
            e.printStackTrace();
       
        }
    }
}
