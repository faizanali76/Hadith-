package dal;

import bll.HadithCollection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CollectionDAO implements ICollectionDAO {

    private DatabaseConnection database;

    public CollectionDAO(DatabaseConnection database) {
        this.database = database;
    }

    @Override
    public List<HadithCollection> getAllCollections() throws SQLException {
        List<HadithCollection> collections = new ArrayList<>();

        String sql = "SELECT * FROM collections ORDER BY collection_id";

        try (Connection connection = database.openConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)) {

            while (result.next()) {
                collections.add(new HadithCollection(
                        result.getInt("collection_id"),
                        result.getString("collection_name"),
                        result.getString("compiler"),
                        result.getString("description"),
                        result.getString("edition"),
                        result.getString("publication_details")
                ));
            }
        }

        return collections;
    }
    
    @Override
    public void addCollection(HadithCollection collection) throws SQLException {

        String sql = "INSERT INTO collections "
                + "(collection_name, compiler, description, edition, publication_details) "
                + "VALUES ('"
                + collection.getCollectionName() + "', '"
                + collection.getCompiler() + "', '"
                + collection.getDescription() + "', '"
                + collection.getEdition() + "', '"
                + collection.getPublicationDetails() + "')";

        try (Connection connection = database.openConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);

            System.out.println("Collection added successfully!");
        }
    }
    
}