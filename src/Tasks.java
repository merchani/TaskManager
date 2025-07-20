import java.util.ArrayList;

public class Tasks {
    private static Tasks tasks;
    private static ArrayList<Task> taskArrayList;
    private static int taskID;

    private Tasks (){
        taskArrayList =  new ArrayList<Task>();
        taskID = 0;
    }

    public static synchronized Tasks getInstance(){
        if (tasks == null){
            tasks =  new Tasks();
        }
        return tasks;
    }

    public void addTask(Task task){
        taskArrayList.add(task);
    }

    public void removeTask(int index){
        taskArrayList.remove(index);
    }

    public void removeAllTasks(){
        taskArrayList.clear();
    }

    public Task get(int index){
        return taskArrayList.get(index);
    }

    public void setHighestTaskID(int id) {
        taskID = id;
    }
    public ArrayList<Task> getTaskList() {
        return taskArrayList;
    }


    public int size(){
        return taskArrayList.size();
    }

    
}
