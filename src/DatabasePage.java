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
    JPanel mainPanel;
    JButton bucketDropDownButton;
    JButton frequencyDropDownButton;
    JButton assignedEmailsDropDownButton;
    JButton dueDateDropDownButton;

    private JTextField searchField;
    private JButton searchButton;
    private JButton clearSearchButton;


    public DatabasePage(JFrame dataFrame){
        Font labelFont = new Font("Serif bold", Font.BOLD, 16);

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
        searchButton.setFont(labelFont);
        clearSearchButton = new JButton("Clear Search");
        clearSearchButton.setFont(labelFont);
        clearSearchButton.setVisible(false);
        JLabel searchLabel = new JLabel("Search (Title/Description):");
        searchLabel.setFont(labelFont);
        searchPanel.add(searchLabel);
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
        preTitleBuffer.setPreferredSize(new Dimension(80, 10));
        gridLabels.add(preTitleBuffer);
        JLabel titleLabel = new JLabel ("Title");
        titleLabel.setFont(labelFont);
        gridLabels.add(titleLabel);

        JPanel preDescriptionBuffer = new JPanel();
        preDescriptionBuffer.setPreferredSize(new Dimension(140, 10));
        gridLabels.add(preDescriptionBuffer);
        JLabel descriptionLabel = new JLabel ("Description");
        descriptionLabel.setFont(labelFont);
        gridLabels.add(descriptionLabel);

        JPanel preBucketBuffer = new JPanel();
        preBucketBuffer.setPreferredSize(new Dimension(110, 10));
        gridLabels.add(preBucketBuffer);
        JLabel bucketLabel = new JLabel ("Bucket");
        bucketLabel.setFont(labelFont);
        bucketDropDownButton = new JButton("▼");
        JPanel bucketLabelAndDropDown = new JPanel();
        bucketLabelAndDropDown.add(bucketLabel);
        bucketLabelAndDropDown.add(bucketDropDownButton);
        gridLabels.add(bucketLabelAndDropDown);

        JPanel preFrequencyBuffer = new JPanel();
        preFrequencyBuffer.setPreferredSize(new Dimension(20, 10));
        gridLabels.add(preFrequencyBuffer);
        JLabel frequencyLabel = new JLabel ("Frequency");
        frequencyLabel.setFont(labelFont);
        frequencyDropDownButton = new JButton("▼");
        JPanel frequencyLabelAndDropDown = new JPanel();
        frequencyLabelAndDropDown.add(frequencyLabel);
        frequencyLabelAndDropDown.add(frequencyDropDownButton);
        gridLabels.add(frequencyLabelAndDropDown);

        JPanel preAssignedEmailsBuffer = new JPanel();
        preAssignedEmailsBuffer.setPreferredSize(new Dimension(30, 10));
        gridLabels.add(preAssignedEmailsBuffer);
        JLabel assignedEmailsLabel = new JLabel ("Assigned Emails");
        assignedEmailsLabel.setFont(labelFont);
        assignedEmailsDropDownButton = new JButton("▼");
        JPanel assignedEmailsLabelAndDropDown = new JPanel();
        assignedEmailsLabelAndDropDown.add(assignedEmailsLabel);
        assignedEmailsLabelAndDropDown.add(assignedEmailsDropDownButton);
        gridLabels.add(assignedEmailsLabelAndDropDown);

        JPanel preDueDateBuffer = new JPanel();
        preDueDateBuffer.setPreferredSize(new Dimension(100, 10));
        gridLabels.add(preDueDateBuffer);
        JLabel dueDateLabel = new JLabel ("Due Date");
        dueDateLabel.setFont(labelFont);
        dueDateDropDownButton = new JButton("▼");
        JPanel dueDateLabelAndDropDown = new JPanel();
        dueDateLabelAndDropDown.add(dueDateLabel);
        dueDateLabelAndDropDown.add(dueDateDropDownButton);
        gridLabels.add(dueDateLabelAndDropDown);

        JPanel preActionBuffer = new JPanel();
        preActionBuffer.setPreferredSize(new Dimension(90, 10));
        gridLabels.add(preActionBuffer);
        JLabel actionLabel = new JLabel ("Action");
        actionLabel.setFont(labelFont);
        gridLabels.add(actionLabel);
        JPanel postActionBuffer = new JPanel();
        postActionBuffer.setPreferredSize(new Dimension(25, 10));
        gridLabels.add(postActionBuffer);

        gridLabels.setLayout(new BoxLayout(gridLabels, BoxLayout.X_AXIS));
        gridPanel = new JPanel();
        gridPanel.setLayout(new BoxLayout(gridPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(gridPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 

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
        addNewTask();
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

    public JButton getPlannerButton(){
        return plannerButton;
    }

    public JFrame getDatabaseFrame(){
        return dataFrame;
    }

    public JButton getBucketDropDownButton(){
        return bucketDropDownButton;
    }

    public JButton getFrequencyDropDownButton(){
        return frequencyDropDownButton;
    }

    public JButton getAssignedEmailsDropDownButton(){
        return assignedEmailsDropDownButton;
    }

    public JButton getDueDateDropDownButton(){
        return dueDateDropDownButton;
    }
}