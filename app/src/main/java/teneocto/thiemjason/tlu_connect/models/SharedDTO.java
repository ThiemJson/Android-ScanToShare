package teneocto.thiemjason.tlu_connect.models;

public class SharedDTO {
    private int id;
    private int userID;
    private int socialNetWorkID;
    private String url;

    public SharedDTO(int id, int userID, int socialNetWorkID, String url) {
        this.id = id;
        this.userID = userID;
        this.socialNetWorkID = socialNetWorkID;
        this.url = url;
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

    public int getSocialNetWorkID() {
        return socialNetWorkID;
    }

    public void setSocialNetWorkID(int socialNetWorkID) {
        this.socialNetWorkID = socialNetWorkID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
