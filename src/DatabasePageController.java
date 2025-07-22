import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

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
                    filterTasksByTitleAndDescription(query);
                }
            }
        });

        databasePage.getClearSearchButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetTaskList();
            }
        });

        databasePage.getBucketDropDownButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPopupMenu bucketMenu = new JPopupMenu();
                String[] bucketOptions = RowPanel.getBucketOptions();
                for (String option : bucketOptions) {
                    JMenuItem item = new JMenuItem(option);
                    item.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(databasePage.getDatabaseFrame(), "Selected: " + option);
                            filterTasksByBucket(option);
                        }
                    });
                    bucketMenu.add(item);
                }
                JMenuItem resetItem = new JMenuItem("Show All");
                resetItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        resetTaskList(); // Call the controller method to reset
                    }
                });
                bucketMenu.addSeparator(); // Optional: separates reset from other options
                bucketMenu.add(resetItem);
                bucketMenu.show(databasePage.getBucketDropDownButton(), 0, databasePage.getBucketDropDownButton().getHeight());
            }
        });

        databasePage.getFrequencyDropDownButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPopupMenu bucketMenu = new JPopupMenu();
                String[] frequencyOptions = RowPanel.getFrequencyOptions();
                for (String option : frequencyOptions) {
                    JMenuItem item = new JMenuItem(option);
                    item.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(databasePage.getDatabaseFrame(), "Selected: " + option);
                            filterTasksByFrequency(option);
                        }
                    });
                    bucketMenu.add(item);
                }
                JMenuItem resetItem = new JMenuItem("Show All");
                resetItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        resetTaskList(); // Call the controller method to reset
                    }
                });
                bucketMenu.addSeparator(); // Optional: separates reset from other options
                bucketMenu.add(resetItem);
                bucketMenu.show(databasePage.getFrequencyDropDownButton(), 0, databasePage.getFrequencyDropDownButton().getHeight());
            }
        });

        databasePage.getAssignedEmailsDropDownButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPopupMenu bucketMenu = new JPopupMenu();
                ArrayList<String> assignedEmailsOptions = SQLHandler.getInstance().getAllEmails();
                for (String option : assignedEmailsOptions) {
                    JMenuItem item = new JMenuItem(option);
                    item.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(databasePage.getDatabaseFrame(), "Selected: " + option);
                            filterTasksByAssignedEmails(option);
                        }
                    });
                    bucketMenu.add(item);
                }
                JMenuItem resetItem = new JMenuItem("Show All");
                resetItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        resetTaskList(); // Call the controller method to reset
                    }
                });
                bucketMenu.addSeparator(); // Optional: separates reset from other options
                bucketMenu.add(resetItem);
                bucketMenu.show(databasePage.getAssignedEmailsDropDownButton(), 0, databasePage.getAssignedEmailsDropDownButton().getHeight());
            }
        });


        databasePage.getPlannerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlannerPage plannerPage = new PlannerPage(databasePage.getDatabaseFrame());
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
        }
        tasks.removeAllTasks();
    }

    public void filterTasksByTitleAndDescription(String query) {
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

    public void filterTasksByBucket(String bucket) {
        SQLHandler.getInstance().loadTasksFromDatabase();
        List<Task> filtered = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getBucket().equalsIgnoreCase(bucket)) {
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

    public void filterTasksByFrequency(String frequency) {
        SQLHandler.getInstance().loadTasksFromDatabase();
        List<Task> filtered = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getFrequency().equalsIgnoreCase(frequency)) {
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

    public void filterTasksByAssignedEmails(String email) {
        SQLHandler.getInstance().loadTasksFromDatabase();
        List<Task> filtered = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getAssignedEmails().contains(email.toLowerCase())) {
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
