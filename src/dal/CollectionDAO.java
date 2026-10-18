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
}