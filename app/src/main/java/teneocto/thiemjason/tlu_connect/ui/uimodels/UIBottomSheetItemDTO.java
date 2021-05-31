package teneocto.thiemjason.tlu_connect.ui.uimodels;

public class UIBottomSheetItemDTO {
    Integer logo;
    String name;

    public UIBottomSheetItemDTO(Integer logo, String name) {
        this.logo = logo;
        this.name = name;
    }

    public Integer getLogo() {
        return logo;
    }

    public void setLogo(Integer logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
