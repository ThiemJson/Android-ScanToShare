package teneocto.thiemjason.tlu_connect.models;

public class SharedDTO {
    private int id;
    private int userId;
    private int scanHisId;
    private String url;

    public SharedDTO(int id, int userId, int scanHisId, String url) {
        this.id = id;
        this.userId = userId;
        this.scanHisId = scanHisId;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getScanHisId() {
        return scanHisId;
    }

    public void setScanHisId(int scanHisId) {
        this.scanHisId = scanHisId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
