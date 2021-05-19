package teneocto.thiemjason.tlu_connect.ui.models;

public class SocialNetwork {
    Integer logo;
    String link;

    public SocialNetwork(int logo, String link) {
        this.logo = logo;
        this.link = link;
    }

    public Integer getLogo() {
        return logo;
    }

    public void setLogo(Integer logo) {
        this.logo = logo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
