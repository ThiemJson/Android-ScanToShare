package teneocto.thiemjason.tlu_connect.ui.register;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.regex.Pattern;

import teneocto.thiemjason.tlu_connect.models.SharedDTO;
import teneocto.thiemjason.tlu_connect.utils.AppConst;
import teneocto.thiemjason.tlu_connect.utils.Utils;

public class RegisterSocialNetworkViewModel extends ViewModel {
    /**
     * Data
     */
    public ArrayList<SharedDTO> sharedDTOArrays = new ArrayList<SharedDTO>();
    public String errorField = "";

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addShared(SharedDTO sharedDTO) {
        if (sharedDTOArrays == null) {
            sharedDTOArrays = new ArrayList<SharedDTO>();
        }

        sharedDTOArrays.add(sharedDTO);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean verifyUserInput() {
        for (SharedDTO sharedDTO : sharedDTOArrays) {
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

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
