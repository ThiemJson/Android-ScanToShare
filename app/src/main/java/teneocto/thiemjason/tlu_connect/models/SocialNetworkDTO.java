package teneocto.thiemjason.tlu_connect.models;

public class SocialNetworkDTO {
    private String id;
    private String name;
    private String imageBase64;

    public SocialNetworkDTO() {

    }

    public SocialNetworkDTO(String id, String name, String imageBase64) {
        this.id = id;
        this.name = name;
        this.imageBase64 = imageBase64;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
