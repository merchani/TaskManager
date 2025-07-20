
public class Task {
    private Integer id;
    private String title;
    private String description;
    private String assignedEmails;
    private String bucket;
    private String frequency;
    private String dueDate;

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getAssignedEmails(){
        return assignedEmails;
    }

    public void setAssignedEmails(String assignedEmails){
        this.assignedEmails = assignedEmails;
    }

    public String getBucket(){
        return bucket;
    }

    public void setBucket(String bucket){
        this.bucket = bucket;
    }

    public String getFrequency(){
        return frequency;
    }

    public void setFrequency(String frequency){
        this.frequency = frequency;
    }

    public String getDueDate(){
        return dueDate;
    }

    public void setDueDate(String dueDate){
        this.dueDate = dueDate;
    }

}
