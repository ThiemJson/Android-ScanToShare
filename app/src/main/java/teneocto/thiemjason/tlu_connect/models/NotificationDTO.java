package teneocto.thiemjason.tlu_connect.models;

public class NotificationDTO {
    private String id;
    private String userID;
    private String content;
    private String title;
    private String imageBase64;

    public NotificationDTO() {
    }

    public NotificationDTO(String id, String userID, String content, String title, String imageBase64) {
        this.id = id;
        this.userID = userID;
        this.content = content;
        this.title = title;
        this.imageBase64 = imageBase64;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
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
