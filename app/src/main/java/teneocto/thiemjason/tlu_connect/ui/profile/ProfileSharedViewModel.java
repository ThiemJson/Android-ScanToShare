package teneocto.thiemjason.tlu_connect.ui.profile;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Pattern;

import teneocto.thiemjason.tlu_connect.database.DBConst;
import teneocto.thiemjason.tlu_connect.firebase.FirebaseDBHelper;
import teneocto.thiemjason.tlu_connect.models.SharedDTO;
import teneocto.thiemjason.tlu_connect.models.UserDTO;
import teneocto.thiemjason.tlu_connect.utils.AppConst;
import teneocto.thiemjason.tlu_connect.utils.Utils;

public class ProfileSharedViewModel extends ViewModel {

    // Data
    public ArrayList<SharedDTO> sharedDTOLiveData = new ArrayList<>();
    public UserDTO userDTO = new UserDTO();

    /**
     * Check user social nw fetched from internet
     */
    public MutableLiveData<Boolean> dataFetched = new MutableLiveData<Boolean>();

    /**
     * Check user information fetched from internet
     */
    public MutableLiveData<Boolean> userDataFetched = new MutableLiveData<Boolean>();

    /**
     * Check user cancel of save information and how / show tool button
     */
    public MutableLiveData<Boolean> hideShowBtnTool = new MutableLiveData<Boolean>();

    /**
     * Check when user changed data and delete progress bar dialog
     */
    public MutableLiveData<Boolean> isDataReverted = new MutableLiveData<Boolean>();

    /**
     * Check when user updated data failure
     * TRUE: When user want to close after that
     * FALSE: When user doesn't want to close after that
     */
    public MutableLiveData<Boolean> isDataUpdatedFailure = new MutableLiveData<>();

    /**
     * Check user updated new information ()
     * TRUE: when user want to close after information updated
     * FALSE: when user doesn't want to close after information updated
     */
    public MutableLiveData<Boolean> isDataUpdated = new MutableLiveData<Boolean>();


    // Old data
    public ArrayList<SharedDTO> oldSharedDTOs = new ArrayList<>();
    public UserDTO oldUserDTO = new UserDTO();

    // Debugs variables
    public String errorField = "";

    public void addShared(SharedDTO sharedDTO) {
        if (sharedDTOLiveData == null) {
            sharedDTOLiveData = new ArrayList<>();
        }

        sharedDTOLiveData.add(sharedDTO);
    }

    /**
     * Check user social network input
     *
     * @return boolean
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean verifySocialNetworkInput() {
        for (SharedDTO sharedDTO : sharedDTOLiveData) {
            switch (Utils.getSocialNWDTOFromId(sharedDTO.getSocialNetWorkID()).getName()) {
                case "Facebook": {
                    Pattern pattern = Pattern.compile(AppConst.REGEX_Facebook_URL, Pattern.CASE_INSENSITIVE);
                    Boolean result = pattern.matcher(sharedDTO.getUrl().trim()).matches();
                    if (!result) {
                        errorField = Utils.getSocialNWDTOFromId(sharedDTO.getSocialNetWorkID()).getName() + " " + sharedDTO.getUrl().trim();
                        return false;
                    }
                    break;
                }
                case "Instagram": {
                    Pattern pattern = Pattern.compile(AppConst.REGEX_Instagram_URL, Pattern.CASE_INSENSITIVE);
                    Boolean result = pattern.matcher(sharedDTO.getUrl()).matches();
                    if (!result) {
                        errorField = Utils.getSocialNWDTOFromId(sharedDTO.getSocialNetWorkID()).getName() + " " + sharedDTO.getUrl().trim();
                        return false;
                    }
                    break;
                }
                case "Snapchat": {
                    Pattern pattern = Pattern.compile(AppConst.REGEX_Snapchat_URL, Pattern.CASE_INSENSITIVE);
                    Boolean result = pattern.matcher(sharedDTO.getUrl()).matches();
                    if (!result) {
                        errorField = Utils.getSocialNWDTOFromId(sharedDTO.getSocialNetWorkID()).getName() + " " + sharedDTO.getUrl().trim();
                        return false;
                    }
                    break;
                }
                case "LinkedIn": {
                    Pattern pattern = Pattern.compile(AppConst.REGEX_LinkedIn_URL, Pattern.CASE_INSENSITIVE);
                    Boolean result = pattern.matcher(sharedDTO.getUrl()).matches();
                    if (!result) {
                        errorField = Utils.getSocialNWDTOFromId(sharedDTO.getSocialNetWorkID()).getName() + " " + sharedDTO.getUrl().trim();
                        return false;
                    }
                    break;
                }
                case "Twitter": {
                    Pattern pattern = Pattern.compile(AppConst.REGEX_Twitter_URL, Pattern.CASE_INSENSITIVE);
                    Boolean result = pattern.matcher(sharedDTO.getUrl()).matches();
                    if (!result) {
                        errorField = Utils.getSocialNWDTOFromId(sharedDTO.getSocialNetWorkID()).getName() + " " + sharedDTO.getUrl().trim();
                        return false;
                    }
                    break;
                }
            }
        }
        return true;
    }

    /**
     * ===================> DATA
     */
    public void loadDataFromFirebase() {
        if (sharedDTOLiveData == null) {
            dataFetched.setValue(false);
            sharedDTOLiveData = new ArrayList<>();
        }

        if (userDTO == null) {
            userDTO = new UserDTO();
        }

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(DBConst.SHARED_TABLE_NAME);
        databaseReference.child(AppConst.USER_UID_Static + "").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        sharedDTOLiveData.add(data.getValue(SharedDTO.class));
                        Log.i(AppConst.TAG_RegisterProfileViewModel, " ==> List count" + sharedDTOLiveData.size());
                    }
                    dataFetched.setValue(true);
                }

                // Store data
                if (oldSharedDTOs != null) {
                    oldSharedDTOs = new ArrayList<>();
                }
                Log.i(AppConst.TAG_RegisterProfileViewModel, " ==> Add new data" + sharedDTOLiveData.size());
                oldSharedDTOs = Utils.cloneSharedDTO(sharedDTOLiveData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadDataFromSQLite();
            }
        });

        databaseReference = firebaseDatabase.getReference(DBConst.USER_TABLE_NAME);
        databaseReference.child(AppConst.USER_UID_Static + "").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {
                    userDTO = snapshot.getValue(UserDTO.class);
                    userDataFetched.setValue(true);
                    oldUserDTO = Utils.cloneUserDTO(userDTO);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadDataFromSQLite();
            }
        });
    }

    public void loadDataFromSQLite() {

    }

    @Override
    protected void onCleared() {

    }

    /**
     * Revert Data
     */
    public void revertData() {
        Log.i(AppConst.TAG_ProfileSharedViewModel, "old shared: " + oldSharedDTOs.size());
        Log.i(AppConst.TAG_ProfileSharedViewModel, "new shared: " + sharedDTOLiveData.size());

        // Old data
        userDTO = Utils.cloneUserDTO(oldUserDTO);

        // Notify changed;
        userDataFetched.setValue(true);

        hideShowBtnTool.setValue(false);
        isDataReverted.setValue(true);
    }

    /**
     * Update new information
     */
    public void updateUserInformation(Boolean closeAfterUpdated) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        // Update profile
        DatabaseReference databaseReference = firebaseDatabase.getReference(DBConst.USER_TABLE_NAME);
        databaseReference.child(userDTO.getId()).setValue(userDTO)
                .addOnSuccessListener(aVoid -> {
                    // Update social network
                    DatabaseReference _databaseReference1 = firebaseDatabase.getReference(DBConst.SHARED_TABLE_NAME);
                    _databaseReference1.child(userDTO.getId()).removeValue((error, ref) -> {
                        FirebaseDBHelper firebaseDBHelper = new FirebaseDBHelper();
                        for (SharedDTO sharedDTO : sharedDTOLiveData){
                            firebaseDBHelper.Shared_Insert(sharedDTO);
                        }

                        // DONE
                        hideShowBtnTool.setValue(false);
                        isDataUpdated.setValue(closeAfterUpdated);
                        Log.i(AppConst.TAG_ProfileSharedViewModel, "Updated information");

                        // Set flag check
                        Utils.isUserUpdatedData = true;
                    });
                })
                .addOnCanceledListener(() ->
                {
                    isDataUpdatedFailure.setValue(closeAfterUpdated);
                    Log.i(AppConst.TAG_ProfileSharedViewModel, "Cancel Updated information ");
                });

    }
}
