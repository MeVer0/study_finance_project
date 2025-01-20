package DB.Models;

import Services.TransactionService;
import com.google.gson.Gson;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.types.EnumStringType;
import com.j256.ormlite.table.DatabaseTable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@DatabaseTable
public class Transaction {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private int userId;

    @DatabaseField(columnName = "transactionType", persisterClass = EnumStringType.class)
    private TransactionService.TransactionType transactionType;

    @DatabaseField
    private String date;

    @DatabaseField
    private Double sum;

    @DatabaseField
    private int categoryId;

    public Transaction() {
        this.date = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public Transaction(int userId, int categoryId, Double sum, TransactionService.TransactionType transactionType) {
        this.date = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.userId = userId;
        this.sum = sum;
        this.categoryId = categoryId;
        this.transactionType = transactionType;
    }

    public TransactionService.TransactionType getType() {
        return transactionType;
    }

    public void setType(TransactionService.TransactionType type) {
        this.transactionType = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
