package teneocto.thiemjason.tlu_connect.models;

public class NotificationDTO {
    private int id;
    private int userID;
    private String content;
    private String title;
    private String imageBase64;

    public NotificationDTO() {
    }

    public NotificationDTO(int id, int userID, String content, String title, String imageBase64) {
        this.id = id;
        this.userID = userID;
        this.content = content;
        this.title = title;
        this.imageBase64 = imageBase64;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
