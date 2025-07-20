import javax.swing.JOptionPane;

public class DatabasePageController {
    DatabasePage databasePage;
    SQLHandler sqlHandler;
    Tasks tasks;

    public DatabasePageController(DatabasePage databasePage){
        this.databasePage = databasePage;
        tasks  = Tasks.getInstance();
        loadExistingItemstoRows();  
        SQLHandler.getInstance().inspectTaskIdSequence();
        

    }

    public void loadExistingItemstoRows(){
        SQLHandler.getInstance().loadTasksFromDatabase();
        //sqlHandler.clearTasksTable();
        

        if (tasks.size()==0){
            databasePage.addFirstNewTask();
            System.out.println("No items in task list");
        } else {
            for (int taskIndex = 0; taskIndex<tasks.size();taskIndex++){
                Task task = tasks.get(taskIndex);
                databasePage.addExistingTask(task);
            }
            System.out.println(tasks.size() +" items in the task list");
        }
        tasks.removeAllTasks();
    }
}
