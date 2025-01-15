package Services;

import DB.Dao.CategoryDao;
import DB.DatabaseConnection;
import DB.Models.Category;

import java.util.List;

public class CategoryService {
    CategoryDao categoryDao = new CategoryDao(new DatabaseConnection().getConnectionSource(), Category.class);
    public CategoryService() throws Exception {
    }

    public void addCategory(int userId, String name){
        categoryDao.addCategory(userId, name);
    }

    public void deleteCategory(int categoryId){
        categoryDao.deleteCategory(categoryId);
    }

    public List<Category> getAllCategoriesByUserId(int userId){
        return categoryDao.getAllCategoriesByUserId(userId);
    }

    public Category getCategoryById(int categoryId){
        return categoryDao.getCategoryById(categoryId);
    }
}
