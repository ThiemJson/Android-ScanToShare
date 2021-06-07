package teneocto.thiemjason.tlu_connect.models;

public class ScannedResultDTO {
    private String name;

    private String id;

    private String url;

    private String socialNetWorkID;

    private String imageBase64;

    private int socialNWIcon;

    private String socialNWName;

    public int getSocialNWIcon() {
        return socialNWIcon;
    }

    public void setSocialNWIcon(int socialNWIcon) {
        this.socialNWIcon = socialNWIcon;
    }

    public String getSocialNWName() {
        return socialNWName;
    }

    public void setSocialNWName(String socialNWName) {
        this.socialNWName = socialNWName;
    }

    public ScannedResultDTO(String name, String id, String url, String socialNetWorkID, String imageBase64) {
        this.name = name;
        this.id = id;
        this.url = url;
        this.socialNetWorkID = socialNetWorkID;
        this.imageBase64 = imageBase64;
    }

    public ScannedResultDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSocialNetWorkID() {
        return socialNetWorkID;
    }

    public void setSocialNetWorkID(String socialNetWorkID) {
        this.socialNetWorkID = socialNetWorkID;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
