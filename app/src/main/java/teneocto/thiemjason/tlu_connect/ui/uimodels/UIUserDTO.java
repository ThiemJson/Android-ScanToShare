package teneocto.thiemjason.tlu_connect.ui.uimodels;

public class UIUserDTO {
    String name;
    int image;
    String email;

    public UIUserDTO(String name, int image, String email) {
        this.name = name;
        this.image = image;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
