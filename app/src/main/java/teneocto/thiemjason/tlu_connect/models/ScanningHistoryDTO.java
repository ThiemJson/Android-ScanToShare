package teneocto.thiemjason.tlu_connect.models;

public class ScanningHistoryDTO {
    private String id;
    private String localUserID;
    private String remoteUserID;

    public ScanningHistoryDTO() {

    }

    public ScanningHistoryDTO(String id, String localUserID, String remoteUserID) {
        this.id = id;
        this.localUserID = localUserID;
        this.remoteUserID = remoteUserID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocalUserID() {
        return localUserID;
    }

    public void setLocalUserID(String localUserID) {
        this.localUserID = localUserID;
    }

    public String getRemoteUserID() {
        return remoteUserID;
    }

    public void setRemoteUserID(String remoteUserID) {
        this.remoteUserID = remoteUserID;
    }
}
