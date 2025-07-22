import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DatabasePage {
    // Create the main frame
    JTextField textBox;
    JScrollPane scrollPane;
    JPanel gridPanel;
    JPanel mainPanel;
    JLabel textLabel;
    JPanel borderPanel;
    JPanel buttonsPanel;
    JButton newTaskButton;
    JButton plannerButton;
    JPanel labelPanel;
    JFrame dataFrame;
    JTextArea textArea;
    int contentPaneHeight;
    RowPanel firstRowPanel;
    JScrollPane mainScrollPane;
    private JTextField searchField;
    private JButton searchButton;
    private JButton clearSearchButton;
    private DatabasePageController controller;


    public DatabasePage(JFrame dataFrame){
        Font labelFont = new Font("Serif bold", Font.BOLD, 16);
        Font inputFont = new Font("Serif bold", Font.PLAIN, 14);

        this.dataFrame = dataFrame;
        dataFrame.getContentPane().removeAll();
        borderPanel = new JPanel(new FlowLayout());
        JPanel content = new JPanel();
        dataFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dataFrame.setTitle("Planner: Database");

        // main panel 
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Search Panel
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        clearSearchButton = new JButton("Clear Search");
        clearSearchButton.setVisible(false);
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(clearSearchButton);
        mainPanel.add(searchPanel, BorderLayout.NORTH);

        // Create a panel with a button
        buttonsPanel = new JPanel();
        newTaskButton = new JButton("+ Add New Task");
        newTaskButton.setFont(labelFont);
        buttonsPanel.add(newTaskButton);
        borderPanel.setSize(0, 20);

        // Create a panel with a button
        plannerButton = new JButton("Planner View");
        plannerButton.setFont(labelFont);        
        buttonsPanel.add(plannerButton);
        borderPanel.add(buttonsPanel);
        
        JPanel gridLabels = new JPanel();

        JPanel preTitleBuffer = new JPanel();
        preTitleBuffer.setPreferredSize(new Dimension(60, 10));
        gridLabels.add(preTitleBuffer);
        JLabel titleLabel = new JLabel ("Title");
        titleLabel.setFont(labelFont);
        gridLabels.add(titleLabel);

        JPanel preDescriptionBuffer = new JPanel();
        preDescriptionBuffer.setPreferredSize(new Dimension(120, 10));
        gridLabels.add(preDescriptionBuffer);
        JLabel descriptionLabel = new JLabel ("Description");
        descriptionLabel.setFont(labelFont);
        gridLabels.add(descriptionLabel);

        JPanel preBucketBuffer = new JPanel();
        preBucketBuffer.setPreferredSize(new Dimension(110, 10));
        gridLabels.add(preBucketBuffer);
        JLabel bucketLabel = new JLabel ("Bucket");
        bucketLabel.setFont(labelFont);
        gridLabels.add(bucketLabel);

        JPanel preFrequencyBuffer = new JPanel();
        preFrequencyBuffer.setPreferredSize(new Dimension(70, 10));
        gridLabels.add(preFrequencyBuffer);
        JLabel frequencyLabel = new JLabel ("Frequency");
        frequencyLabel.setFont(labelFont);
        gridLabels.add(frequencyLabel);

        JPanel preAssignedEmailsBuffer = new JPanel();
        preAssignedEmailsBuffer.setPreferredSize(new Dimension(120, 10));
        gridLabels.add(preAssignedEmailsBuffer);
        JLabel assignedEmailsLabel = new JLabel ("Assigned Emails");
        assignedEmailsLabel.setFont(labelFont);
        gridLabels.add(assignedEmailsLabel);

        JPanel preDueDateBuffer = new JPanel();
        preDueDateBuffer.setPreferredSize(new Dimension(115, 10));
        gridLabels.add(preDueDateBuffer);
        JLabel dueDateLabel = new JLabel ("Due Date");
        dueDateLabel.setFont(labelFont);
        gridLabels.add(dueDateLabel);

        JPanel preActionBuffer = new JPanel();
        preActionBuffer.setPreferredSize(new Dimension(103, 10));
        gridLabels.add(preActionBuffer);
        JLabel actionLabel = new JLabel ("Action");
        actionLabel.setFont(labelFont);
        gridLabels.add(actionLabel);
        JPanel postActionBuffer = new JPanel();
        postActionBuffer.setPreferredSize(new Dimension(17, 10));
        gridLabels.add(postActionBuffer);

        gridLabels.setLayout(new BoxLayout(gridLabels, BoxLayout.X_AXIS));

        plannerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlannerPage plannerPage = new PlannerPage(dataFrame);
            }
        });

        gridPanel = new JPanel();
        gridPanel.setLayout(new BoxLayout(gridPanel, BoxLayout.Y_AXIS));

    
        
        scrollPane = new JScrollPane(gridPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
        //scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 

        newTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newTaskButton.setEnabled(false);
                addNewTask();
                addVerticalScrollPaneAtMaxHeight();
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });
        
        mainPanel.add(borderPanel);
        mainPanel.add(gridLabels);
        mainPanel.add(scrollPane);
        mainPanel.setBorder(new EmptyBorder (10,50,10,50));
        content.add(mainPanel);

        dataFrame.add(content);
        dataFrame.getContentPane().revalidate();
        dataFrame.getContentPane().repaint();
        dataFrame.setLayout(new FlowLayout());
        dataFrame.setVisible(true);

        new DatabasePageController(this);
    }

    public JPanel addNewTask(){
        firstRowPanel = new RowPanel();
        firstRowPanel.getTaskButton(newTaskButton);
        JPanel panel = firstRowPanel.getRowPanel();
        gridPanel.add(panel,0);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(2, 10, 7, 0));
        return panel;
    }

    public void addFirstNewTask(){
        JPanel panel = addNewTask();
        newTaskButton.setEnabled(false);
        newTaskButton.setVisible(false);
        gridPanel.revalidate();
    }

    public void addExistingTask(Task task){
        firstRowPanel = new RowPanel();
        firstRowPanel.initializeAsExistingTask(task);
        firstRowPanel.getTaskButton(newTaskButton);
        JPanel panel = firstRowPanel.getRowPanel();
        gridPanel.add(panel, 0);
        addVerticalScrollPaneAtMaxHeight();
    }

    private void addVerticalScrollPaneAtMaxHeight() {
        int frameHeight = dataFrame.getContentPane().getHeight();
        int gridPanelHeight = gridPanel.getPreferredSize().height; // More reliable than getHeight()
        int reservedHeight = 120; // Space for buttons, labels, etc.
        int maxHeight = frameHeight - reservedHeight;

        if (gridPanelHeight > maxHeight - 20) {
            scrollPane.setPreferredSize(new Dimension(scrollPane.getWidth(), maxHeight));
        } else {
            scrollPane.setPreferredSize(null); // Let it shrink if needed
        }

        scrollPane.revalidate();
        mainPanel.revalidate();
    }

    public void clearTasksFromGrid() {
        gridPanel.removeAll();
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    public void highlightSearchMode() {
        gridPanel.setBackground(new Color(255, 255, 200));
        gridPanel.setBorder(new LineBorder(Color.ORANGE, 2));
        clearSearchButton.setVisible(true);
    }

    public void clearSearchHighlight() {
        gridPanel.setBackground(null);
        gridPanel.setBorder(null);
        clearSearchButton.setVisible(false);
    }

    public JButton getSearchButton(){
        return searchButton;
    }
    
    public JTextField getSearchField(){
        return searchField;
    }

    public JButton getClearSearchButton(){
        return clearSearchButton;
    }
}