package DB.Dao;

import DB.Models.Transaction;
import Services.TransactionService;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao extends BaseDaoImpl<Transaction, Integer> {
    public TransactionDao(ConnectionSource connectionSource, Class<Transaction> dataClass) throws SQLException {
        super(connectionSource, dataClass);
        TableUtils.createTableIfNotExists(connectionSource, Transaction.class);
    }

    public void addTransaction(int userId, TransactionService.TransactionType transactionType, int categoryId, Double sum) {
        try {
            this.create(new Transaction(userId, categoryId, sum, transactionType));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTransaction(int transactionId) {
        try {
            Transaction transaction = this.queryBuilder().where().eq("Id", transactionId).queryForFirst();
            this.delete(transaction);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Transaction> getAllTransactionsByUserId(int userId) {
        try {
            return this.queryBuilder().where().eq("UserId", userId).query();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Transaction> getAllIncomeByUserId(int userId) {
        try {
            return this.queryBuilder().
                    where()
                    .eq("UserId", userId)
                    .and()
                    .eq("transactionType", TransactionService.TransactionType.INCOME)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Transaction> getAllSpendByUserId(int userId) {
        try {
            return this.queryBuilder().
                    where()
                    .eq("UserId", userId)
                    .and()
                    .eq("transactionType", TransactionService.TransactionType.SPEND)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Transaction getTransactionById(int transactionId) {
        try {
            return this.queryBuilder().where().eq("Id", transactionId).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateTransaction(int transactionId, TransactionService.TransactionType transactionType, int categoryId, Double sum) {
        try {
            Transaction transactionToUpdate = this.getTransactionById(transactionId);
            transactionToUpdate.setSum(sum);
            transactionToUpdate.setCategoryId(categoryId);
            transactionToUpdate.setType(transactionType);
            this.update(transactionToUpdate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
