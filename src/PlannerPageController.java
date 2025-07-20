import java.util.ArrayList;

import javax.swing.JOptionPane;

public class PlannerPageController {
    private PlannerPage plannerPage;
    private Tasks tasks;
    SQLHandler sqlHandler;


    public PlannerPageController(PlannerPage plannerPage){
       this.plannerPage = plannerPage;
       tasks = Tasks.getInstance();
       addTasksByBucket();
       plannerPage.revalidateFrame();
    }


    public void addTasksByBucket() {
        sqlHandler = SQLHandler.getInstance();
        sqlHandler.loadTasksFromDatabase();
        ArrayList<ColumnPanel> columnsList = plannerPage.getColumnsList();
        if (tasks.size() > 0) {
            JOptionPane.showMessageDialog(plannerPage.getColumnsList().get(0).getColumnPanel(), "The size of tasks list: " + tasks.size());
        }
        for (int taskIndex = 0;taskIndex<tasks.size();taskIndex++){
            Task task = tasks.get(taskIndex);
            System.out.println("Task Before PlannerPage: " + task.getDueDate());
            for (int colIndex = 0; colIndex < columnsList.size(); colIndex++) {
            ColumnPanel column = columnsList.get(colIndex);
            if (column.getBucketName().equals(task.getBucket())) {
                column.addTask(task);
                System.out.println("Task"+ taskIndex+" After PlannerPage: " + task.getDueDate());

                break; // Stop checking once the correct column is found
                }
            }
        }
        tasks.removeAllTasks();
    }   

    public void addNewColumn(){

    }


}
