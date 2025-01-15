package Services;
import DB.Models.Category;
import DB.Models.Transaction;
import DB.Models.User;
import Exceptions.LoginException;

import java.util.List;

public class MainService {

    UserService userService = new UserService();
    CategoryService categoryService = new CategoryService();
    TransactionService transactionService = new TransactionService();

    User currentUser;

    public MainService() throws Exception {}

    public class RegistrationNLogin {
        public void registerUser(String username, String password) {
            if (!userService.checkUserAlreadyExists(username)) {
                userService.addUser(username, password);
            };
        }

        public void login(String username, String password) {
            if (currentUser == null) {
                if (userService.checkForLogin(username, password)) {
                    currentUser = userService.getUserForLogin(username);
                };
            } else {
                throw new LoginException("Перед попыткой войти, нужно выйти из текущего пользователя");
            }
        }

        public void logout() {
            if (currentUser != null) {
                currentUser = null;
            } else {
                throw new LoginException("Пользователь итак не назначен");
            }
        }
    }

    public class TransactionCRUD {
        public void createTransaction(int categoryId, TransactionService.TransactionType transactionType, Double sum) {
            if (currentUser == null) {
                throw new LoginException("Перед созданием транзакций необходимо войти в систему");
            }
            transactionService.addTransaction(currentUser.getId(), transactionType,categoryId, sum);
        }

        public void deleteTransaction(int transactionId) {
            if (currentUser == null) {
                throw new LoginException("Перед созданием транзакций необходимо войти в систему");
            }
            transactionService.deleteTransaction(transactionId);
        }

        public List<Transaction> getAllTransactionsByUserId() {
            if (currentUser == null) {
                throw new LoginException("Перед созданием транзакций необходимо войти в систему");
            }
            return transactionService.getAllTransactionsByUserId(currentUser.getId());
        }

        public List<Transaction> getAllIncomeByUserId(int userId) {
            if (currentUser == null) {
                throw new LoginException("Перед созданием транзакций необходимо войти в систему");
            }
            return transactionService.getAllIncomeByUserId(userId);
        }
    }

    public class CategoryCRUD {

        public void createCategory(String name) {
            categoryService.addCategory(currentUser.getId(), name);
        }

        public void deleteCategory(int categoryId) {
            categoryService.deleteCategory(categoryId);
        }

        public List<Category> getAllTransactionsByUserId() {
            if (currentUser == null) {
                throw new LoginException("Перед созданием транзакций необходимо войти в систему");
            }
            return categoryService.getAllCategoriesByUserId(currentUser.getId());
        }
    }

}
