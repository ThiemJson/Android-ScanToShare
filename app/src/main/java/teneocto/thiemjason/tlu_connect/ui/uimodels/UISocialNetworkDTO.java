package teneocto.thiemjason.tlu_connect.ui.uimodels;

public class UISocialNetworkDTO {
    Integer logo;
    String link;

    public UISocialNetworkDTO(int logo, String link) {
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
