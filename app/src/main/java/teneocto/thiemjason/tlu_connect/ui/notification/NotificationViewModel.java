package teneocto.thiemjason.tlu_connect.ui.notification;

import android.os.Build;
import android.view.View;

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
    public MutableLiveData<Boolean> isFetched = new MutableLiveData<>();

    /**
     * ===========================> DATA
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void loadDataFromFirebase() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(DBConst.NOTIFICATION_TABLE_NAME);
        databaseReference.child(AppConst.SP_CURRENT_USER_ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        notificationDTOArrayList.add(data.getValue(NotificationDTO.class));
                    }
                    isFetched.setValue(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadDataFromSQLite();
            }
        });
    }


    private void loadDataFromSQLite() {

    }
}
