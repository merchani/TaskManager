import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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

        databasePage.getSearchButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String query = databasePage.getSearchField().getText().trim();
                if (!query.isEmpty()) {
                    filterTasks(query);
                }
            }
        });

        databasePage.getClearSearchButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetTaskList();
            }
        });
        

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

    public void filterTasks(String query) {
        SQLHandler.getInstance().loadTasksFromDatabase();
        List<Task> filtered = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                task.getDescription().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(task);
            }
        }
        databasePage.clearTasksFromGrid();
        for (Task task : filtered) {
            databasePage.addExistingTask(task);
        }
        databasePage.highlightSearchMode();
        tasks.removeAllTasks();
    }

    public void resetTaskList() {
        databasePage.clearTasksFromGrid();
        loadExistingItemstoRows();
        databasePage.clearSearchHighlight();
    }

}
