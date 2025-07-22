import java.time.LocalDate;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.jdatepicker.impl.*;
import java.util.Properties;



public class RowPanel {
        JButton actionButton;
        JButton newTaskButton;
        JTextArea titleTextArea;
        JTextArea descriptionTextArea;
        JComboBox<String> bucketComboBox;
        JComboBox<String> frequencyComboBox;
        JList<String> emailList;
        JPopupMenu emailDropdown;
        JButton emailSelectButton;
        ArrayList<String> emailOptions;
        JTextArea assignedEmailsTextArea;
        JDatePickerImpl dueDatePicker;
        UtilDateModel model;
        int rowLength;
        JPanel row;
        Task task;
        Tasks tasks;
        ActionListener actionListener;
        private int taskID = -1;



        public RowPanel(){
                row = new JPanel(new GridBagLayout()); 
                GridBagConstraints c = new GridBagConstraints();
                c.gridx = 6;
                c.weightx = 0.1;
                c.gridy = 0;
                row.add(createActionButton(),c);
                c.gridx = 0;
                c.weightx = 0.5;
                c.gridy = 0;
                row.add(createTitleTextArea(),c);
                c.gridx = 1;
                c.weightx = 0.5;
                c.gridy = 0;
                row.add(createDescriptionTextArea(),c);
                c.gridx = 2;
                c.weightx = 0.5;
                c.gridy = 0;
                row.add(createBucketComboBox(),c);
                c.gridx = 3;
                c.weightx = 0.5;
                c.gridy = 0;
                row.add(createFrequencyComboBox(),c);
                c.gridx = 4;
                c.weightx = 0.5;
                c.gridy = 0; 
                row.add(createAssignedEmailsDropDown(),c);
                c.gridx = 5;
                c.weightx = 0.2;
                c.gridy = 0;
                row.add(createDueDatePicker(),c);
                task =  new Task();
        }

        public void getTaskButton(JButton newTaskButton){
                this.newTaskButton = newTaskButton;
        }


        public void submitTask() throws ParseException{
                task.setTitle(getTitleString());
                task.setDescription(getDescriptionString());
                task.setBucket(getBucketString());
                task.setFrequency(getFrequencyString());
                task.setAssignedEmails(getAssignedEmailsString());
                task.setDueDate(getDueDateString());
                tasks = Tasks.getInstance();

                if (taskID == -1) {
                        int generatedId = SQLHandler.getInstance().saveNewTaskToDatabase(task);
                        task.setID(generatedId);
                        taskID = generatedId;
                        tasks.addTask(task); // Now add it with the correct ID
                } else {
                        // Existing task
                        task.setID(taskID); // Ensure the ID is preserved
                        updateTaskToDatabase(task);
                }
                
                System.out.println("Submitted task ID: " + task.getID());
                
                disableFields();


                JOptionPane.showMessageDialog(actionButton, "This Task will now appear on the Planner View!\n\n"+
                "The taskID is '"+taskID+"'\n"+
                "The title is '"+task.getTitle()+"'\n"+
                "The description is '"+task.getDescription()+"'\n"+
                "The bucket is '"+task.getBucket()+"'\n"+
                "The frequency is '"+task.getFrequency()+"'\n"+
                "The assigned email(s) include'"+task.getAssignedEmails()+"'\n"+
                "The due date is '"+task.getDueDate()+"'");
                actionButton.setText("Edit");
                newTaskButton.setVisible(true);
                newTaskButton.setEnabled(true);
                actionButton.removeActionListener(actionListener);
                actionListener = new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            editTask();
                        }
                };
                actionButton.addActionListener(actionListener);
        }

        public void updateTaskToDatabase(Task task){
                SQLHandler.getInstance().updateTaskToDatabase(task);
                tasks.removeAllTasks();
        }

        public void setTaskID (Task task){
                taskID = task.getID();
        }



        public void editTask(){
                actionButton.setText("Save");
                enableFields();
                actionButton.removeActionListener(actionListener);
                actionListener = new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                submitTask();
                            } catch (ParseException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                        }
                };
                actionButton.addActionListener(actionListener);
        }

        public JPanel createActionButton(){
                JPanel field = new JPanel();
                JPanel actionButtonPanel = new JPanel();
                actionButton = new JButton("Submit");
                actionButtonPanel.add(actionButton);
                actionListener = new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                submitTask();
                            } catch (ParseException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                        }
                };
                actionButton.addActionListener(actionListener);
                field.setPreferredSize(new Dimension(120,45));
                field.add(actionButtonPanel);
                rowLength++;
                return field;
        }

        public JPanel createTitleTextArea(){
                JPanel field = new JPanel();
                titleTextArea = new JTextArea(1,2);
                titleTextArea.setLineWrap(true);
                titleTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                titleTextArea.setBackground(new Color(238, 238, 238));
                JScrollPane scrollPane = new JScrollPane(titleTextArea);
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
                scrollPane.setPreferredSize(new Dimension(200,40));	
                field.add(scrollPane);
                field.setLayout(new BoxLayout(field, BoxLayout.Y_AXIS));
                rowLength++;
                return field;
                }

        public String getTitleString(){
                return titleTextArea.getText();
        }

        public void setTitleString(String title){
                titleTextArea.setText(title);
        }

        public JPanel createDescriptionTextArea(){
                JPanel field = new JPanel();
                descriptionTextArea = new JTextArea(1,5);
                descriptionTextArea.setLineWrap(true);
                descriptionTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                descriptionTextArea.setBackground(new Color(238, 238, 238));
                JScrollPane scrollPane = new JScrollPane(descriptionTextArea);
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
                scrollPane.setPreferredSize(new Dimension(200,40));	
                field.add(scrollPane);
                field.setLayout(new BoxLayout(field, BoxLayout.Y_AXIS));
                rowLength++;

                return field;
                }

        public String getDescriptionString(){
                return descriptionTextArea.getText();
        }

        public void setDescriptionString(String description){
                descriptionTextArea.setText(description);
        }

        public JPanel createBucketComboBox() {
                JPanel field = new JPanel();

                String[] bucketOptions = getBucketOptions();
                bucketComboBox = new JComboBox<String>(bucketOptions);
                bucketComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                JPanel panel = new JPanel();
                panel.add(bucketComboBox);
                panel.setPreferredSize(new Dimension(200,38));
                field.add(panel,BorderLayout.WEST);
                field.setLayout(new BoxLayout(field, BoxLayout.Y_AXIS));
                rowLength++;
                return field;
        }

        public static String[] getBucketOptions() {
                return new String[] {"Quality Team","Environments Team","Change and Release Team","Operations Monitoring Team"};
        }


        public String getBucketString(){
                return (String) bucketComboBox.getSelectedItem();
        }

        public void setBucketString(String bucketOption){
                bucketComboBox.setSelectedItem(bucketOption);
        }

        public JPanel createFrequencyComboBox() {    
                JPanel field = new JPanel();

                String[] frequencyOptions = {"One Time", "Daily", "Weekly", "Monthly", "Yearly"};
                frequencyComboBox = new JComboBox<>(frequencyOptions);
                frequencyComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                JPanel panel = new JPanel();
                panel.add(frequencyComboBox);
                panel.setPreferredSize(new Dimension(100,38));
                field.add(panel,BorderLayout.WEST);
                field.setLayout(new BoxLayout(field, BoxLayout.Y_AXIS));
                rowLength++;
                return field;
        }

        public String getFrequencyString(){
                return (String) frequencyComboBox.getSelectedItem();
        }

        public void setFrequencyString(String frequencyOption){
                frequencyComboBox.setSelectedItem(frequencyOption);
        }

        public JPanel createAssignedEmailsDropDown() {
                // Fetch emails from the database
                emailOptions = SQLHandler.getInstance().getAllEmails();

                // Create the JTextArea to display selected emails
                assignedEmailsTextArea = new JTextArea(2, 20);
                assignedEmailsTextArea.setLineWrap(true);
                assignedEmailsTextArea.setWrapStyleWord(true);
                assignedEmailsTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                assignedEmailsTextArea.setBackground(new Color(238, 238, 238));
                assignedEmailsTextArea.setEditable(false);

                JScrollPane emailScrollPane = new JScrollPane(assignedEmailsTextArea);
                emailScrollPane.setPreferredSize(new Dimension(200, 40));

                // Create the button to open the dialog
                emailSelectButton = new JButton("Select Emails");
                emailSelectButton.addActionListener(e -> openEmailSelectionDialog());

                // Panel to hold everything
                JPanel field = new JPanel();
                field.setLayout(new BoxLayout(field, BoxLayout.X_AXIS));
                field.add(emailSelectButton);
                field.add(emailScrollPane);

                rowLength++;

                return field;
        }

        private void openEmailSelectionDialog() {
                JDialog dialog = new JDialog((java.awt.Frame) null, "Select Emails", true);
                dialog.setLayout(new BorderLayout());

                // Create the JList
                emailList = new JList<>(emailOptions.toArray(new String[0]));

                // Use a custom selection model to allow toggle without Ctrl
                DefaultListSelectionModel selectionModel = new DefaultListSelectionModel() {
                        @Override
                        public void setSelectionInterval(int index0, int index1) {
                        if (isSelectedIndex(index0)) {
                                removeSelectionInterval(index0, index1);
                        } else {
                                addSelectionInterval(index0, index1);
                        }
                        }
                };
                emailList.setSelectionModel(selectionModel);

                // Pre-select already assigned emails
                String[] currentEmails = assignedEmailsTextArea.getText().split(",\\s*");
                java.util.List<String> currentList = java.util.Arrays.asList(currentEmails);
                int[] indicesToSelect = currentList.stream()
                        .mapToInt(email -> emailOptions.indexOf(email))
                        .filter(i -> i >= 0)
                        .toArray();
                emailList.setSelectedIndices(indicesToSelect);

                JScrollPane scrollPane = new JScrollPane(emailList);
                scrollPane.setPreferredSize(new Dimension(250, 150));
                dialog.add(scrollPane, BorderLayout.CENTER);

                // Confirm button
                JButton confirmButton = new JButton("Confirm");
                confirmButton.addActionListener(e -> {
                        java.util.List<String> selected = emailList.getSelectedValuesList();
                        assignedEmailsTextArea.setText(String.join(", ", selected));
                        dialog.dispose();
                });

                JPanel buttonPanel = new JPanel();
                buttonPanel.add(confirmButton);
                dialog.add(buttonPanel, BorderLayout.SOUTH);

                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
        }



        public String getAssignedEmailsString(){
                return assignedEmailsTextArea.getText();
        }

        public void setAssignedEmailsString(String assignedEmailsString){
                assignedEmailsTextArea.setText(assignedEmailsString);

        }

        public JPanel createDueDatePicker() {
                JPanel field = new JPanel();

                model = new UtilDateModel();
                model.setSelected(true); // Default to today
                

                // DatePanel: allows configuration (optional)
                Properties p = new Properties();
                p.put("text.today", "Today");
                p.put("text.month", "Month");
                p.put("text.year", "Year");
                

                JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
                datePanel.setBackground(new Color(255, 255, 255));
                datePanel.setForeground(new Color(255, 255, 255));
                dueDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
                dueDatePicker.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                dueDatePicker.setBackground(new Color(255, 255, 255));


                JPanel panel = new JPanel();
                panel.add(dueDatePicker);
                panel.setPreferredSize(new Dimension(230,35));
                field.add(panel);
                field.setLayout(new BoxLayout(field, BoxLayout.Y_AXIS));
                //field.setBorder(new EmptyBorder(0,0,0,10));
                rowLength++;
                return field;
        }


        public String getDueDateString(){
                java.util.Date selectedDate = (java.util.Date) dueDatePicker.getModel().getValue();
                if (selectedDate != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        return sdf.format(selectedDate);
                } else {
                        return "";
                }
        }

        public void setDueDateString(String dueDate) {
                try {
                        
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        java.util.Date parsedDate = sdf.parse(dueDate);
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(parsedDate);
                        model.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                        model.setSelected(true);
                } catch (ParseException e) {
                        System.err.println("Invalid date format. Please use dd/MM/yyyy.");
                        e.printStackTrace();
                }
        }


        public void disableFields(){
                titleTextArea.setEnabled(false);
                titleTextArea.setBackground(new Color(238, 238, 238));
                titleTextArea.setBorder(BorderFactory.createLineBorder(new Color(187, 209, 230)));
                descriptionTextArea.setEnabled(false);
                descriptionTextArea.setBackground(new Color(238, 238, 238));
                descriptionTextArea.setBorder(BorderFactory.createLineBorder(new Color(187, 209, 230)));
                bucketComboBox.setEnabled(false);
                bucketComboBox.setBorder(BorderFactory.createLineBorder(new Color(187, 209, 230)));
                frequencyComboBox.setEnabled(false);
                frequencyComboBox.setBorder(BorderFactory.createLineBorder(new Color(187, 209, 230)));
                emailSelectButton.setEnabled(false);
                assignedEmailsTextArea.setEnabled(false);
                assignedEmailsTextArea.setBackground(new Color(238, 238, 238));
                assignedEmailsTextArea.setBorder(BorderFactory.createLineBorder(new Color(187, 209, 230)));
                disableDatePicker(dueDatePicker);
                dueDatePicker.setBorder(BorderFactory.createLineBorder(new Color(187, 209, 230)));
        }

        public void disableDatePicker(JDatePickerImpl datePicker) {
                // Disable the text field
                datePicker.getJFormattedTextField().setEnabled(false);
                // Use reflection to access and disable the popup butto
                try {
                        Field popupButtonField = JDatePickerImpl.class.getDeclaredField("button");
                        popupButtonField.setAccessible(true);
                        JButton popupButton = (JButton) popupButtonField.get(datePicker);
                        popupButton.setEnabled(false);
                } catch (Exception e) {
                        e.printStackTrace(); // Optional: handle/log the exception
                        }
                }

        public void enableFields(){
                ////// need to change some of these
                titleTextArea.setEnabled(true);
                titleTextArea.setBackground(new Color(238, 238, 238));
                titleTextArea.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
                descriptionTextArea.setEnabled(true);
                descriptionTextArea.setBackground(new Color(238, 238, 238));
                descriptionTextArea.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
                bucketComboBox.setEnabled(true);
                bucketComboBox.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
                frequencyComboBox.setEnabled(true);
                frequencyComboBox.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
                emailSelectButton.setEnabled(true);
                assignedEmailsTextArea.setEnabled(true);
                assignedEmailsTextArea.setBackground(new Color(238, 238, 238));
                assignedEmailsTextArea.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
                enableDatePicker(dueDatePicker);
                dueDatePicker.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        }

        public void enableDatePicker(JDatePickerImpl datePicker) {
                // Disable the text field
                datePicker.getJFormattedTextField().setEnabled(true);
                // Use reflection to access and disable the popup butto
                try {
                        Field popupButtonField = JDatePickerImpl.class.getDeclaredField("button");
                        popupButtonField.setAccessible(true);
                        JButton popupButton = (JButton) popupButtonField.get(datePicker);
                        popupButton.setEnabled(true);
                } catch (Exception e) {
                        e.printStackTrace(); // Optional: handle/log the exception
                }
        }
        
        public void initializeAsExistingTask(Task task) {
                this.task = task;
                this.taskID = task.getID();
                setTitleString(task.getTitle());
                setDescriptionString(task.getDescription());
                setBucketString(task.getBucket());
                setFrequencyString(task.getFrequency());
                setAssignedEmailsString(task.getAssignedEmails());
                setDueDateString(task.getDueDate());
                disableFields();
                actionButton.setText("Edit");
                actionButton.removeActionListener(actionListener);
                actionListener = e -> editTask();
                actionButton.addActionListener(actionListener);
        }



        

        
        public JPanel getRowPanel(){
                return row;
        }

        public void duplicate(){

        }

        public void delete(){

        }
        
}
