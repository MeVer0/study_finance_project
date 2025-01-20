package Services;

import DB.Dao.PlanDao;
import DB.DatabaseConnection;
import DB.Models.Plan;

import java.util.List;

public class PlanService {
    PlanDao planDao = new PlanDao(new DatabaseConnection().getConnectionSource(), Plan.class);

    public PlanService() throws Exception {
    }

    public void addPlan(int userId, int categoryId, String name,  Double sum) {
        planDao.addPlan(userId, categoryId, name, sum);
    }

    public void deletePlan(int planId) {
        planDao.deletePlan(planId);
    }

    public void updatePlan(int planId, int categoryId, Double sum) {
        planDao.updatePlan(planId, categoryId, sum);
    }

    public List<Plan> getAllPlansByUserId(int userId){
        return planDao.getAllPlansByUserId(userId);
    };

    public int getPlanIdByUserIdAndPlanName(int userId, String planName){
        return planDao.getPlanIdByUserIdAndPlanName(userId, planName);
    }

    public Plan getPlanById(int planId){
        return planDao.getPlanById(planId);
    }
}
