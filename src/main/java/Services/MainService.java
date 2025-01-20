package Services;
import DB.Models.Category;
import DB.Models.Plan;
import DB.Models.Transaction;
import DB.Models.User;
import Exceptions.LoginException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainService {

    UserService userService;
    CategoryService categoryService;
    TransactionService transactionService ;
    PlanService planService;

    public User getCurrentUser() {
        return currentUser;
    }

    public User currentUser;

    public MainService() {
        try {
            this.userService = new UserService();
            this.categoryService = new CategoryService();
            this.transactionService = new TransactionService();
            this.planService = new PlanService();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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

    public void updateTransaction(int transactionId, int categoryId, TransactionService.TransactionType transactionType, double newSum) {
        if (currentUser == null) {
            throw new LoginException("Перед редактирование транзакций необходимо войти в систему");
        }
        transactionService.updateTransaction(transactionId, transactionType, categoryId, newSum);
    }

    public Transaction getTransactionById(int transactionId){
        return transactionService.getTransactionById(transactionId);
    }

    public List<Transaction> getAllTransactionsByUserId(int userId) {
        if (currentUser == null) {
            throw new LoginException("Перед созданием транзакций необходимо войти в систему");
        }
        return transactionService.getAllTransactionsByUserId(userId);
    }

    public List<Transaction> getAllIncomeByUserId(int userId) {
        if (currentUser == null) {
            throw new LoginException("Перед получением списка доходных транзакций необходимо войти в систему");
        }
        return transactionService.getAllIncomeByUserId(userId);
    }

    public List<Transaction> getAllSpendsByUserId(int userId) {
        if (currentUser == null) {
            throw new LoginException("Перед получением списка транзакций трат необходимо войти в систему");
        }
        return transactionService.getAllSpendsByUserId(userId);
    }

    public void createCategory(String name) {
        categoryService.addCategory(currentUser.getId(), name);
    }

    public void deleteCategory(int categoryId) {
        categoryService.deleteCategory(categoryId);
    }

    public void updateCategory(int categoryId, String name) {
        categoryService.updateCategory(categoryId, name);
    }

    public List<Category> getAllCategoryByUserId(int userId) {
        if (currentUser == null) {
            throw new LoginException("Перед получением списка категорий пользователя войти в систему");
        }
        return categoryService.getAllCategoriesByUserId(userId);
    }

    public void createPlan(int userId, int categoryId, Double sum, String name) {
        if (currentUser == null) {
            throw new LoginException("Перед просмотром необходимо войти в систему");
        }
        this.planService.addPlan(userId, categoryId, name, sum);
    }

    public void deletePlan(int planId) {
        this.planService.deletePlan(planId);
    }

    public void updatePlan(int planId, int categoryId, Double sum) {
        this.planService.updatePlan(planId, categoryId, sum);
    }

    public List<Plan> getAllPlanByUserId(int userId) {
        return this.planService.getAllPlansByUserId(userId);
    }

    public Map<String, Double> getCalculatedInfoByUserId(int userId) {

        List<Transaction> usersIncomes = getAllIncomeByUserId(userId);
        List<Transaction> usersSpends = getAllSpendsByUserId(userId);

        List<Category> usersCategory = getAllCategoryByUserId(userId);

        Map<String, Double> PnL = new HashMap<>();

        for (Transaction transaction : usersIncomes) {
            int categoryId = transaction.getCategoryId();
            Double sum = transaction.getSum();
            for (Category category : usersCategory) {
                if (categoryId == category.getId()) {
                    Double currentSum = PnL.get(category.getName());
                    if (currentSum != null){
                        PnL.put(category.getName(), currentSum + sum);
                        currentSum = 0.0;
                    } else {
                        PnL.put(category.getName(), sum);
                    }
                }
            }
        }

        return PnL;

    }

    public String getCategoryNameByUserIdAndCatId(int userId, int categoryId) {
        return categoryService.getCategoryNameByUserIdAndCatId(userId, categoryId);
    }

    public int getCategoryIdByUserIdAndCategoryName(int userId, String categoryName) {
        return categoryService.getCategoryIdByUserIdAndCategoryName(userId, categoryName);
    }

    public int getPlanIdByUserIdAndPlanName(int userId, String planName) {
        return planService.getPlanIdByUserIdAndPlanName(userId, planName);
    }

    public Category getCategoryById(int categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    public Plan getPlanById(int planId){
        return planService.getPlanById(planId);
    }

    public Map<Integer, Double> calculateSumByCategory(List<Transaction> transactions) {
        return transactions.stream().collect(
                Collectors.groupingBy(Transaction::getCategoryId, Collectors.summingDouble(Transaction::getSum))
        );
    }
}
