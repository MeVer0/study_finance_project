package Services;

import DB.Dao.TransactionDao;
import DB.DatabaseConnection;
import DB.Models.Transaction;

import java.util.List;

public class TransactionService {

    public enum TransactionType {
        INCOME("Доход"),
        SPEND("Трата");

        private final String description;

        TransactionType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    TransactionDao transactionDao = new TransactionDao(new DatabaseConnection().getConnectionSource(), Transaction.class);
    public TransactionService() throws Exception {
    }

    public void addTransaction(int userId,  TransactionType transactionType, int categoryId, Double sum){
        transactionDao.addTransaction(userId, transactionType, categoryId, sum);
    }

    public void deleteTransaction(int transactionId){
        transactionDao.deleteTransaction(transactionId);
    }

    public List<Transaction> getAllTransactionsByUserId(int userId){
        return transactionDao.getAllTransactionsByUserId(userId);
    }

    public List<Transaction> getAllIncomeByUserId(int userId){
        return transactionDao.getAllIncomeByUserId(userId);
    }

}
