package teneocto.thiemjason.tlu_connect.models;

public class HomeSliderItem {
    private int image;
    private String name;
    private int qrImage;
    private String url;

    public HomeSliderItem(int image, String name, int qrImage, String url) {
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

    public int getQrImage() {
        return qrImage;
    }

    public void setQrImage(int qrImage) {
        this.qrImage = qrImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
