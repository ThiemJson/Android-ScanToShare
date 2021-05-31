package teneocto.thiemjason.tlu_connect.ui.profile;

import android.os.Build;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import teneocto.thiemjason.tlu_connect.database.DBConst;
import teneocto.thiemjason.tlu_connect.models.SharedDTO;
import teneocto.thiemjason.tlu_connect.models.UserDTO;
import teneocto.thiemjason.tlu_connect.utils.AppConst;
import teneocto.thiemjason.tlu_connect.utils.Utils;

public class ProfileSocialNetworkViewModel extends ViewModel {
    public ArrayList<SharedDTO> sharedDTOLiveData = new ArrayList<>();
    public MutableLiveData<Boolean> dataFetched = new MutableLiveData<Boolean>();
    public String errorField = "";

    public void addShared(SharedDTO sharedDTO){
        if (sharedDTOLiveData == null) {
            sharedDTOLiveData = new ArrayList<>();
        }

        sharedDTOLiveData.add(sharedDTO);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean verifyUserInput() {
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
            sharedDTOLiveData = new ArrayList<>();
        }

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(DBConst.SHARED_TABLE_NAME);
        databaseReference.child(AppConst.SP_CURRENT_USER_ID + "").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        sharedDTOLiveData.add(data.getValue(SharedDTO.class));
                    }
                }

                dataFetched.setValue(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadDataFromSQLite();
            }
        });
    }

    public void loadDataFromSQLite(){

    }

    @Override
    protected void onCleared() {

    }
}
