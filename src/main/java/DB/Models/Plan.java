package DB.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Plan")
public class Plan {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private int userId;

    @DatabaseField
    private int categoryId;

    @DatabaseField
    private Double sum;

    @DatabaseField
    private String name; // Добавленное поле для имени плана

    public Plan() {}

    public Plan(int userId, int categoryId, Double sum, String name) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.sum = sum;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
