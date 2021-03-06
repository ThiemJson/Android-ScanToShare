package teneocto.thiemjason.tlu_connect.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import teneocto.thiemjason.tlu_connect.database.DBConst;
import teneocto.thiemjason.tlu_connect.models.NotificationDTO;
import teneocto.thiemjason.tlu_connect.models.ScannedDTO;
import teneocto.thiemjason.tlu_connect.models.SharedDTO;
import teneocto.thiemjason.tlu_connect.models.SocialNetworkDTO;
import teneocto.thiemjason.tlu_connect.models.UserDTO;

/**
 *
 */
public class FirebaseDBHelper {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public FirebaseDBHelper() {
    }

    /**
     * ===========================> INSERT
     */
    public boolean USER_Insert(UserDTO userDTO) {
        try {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference(DBConst.USER_TABLE_NAME);
            databaseReference.child(userDTO.getId() + "").setValue(userDTO);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean SN_Insert(SocialNetworkDTO socialNetworkDTO) {
        try {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference(DBConst.SN_TABLE_NAME);
            databaseReference.child(socialNetworkDTO.getId() + "").setValue(socialNetworkDTO);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Notification_Insert(NotificationDTO notificationDTO) {
        try {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference(DBConst.NOTIFICATION_TABLE_NAME);
            databaseReference.child(notificationDTO.getUserID() + "").child(notificationDTO.getId() + "").setValue(notificationDTO);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Scanned_History_Insert(ScannedDTO scannedDTO) {
        try {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference(DBConst.SCAN_TABLE_NAME);
            databaseReference.child(scannedDTO.getUserId() + "").child(scannedDTO.getId() + "").setValue(scannedDTO);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean Shared_Insert(SharedDTO sharedDTO) {
        try {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference(DBConst.SHARED_TABLE_NAME);
            databaseReference.child(sharedDTO.getUserID() + "").child(sharedDTO.getId() + "").setValue(sharedDTO);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * ===========================> INSERT
     */
}
