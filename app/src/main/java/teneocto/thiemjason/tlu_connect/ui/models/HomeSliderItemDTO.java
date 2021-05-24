package teneocto.thiemjason.tlu_connect.ui.models;

public class HomeSliderItemDTO {
    private int image;
    private String name;
    private String qrImage;
    private String url;

    public HomeSliderItemDTO(int image, String name, String qrImage, String url) {
        this.image = image;
        this.name = name;
        this.qrImage = qrImage;
        this.url = url;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQrImage() {
        return qrImage;
    }

    public void setQrImage(String qrImage) {
        this.qrImage = qrImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
