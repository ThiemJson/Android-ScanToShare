package teneocto.thiemjason.tlu_connect.models;

import android.widget.ImageView;

public class Social {
    ImageView logo;
    String link;

    public Social(ImageView logo, String link) {
        this.logo = logo;
        this.link = link;
    }

    public ImageView getLogo() {
        return logo;
    }

    public void setLogo(ImageView logo) {
        this.logo = logo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
