package teneocto.thiemjason.tlu_connect.ui.notification;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.database.DBConst;
import teneocto.thiemjason.tlu_connect.models.NotificationDTO;
import teneocto.thiemjason.tlu_connect.utils.AppConst;

public class NotificationViewModel extends ViewModel {
    public ArrayList<NotificationDTO> notificationDTOArrayList = new ArrayList<>();
    public MutableLiveData<Boolean>  isFetched = new MutableLiveData<>();

    /**
     * ===========================> DATA
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void loadDataFromFirebase() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(DBConst.NOTIFICATION_TABLE_NAME);
        databaseReference.child(AppConst.USER_UID_Static).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        NotificationDTO notificationDTO = data.getValue(NotificationDTO.class);
                        if (checkNotificationDuplicate(notificationDTO)) {
                            notificationDTOArrayList.add(data.getValue(NotificationDTO.class));
                        }
                    }
                    isFetched.setValue(true);
                }
                isFetched.setValue(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadDataFromSQLite();
            }
        });
    }

    private Boolean checkNotificationDuplicate(NotificationDTO _notificationDTO) {
        for (NotificationDTO notificationDTO : notificationDTOArrayList) {
            if (notificationDTO.getId().equals(_notificationDTO.getId())) {
                return false;
            }
        }
        return true;
    }

    private void loadDataFromSQLite() {

    }
}
