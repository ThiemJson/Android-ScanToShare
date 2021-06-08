package teneocto.thiemjason.tlu_connect.ui.scanninghistory;

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
import teneocto.thiemjason.tlu_connect.models.ScannedDTO;
import teneocto.thiemjason.tlu_connect.models.UserDTO;
import teneocto.thiemjason.tlu_connect.utils.AppConst;
import teneocto.thiemjason.tlu_connect.utils.Utils;

/**
 * Scanning History View Models
 */
public class ScanningHistoryViewModel extends ViewModel {
    /**
     * Data
     */
    public ArrayList<ScannedDTO> mUserScanned = new ArrayList<>();

    /**
     * Flag check
     */
    public MutableLiveData<Boolean> isFetched = new MutableLiveData<Boolean>();

    /**
     * ===========================> DATA
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void loadDataFromFirebase() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(DBConst.SCAN_TABLE_NAME);
        databaseReference.child(AppConst.USER_UID_Static).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        ScannedDTO scannedDTO = data.getValue(ScannedDTO.class);
                        mUserScanned.add(scannedDTO);
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

    private void loadDataFromSQLite() {
    }
}
