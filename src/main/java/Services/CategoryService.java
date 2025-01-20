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

    public void updateCategory(int categoryId, String name){
        categoryDao.updateCategory(categoryId, name);
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

    public String getCategoryNameByUserIdAndCatId(int userId, int categoryId) {
        List<Category> userCategories  = categoryDao.getAllCategoriesByUserId(userId);
        for (Category category : userCategories) {
            if (category.getId() == categoryId) {
                return category.getName();
            }
        }
        return "Неизвестная категория";
    }

    public int getCategoryIdByUserIdAndCategoryName(int userId, String categoryName) {
        List<Category> userCategories  = categoryDao.getAllCategoriesByUserId(userId);
        for (Category category : userCategories) {
            if (category.getName().equals(categoryName)) {
                return category.getId();
            }
        }
        return 0;
    }
}
