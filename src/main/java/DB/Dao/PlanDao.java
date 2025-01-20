package DB.Dao;

import DB.Models.Plan;
import DB.Models.Transaction;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlanDao extends BaseDaoImpl<Plan, Integer> {

    public PlanDao(ConnectionSource connectionSource, Class<Plan> dataClass) throws SQLException {
        super(connectionSource, dataClass);
        TableUtils.createTableIfNotExists(connectionSource, Plan.class);
    }

    public void addPlan(int userId, int categoryId, String name, Double sum) {
        try {
            this.create(new Plan(userId, categoryId, sum, name));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Plan getPlanById(int planId) {
        try {
            Plan plan = this.queryBuilder().where().eq("Id", planId).queryForFirst();
            return plan;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updatePlan(int planId, int CategoryId, Double sum) {
        try {
            Plan planToUpdate = this.queryForId(planId);
            planToUpdate.setCategoryId(CategoryId);
            planToUpdate.setSum(sum);
            this.update(planToUpdate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePlan(int planId) {
        try {
            Plan planToDelete = this.queryForId(planId);
            this.delete(planToDelete);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Plan> getAllPlansByUserId(int userId) {
        try {
            List<Plan> userPlans = this.queryBuilder().where().eq("userId", userId).query();
            return userPlans;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public int getPlanIdByUserIdAndPlanName(int userId, String planName) {
        try {
            List<Plan> plans = this.queryBuilder().where().eq("userId", userId).and().eq("name", planName).query();
            if (!plans.isEmpty()) {
                return plans.get(0).getId();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
