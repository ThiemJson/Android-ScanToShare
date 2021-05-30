package teneocto.thiemjason.tlu_connect.ui.register;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.regex.Pattern;

import teneocto.thiemjason.tlu_connect.models.SharedDTO;
import teneocto.thiemjason.tlu_connect.models.UserDTO;
import teneocto.thiemjason.tlu_connect.utils.AppConst;
import teneocto.thiemjason.tlu_connect.utils.Utils;

public class RegisterSocialNetworkViewModel extends ViewModel {
    public MutableLiveData<ArrayList<SharedDTO>> sharedDTOLiveData = new MutableLiveData<ArrayList<SharedDTO>>();
    public MutableLiveData<Boolean> isVerify = new MutableLiveData<Boolean>();
    private ArrayList<SharedDTO> arrayList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addShared(SharedDTO sharedDTO) {
        if (sharedDTOLiveData.getValue() == null) {
            sharedDTOLiveData.setValue(arrayList);
        }

        arrayList.add(sharedDTO);
        sharedDTOLiveData.setValue(arrayList);
        verifyUserInput();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void verifyUserInput() {
        for (SharedDTO sharedDTO : arrayList) {
            switch (Utils.getSocialNWDTOFromId(sharedDTO.getSocialNetWorkID()).getName()) {
                case "Facebook": {
                    Pattern pattern = Pattern.compile(AppConst.REGEX_Facebook_URL, Pattern.CASE_INSENSITIVE);
                    Boolean result = pattern.matcher(sharedDTO.getUrl()).matches();
                    if (!result) {
                        isVerify.setValue(false);
                        return;
                    }
                    break;
                }
                case "Instagram": {
                    Pattern pattern = Pattern.compile(AppConst.REGEX_Instagram_URL, Pattern.CASE_INSENSITIVE);
                    Boolean result = pattern.matcher(sharedDTO.getUrl()).matches();
                    if (!result) {
                        isVerify.setValue(false);
                        return;
                    }
                    break;
                }
                case "Snapchat": {
                    Pattern pattern = Pattern.compile(AppConst.REGEX_Snapchat_URL, Pattern.CASE_INSENSITIVE);
                    Boolean result = pattern.matcher(sharedDTO.getUrl()).matches();
                    if (!result) {
                        isVerify.setValue(false);
                        return;
                    }
                    break;
                }
                case "LinkedIn": {
                    Pattern pattern = Pattern.compile(AppConst.REGEX_LinkedIn_URL, Pattern.CASE_INSENSITIVE);
                    Boolean result = pattern.matcher(sharedDTO.getUrl()).matches();
                    if (!result) {
                        isVerify.setValue(false);
                        return;
                    }
                    break;
                }
                case "Twitter": {
                    Pattern pattern = Pattern.compile(AppConst.REGEX_Twitter_URL, Pattern.CASE_INSENSITIVE);
                    Boolean result = pattern.matcher(sharedDTO.getUrl()).matches();
                    if (!result) {
                        isVerify.setValue(false);
                        return;
                    }
                    break;
                }
            }
        }

        isVerify.setValue(true);
    }

    public void registerSocialNetwork() {

    }
}
