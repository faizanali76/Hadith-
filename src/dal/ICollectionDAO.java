package dal;
import bll.HadithCollection;
import java.sql.SQLException;
import java.util.List;

public interface ICollectionDAO {
		List<HadithCollection> getAllCollections() throws SQLException;
		void addCollection(HadithCollection collection) throws SQLException;
		void updateCollection(HadithCollection collection) throws SQLException;
		void deleteCollection(int collectionId) throws SQLException;
}
