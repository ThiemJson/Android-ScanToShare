package teneocto.thiemjason.tlu_connect.ui.scanninghistory;

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
import teneocto.thiemjason.tlu_connect.models.ScanningHistoryDTO;
import teneocto.thiemjason.tlu_connect.models.UserDTO;
import teneocto.thiemjason.tlu_connect.utils.Utils;

public class ScanningHistoryViewModel extends ViewModel {
    public ArrayList<UserDTO> mUserScanned = new ArrayList<>();
    public MutableLiveData<Boolean> isFetched = new MutableLiveData<Boolean>();

    /**
     * ===========================> DATA
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void loadDataFromFirebase() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(DBConst.SCAN_TABLE_NAME);
        databaseReference.child("1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        ScanningHistoryDTO scanningHistoryDTO = data.getValue(ScanningHistoryDTO.class);
                        UserDTO userDTO = Utils.getUserDTOFromId(scanningHistoryDTO.getRemoteUserID());
                        mUserScanned.add(userDTO);
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
