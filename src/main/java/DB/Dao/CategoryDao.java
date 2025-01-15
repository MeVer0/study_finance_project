package DB.Dao;

import DB.Models.Category;
import DB.Models.Transaction;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao extends BaseDaoImpl<Category, Integer> {
    public CategoryDao(ConnectionSource connectionSource, Class<Category> dataClass) throws SQLException {
        super(connectionSource, dataClass);
        TableUtils.createTableIfNotExists(connectionSource, Category.class);
    }

    public void addCategory(int userId, String name) {
        try {
            this.create(new Category(name, userId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCategory(int categoryId) {
        try {
            Category category = this.queryBuilder().where().eq("Id", categoryId).queryForFirst();
            this.delete(category);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Category> getAllCategoriesByUserId(int userId) {
        try {
            return this.queryBuilder().where().eq("UserId", userId).query();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Category getCategoryById(int categoryId) {
        try {
            return this.queryBuilder().where().eq("Id", categoryId).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}