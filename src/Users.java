
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

  
import java.util.ArrayList;


public class Users {
    private static Users users;
    private static int userID;
    private ArrayList<User> userArrayList;
    static final String DB_URL = "C://localhost/";
    static final String USER = "guest";
    static final String PASS = "guest123";

    private Users (){
        userArrayList = new ArrayList<User>();
        userID = 0;
    }

    public static synchronized Users getInstance(){
        if (users == null){
            users = new Users();
        }
        return users;
    }

    public void setHighestUserID(int id){
        userID = id;
    }

    public void addUser(User user){
        user.setID(userID);
        userArrayList.add(user);
        userID++;
    };

    public User getUserByUsername(String username){
        int userInt;
        for (int i = 0; i < userArrayList.size(); i++){
            String currUsername = userArrayList.get(i).getUsername();
            if (currUsername.equals(username)){
                userInt = i;
                return userArrayList.get(userInt);
            }
        }
         
        User emptyUser = new User();
        emptyUser.setForename("No User Exists");
        return emptyUser;
    }

    public Boolean checkUsername(String username){
        for (int i = 0; i < userArrayList.size(); i++){
            String currUsername = userArrayList.get(i).getUsername();
            if (currUsername.equals(username)){
                return true;
            }
        }
        return false;
    }

    public Boolean checkPassword(String username, String password){
        for (int i = 0; i < userArrayList.size(); i++){
            User tempuser = userArrayList.get(i);
            String currUsername = tempuser.getUsername();
            if (currUsername.equals(username)){
                if (tempuser.getPassword().equals(password)){
                    return true;
                }
            }
        }
        return false;
    }

    public void removeAllUsers(){
        userArrayList.clear();
    }

    public ArrayList<User> getUserList() {
        return userArrayList;
    }


}
