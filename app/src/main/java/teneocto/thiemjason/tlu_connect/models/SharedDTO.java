package teneocto.thiemjason.tlu_connect.models;

public class SharedDTO {
    private String id;
    private String userID;
    private String socialNetWorkID;
    private String url;

    public SharedDTO(String id, String userID, String socialNetWorkID, String url) {
        this.id = id;
        this.userID = userID;
        this.socialNetWorkID = socialNetWorkID;
        this.url = url;
    }

    public SharedDTO() {

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

    public String getSocialNetWorkID() {
        return socialNetWorkID;
    }

    public void setSocialNetWorkID(String socialNetWorkID) {
        this.socialNetWorkID = socialNetWorkID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
