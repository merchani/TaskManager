import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;

public class SQLHandler {

    // Path to the .db file (change if needed)
    private static SQLHandler sqlHandler;
    private static final String DB_PATH = "C:\\Users\\imerchan\\Documents\\GitHub\\TaskManager\\TaskManager\\src\\tasks_users_new.db";

    // SQL schema as a multi-line string
    private static final String TASKS_USERS_SCHEMA_SQL = """
        CREATE TABLE IF NOT EXISTS tasks_table (
            ID INTEGER PRIMARY KEY AUTOINCREMENT,
            title TEXT NOT NULL,
            description TEXT NOT NULL,
            assignedEmails TEXT NOT NULL,
            bucket TEXT NOT NULL,
            frequency TEXT NOT NULL,
            dueDate DATE NOT NULL
        );

        CREATE TABLE IF NOT EXISTS users_table (
        ID INTEGER PRIMARY KEY,
        forename TEXT NOT NULL,
        surname TEXT NOT NULL,
        email TEXT NOT NULL,
        username TEXT NOT NULL,
        password TEXT NOT NULL
        );

        CREATE TABLE IF NOT EXISTS emails_table (
            ID INTEGER PRIMARY KEY,
            emailText TEXT NOT NULL
        );

        CREATE TABLE IF NOT EXISTS frequencies_table (
            ID INTEGER PRIMARY KEY,
            frequencyText TEXT NOT NULL
        );

        CREATE TABLE IF NOT EXISTS buckets_table (
            ID INTEGER PRIMARY KEY,
            bucketText TEXT NOT NULL
        );
        """;

    private SQLHandler() {
        validateAndRecreateDatabase();
        Connection conn = getConnection();
        if (conn != null) {
            createSchema(conn);
        } else {
            System.out.println("Failed to create schema: connection is null.");
        }

    }

    private void validateAndRecreateDatabase() {
        File dbFile = new File(DB_PATH);
        try (Connection testConn = DriverManager.getConnection(DBURL());
        Statement stmt = testConn.createStatement()) {
            stmt.execute("SELECT name FROM sqlite_master WHERE type='table';");
            System.out.println("Database is valid.");
        } catch (SQLException e) {
            System.out.println("Invalid database file. Recreating...");
            if (dbFile.exists()) {
                boolean deleted = dbFile.delete();
                if (deleted) {
                    System.out.println("Old database file deleted.");
                } else {
                    System.out.println("Failed to delete old database file.");
                    return; // Stop here if deletion failed
                }
            }
            recreateDatabase(); // Your method to create schema and file
        }
    }

    public static synchronized SQLHandler getInstance(){
        if (sqlHandler == null) {
            sqlHandler = new SQLHandler();
        }
        return sqlHandler;
    }

    public static Connection getConnection() {
            try {
                Connection conn = DriverManager.getConnection(DBURL());
                System.out.println("Connected to database at: " + DB_PATH);
                return conn;
            } catch (SQLException e) {
                System.out.println("Connection failed:");
                e.printStackTrace();
                return null;
            }
    }

    public static String DBURL(){
        return "jdbc:sqlite:" + DB_PATH;
    }

    public void listAllTables() {
        String sql = "SELECT name FROM sqlite_master WHERE type='table'";
        try (Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Tables in database:");
            while (rs.next()) {
                System.out.println("- " + rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("Error listing tables: " + e.getMessage());
        }
    }


    public void createSchema(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            String[] statements = TASKS_USERS_SCHEMA_SQL.split(";");
            for (String sql : statements) {
                sql = sql.trim();
                if (!sql.isEmpty()) {
                    stmt.execute(sql + ";");
                    System.out.println("Executed: " + sql);
                }
            }
            System.out.println("Database schema created successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to create schema:");
            e.printStackTrace();
        }
    }

    

    private void recreateDatabase() {
        String[] schemaStatements = TASKS_USERS_SCHEMA_SQL.split(";");
        try (Connection conn = DriverManager.getConnection(DBURL());
            Statement stmt = conn.createStatement()) {

            for (String sql : schemaStatements) {
                if (!sql.trim().isEmpty()) {
                    stmt.executeUpdate(sql.trim() + ";");
                }
            }
            System.out.println("Database recreated successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to recreate database:");
            e.printStackTrace();
        }
    }




    /**
     * Insert a new task into tasks_table.
     * @param id Task ID
     * @param forename User forename
     * @param description Task description
     * @param assignedEmails Comma-separated emails assigned
     * @param bucket Bucket name
     * @param frequency Frequency string
     * @param dueDate Due date (java.sql.Date)
     * @return true if insert successful, false otherwise
     */
    public boolean insertUser(int id, String forename, String surname, String email, String username, String password) {
        String sql = "INSERT INTO users_table (ID, forename, surname, email, username, password) VALUES (?, ?, ?, ?, ?, ?);";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, forename);
            pstmt.setString(3, surname);
            pstmt.setString(4, email);
            pstmt.setString(5, username);
            pstmt.setString(6, password);

            pstmt.executeUpdate();
            insertEmail(email);
            return true;
        } catch (SQLException e) {
            System.out.println("Insert user failed:");
            e.printStackTrace();
            return false;
        }
    }
    
    public void insertEmail(String email) {
        String query = "INSERT INTO emails_table (emailText) VALUES (?)";
        try {
            PreparedStatement stmt = getConnection().prepareStatement(query);
            stmt.setString(1, email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Insert a new task into tasks_table.
     * @param title Task title
     * @param description Task description
     * @param assignedEmails Comma-separated emails assigned
     * @param bucket Bucket name
     * @param frequency Frequency string
     * @param dueDate Due date (java.sql.Date)
     * @return true if insert successful, false otherwise
     */
    public int saveNewTaskToDatabase(Task task) {
        String sql = "INSERT INTO tasks_table (title, description, assignedEmails, bucket, frequency, dueDate) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, task.getTitle());
            pstmt.setString(2, task.getDescription());
            pstmt.setString(3, task.getAssignedEmails());
            pstmt.setString(4, task.getBucket());
            pstmt.setString(5, task.getFrequency());
            pstmt.setString(6, task.getDueDate());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Return the generated ID
                }
            }

        } catch (SQLException e) {
            System.out.println("Failed to save task: " + e.getMessage());
        }
        return -1; // Error case
    }


    public boolean updateTask(Connection conn, int id, String title, String description, String assignedEmails, String bucket, String frequency, Date dueDate) {
        String sql = "UPDATE tasks_table SET title = ?, description = ?, assignedEmails = ?, bucket = ?, frequency = ?, dueDate = ? WHERE ID = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setString(3, assignedEmails);
            pstmt.setString(4, bucket);
            pstmt.setString(5, frequency);
            pstmt.setDate(6, dueDate);
            pstmt.setInt(7, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Update task failed:");
            e.printStackTrace();
            return false;
        }
    }

    public int getMaxTaskId() {
        int maxId = 0;
        String query = "SELECT MAX(id) FROM tasks_table";

        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                maxId = rs.getInt(1); // Will return 0 if table is empty
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maxId+1;
    }

    public void inspectTaskIdSequence() {
        String sql = "SELECT seq FROM sqlite_sequence WHERE name='tasks_table'";
        try (Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                int currentId = rs.getInt("seq");
                System.out.println("Current AUTOINCREMENT value for tasks_table: " + currentId);
            } else {
                System.out.println("No entry found in sqlite_sequence for tasks_table. It may not have any rows yet.");
            }

        } catch (SQLException e) {
            System.out.println("Failed to inspect task ID sequence: " + e.getMessage());
        }
    }



    public void loadUsersFromDatabase() {
    String sql = "SELECT * FROM users_table;";
    try (PreparedStatement pstmt = getConnection().prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {
            //clearTasksTable();
            Users users = Users.getInstance();
            users.removeAllUsers(); // Clear existing in-memory users
            int maxID = 0;
            while (rs.next()) {
                User user = new User();
                int id = rs.getInt("ID");
                user.setID(id);
                user.setForename(rs.getString("forename"));
                user.setSurname(rs.getString("surname"));
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                users.getUserList().add(user);
                if(id>maxID) maxID = id;
                if(id == 0){
                    System.out.println("No users to load");

                }
            }
            users.setHighestUserID(maxID+1);
            System.out.println("Users loaded from database successfully. Total: " + users.getUserList().size());
        } catch (SQLException e) {
            System.out.println("Failed to load users from database:");
            e.printStackTrace();
        }
    }


    public void loadTasksFromDatabase() {
        String sql = "SELECT * FROM tasks_table;";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {
            //clearTasksTable();
            Tasks tasks = Tasks.getInstance();
            tasks.removeAllTasks(); // Clear existing tasks before loading new ones
            int maxID = 0;
            while (rs.next()) {
                Task task = new Task();
                int id = rs.getInt("ID");
                task.setID(id);
                task.setTitle(rs.getString("title"));
                task.setDescription(rs.getString("description"));
                task.setAssignedEmails(rs.getString("assignedEmails"));
                task.setBucket(rs.getString("bucket"));
                task.setFrequency(rs.getString("frequency"));
                task.setDueDate(formatDate(rs.getLong("dueDate"))); // Assuming dueDate is stored as a string
                tasks.getTaskList().add(task); 
                if (id>maxID) maxID = id;
            }
            tasks.setHighestTaskID(maxID+1);

            System.out.println("Tasks loaded from database successfully. Tasks size: "+tasks.size());

        } catch (SQLException e) {
            System.out.println("Failed to load tasks from database:");
            e.printStackTrace();
        }
    }

    public String formatDate(long millis){
        java.util.Date date = new java.util.Date(millis);
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public void clearTasksTable() {
        String sql = "DELETE FROM tasks_table;";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            int rowsDeleted = pstmt.executeUpdate();
            System.out.println("All tasks deleted from tasks_table. Rows affected: " + rowsDeleted);
        }
        catch (SQLException e) {
            System.out.println("Failed to delete tasks from tasks_table:");
            e.printStackTrace();
        }
    }

    

    public void clearUsersTable() {
        String sql = "DELETE FROM users_table;";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            int rowsDeleted = pstmt.executeUpdate();
            System.out.println("All users deleted from users_table. Rows affected: " + rowsDeleted);
        }
        catch (SQLException e) {
            System.out.println("Failed to delete tasks from tasks_table:");
            e.printStackTrace();
        }
    }

    public boolean deleteTaskByID(int id) {
        SQLHandler.getInstance().inspectTaskIdSequence();

        String sql = "DELETE FROM tasks_table WHERE ID = ?;";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Task with ID " + id + " deleted from database.");
                return true;
            } else {
                System.out.println("No task found with ID " + id + ".");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Failed to delete task with ID " + id + ":");
            e.printStackTrace();
            return false;
        }
    }

public boolean updateTaskDueDateByFrequency(int id) {
    String selectSql = "SELECT dueDate, frequency FROM tasks_table WHERE ID = ?;";
    String updateSql = "UPDATE tasks_table SET dueDate = ? WHERE ID = ?;";

    try (PreparedStatement selectStmt = getConnection().prepareStatement(selectSql)) {
        selectStmt.setInt(1, id);
        ResultSet rs = selectStmt.executeQuery();

        if (rs.next()) {
            Date currentDueDate = rs.getDate("dueDate");
            String frequency = rs.getString("frequency");

            // Calculate new due date
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTime(currentDueDate);

            switch (frequency.toLowerCase()) {
                case "daily":
                    calendar.add(java.util.Calendar.DATE, 1);
                    break;
                case "weekly":
                    calendar.add(java.util.Calendar.DATE, 7);
                    break;
                case "monthly":
                    calendar.add(java.util.Calendar.MONTH, 1);
                    break;
                case "yearly":
                    calendar.add(java.util.Calendar.YEAR, 1);
                    break;
                default:
                    System.out.println("Unknown frequency: " + frequency);
                    return false;
            }

            Date newDueDate = new Date(calendar.getTimeInMillis());

            try (PreparedStatement updateStmt = getConnection().prepareStatement(updateSql)) {
                updateStmt.setDate(1, newDueDate);
                updateStmt.setInt(2, id);
                updateStmt.executeUpdate();
                System.out.println("Due date updated for task ID " + id + " to " + newDueDate);
                return true;
            }
        } else {
            System.out.println("No task found with ID " + id);
            return false;
        }
    } catch (SQLException e) {
        System.out.println("Failed to update due date for task ID " + id + ":");
        e.printStackTrace();
        return false;
    }
}



    public ArrayList<String> getAllEmails() {
        ArrayList<String> emails = new ArrayList<>();
        String sql = "SELECT emailText FROM emails_table;";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                emails.add(rs.getString("emailText"));
            }
            System.out.println("number of emails is: "+emails.size());
        } catch (SQLException e) {
            System.out.println("Failed to fetch emails:");
            e.printStackTrace();
        }
        return emails;
    }

    public void clearEmailsTable() {
        String query = "DELETE FROM emails_table";

        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.executeUpdate();
            System.out.println("All emails have been cleared from emails_table.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}