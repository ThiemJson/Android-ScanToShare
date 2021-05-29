package teneocto.thiemjason.tlu_connect.models;

public class ScanningHistoryDTO {
    private int id;
    private int localUserID;
    private int remoteUserID;

    public ScanningHistoryDTO() {

    }

    public ScanningHistoryDTO(int id, int localUserID, int remoteUserID) {
        this.id = id;
        this.localUserID = localUserID;
        this.remoteUserID = remoteUserID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLocalUserID() {
        return localUserID;
    }

    public void setLocalUserID(int localUserID) {
        this.localUserID = localUserID;
    }

    public int getRemoteUserID() {
        return remoteUserID;
    }

    public void setRemoteUserID(int remoteUserID) {
        this.remoteUserID = remoteUserID;
    }
}
