import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TaskPanel {
    JPanel taskPanel;
    JPanel taskTitlePanel;
    JLabel taskTitleLabel;
    JPanel taskDescriptionPanel;
    JLabel taskDescriptionLabel;
    JPanel taskAssigneesPanel;
    JLabel taskAssigneesLabel;
    JPanel taskDueDatePanel;
    JLabel taskDueDateLabel;
    JPanel taskPanelShape;
    ColumnPanel parentColumn;
    Task task;
    JCheckBox completeCheckBox;

    public TaskPanel(Task task, ColumnPanel parentColumn){
        this.task = task;
        this.parentColumn = parentColumn;
        taskPanel = new JPanel();
        taskPanel.setPreferredSize(new Dimension(300, 149));
        taskPanelShape = new JPanel();
        taskPanelShape.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        taskPanelShape.setForeground(new Color(250,250,250));
        taskPanelShape.setPreferredSize(new Dimension(300,149));
        taskPanelShape.setMaximumSize(new Dimension(300, 150)); // Same as preferred size


        taskDueDatePanel = new JPanel();
        taskDueDateLabel = new JLabel(task.getDueDate());
        taskDueDatePanel.add(taskDueDateLabel);

        taskTitlePanel = new JPanel();
        taskTitleLabel = new JLabel(task.getTitle());
        taskTitlePanel.add(taskTitleLabel);

        taskDescriptionPanel = new JPanel();
        taskDescriptionLabel = new JLabel(task.getDescription());
        taskDescriptionPanel.add(taskDescriptionLabel);

        taskAssigneesPanel = new JPanel();
        taskAssigneesLabel = new JLabel(task.getAssignedEmails());
        taskAssigneesPanel.add(taskAssigneesLabel);

        completeCheckBox = new JCheckBox("Mark Task Complete");
        completeCheckBox.addActionListener(em-> handleTaskCompletion());
        // Wrap checkbox in a panel with centered layout
        
        JPanel checkboxPanel = new JPanel();
        checkboxPanel.add(completeCheckBox);

        taskPanelShape.add(taskDueDatePanel);
        taskPanelShape.add(taskTitlePanel);
        taskPanelShape.add(checkboxPanel);

        taskPanelShape.setLayout(new BoxLayout(taskPanelShape, BoxLayout.Y_AXIS));
        taskPanel.add(taskPanelShape, BorderLayout.CENTER);
    }

    private void handleTaskCompletion(){
        int result = JOptionPane.showConfirmDialog(taskPanel, "Are you sure you want to mark this task as complete?", "Confirm Task Completion",JOptionPane.YES_NO_OPTION);
        if(result == JOptionPane.YES_OPTION){
            parentColumn.removeTaskPanel(this);
        } else {
            completeCheckBox.setSelected(false);
        }
    }
    public Task getTask(){
        return task;
    }

    public void editTaskPanel(){

    }

    public void setTaskTitle(String input){

    }
    public  void setTaskDescription(String input){

    }
    public JPanel getTaskPanel(){
        return taskPanel;
    }
}
