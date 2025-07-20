import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ColumnPanel {
    JButton newBucketButton;
    JPanel newBucketPanel;
    JPanel column;
    JPanel subColumnPanel;
    JPanel bucketLabelPanel;
    JLabel bucketNameLabel;
    JScrollPane scrollPane;

    public ColumnPanel(String bucketName){
        Font labelFont = new Font("Serif bold", Font.BOLD, 16);
        Font inputFont = new Font("Serif bold", Font.PLAIN, 14);
        bucketNameLabel = new JLabel(bucketName); 
        bucketNameLabel.setFont(labelFont);
        column = new JPanel();
        subColumnPanel = new JPanel();
        bucketLabelPanel = new JPanel();
        bucketLabelPanel.add(bucketNameLabel);

        subColumnPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        subColumnPanel.setLayout(new BoxLayout(subColumnPanel, BoxLayout.Y_AXIS));
        subColumnPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        scrollPane = new JScrollPane(subColumnPanel);
        column.add(bucketLabelPanel);
        column.add(scrollPane);

        //scrollPane.setPreferredSize(new Dimension(300, )); // Adjust height as needed
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 	
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        subColumnPanel.setLayout(new BoxLayout(subColumnPanel,BoxLayout.Y_AXIS));
        column.setLayout(new BoxLayout(column,BoxLayout.Y_AXIS));
    }

    public void setPanelDimensions(int width, int height){
        scrollPane.setPreferredSize(new Dimension(width,height-200));
    }

    public void setBucketName(String bucketName){
        bucketNameLabel.setText(bucketName);
    }

    public String getBucketName(){
        return bucketNameLabel.getText();
    }

    public void addTask(Task task){
        TaskPanel taskP = new TaskPanel(task, this);

        subColumnPanel.add(taskP.getTaskPanel());

        // Remove and re-add glue to keep it at the bottom
        for (Component comp : subColumnPanel.getComponents()) {
            if (comp instanceof Box.Filler) {
                break;
            }
        }
        subColumnPanel.add(Box.createVerticalGlue());

        subColumnPanel.revalidate();
        subColumnPanel.repaint();
    }


    public void removeTaskPanel(TaskPanel taskP) {
        JPanel panelToRemove = taskP.getTaskPanel();

        // Remove the task panel and any vertical strut before it
        Component[] components = subColumnPanel.getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i] == panelToRemove) {
                // Remove the vertical strut before the panel (if any)
                if (i > 0 && components[i - 1] instanceof Box.Filler) {
                    subColumnPanel.remove(components[i - 1]);
                }
                subColumnPanel.remove(panelToRemove);
                break;
            }
        }

        // Remove any existing vertical glue
        for (Component comp : subColumnPanel.getComponents()) {
            if (comp instanceof Box.Filler) {
                subColumnPanel.remove(comp);
                break;
            }
        }

        // Re-add vertical glue at the bottom
        subColumnPanel.add(Box.createVerticalGlue());

        subColumnPanel.revalidate();
        subColumnPanel.repaint();

        // Handle task deletion or due date update
        Task task = taskP.getTask();
        if (task.getFrequency().equals("One Time")) {
            SQLHandler.getInstance().deleteTaskByID(task.getID());
        } else {
            SQLHandler.getInstance().updateTaskDueDateByFrequency(task.getID());
        }
    }


    public JPanel getColumnPanel(){
        return column;
    }
}
