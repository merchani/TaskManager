import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlannerPage {
    JPanel borderPanel;
    Color graphicColor;
    Color graphicColor2;
    TaskPanel taskPanel; 
    ArrayList<ColumnPanel> columnsList;
    JPanel buttonsPanel;
    JPanel columnPanels;
    JFrame plannerFrame;

    public PlannerPage(JFrame plannerFrame){
        Font labelFont = new Font("Serif bold", Font.BOLD, 16);

        columnsList = new ArrayList<ColumnPanel>();
        this.plannerFrame = plannerFrame;

        buttonsPanel = new JPanel(); // Align buttons to the left
        // Add Back button
        JButton backButton = new JButton("Back to Database");
        backButton.setFont(labelFont);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plannerFrame.repaint();
                plannerFrame.revalidate(); // Reuse the same frame
                plannerFrame.setVisible(true);
                new DatabasePage(plannerFrame);
                }
            });
        buttonsPanel.add(backButton);


        plannerFrame.getContentPane().removeAll();
        plannerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        plannerFrame.setTitle("Planner: Planner Page");
        columnPanels = new JPanel();

        columnsList.add(new ColumnPanel("Quality Team"));
        columnsList.add(new ColumnPanel("Environments Team"));
        columnsList.add(new ColumnPanel("Change and Release Team"));
        columnsList.add(new ColumnPanel("Operations Monitoring Team"));
        columnPanels.setLayout(new FlowLayout());
        
        //for loop of all columns in columnsPanel to add and set size
        for(int index = 0; index<columnsList.size();index++){
            ColumnPanel column = columnsList.get(index);
            int height = plannerFrame.getHeight();
            int width = plannerFrame.getWidth();
            column.setPanelDimensions(width/5,height);
            columnPanels.add(column.getColumnPanel());
        }
        plannerFrame.getContentPane().add(buttonsPanel);
        plannerFrame.getContentPane().add(columnPanels);
        plannerFrame.setLayout(new BoxLayout(plannerFrame.getContentPane(),BoxLayout.Y_AXIS));

        revalidateFrame();

        new PlannerPageController(this);
    }

    public ArrayList<ColumnPanel> getColumnsList(){
        return columnsList;
    }

    public JPanel getColumnPanels(){
        return columnPanels;
    }

    public void revalidateFrame(){
        plannerFrame.getContentPane().revalidate();
        plannerFrame.getContentPane().repaint();
        plannerFrame.setVisible(true);
    }
}
