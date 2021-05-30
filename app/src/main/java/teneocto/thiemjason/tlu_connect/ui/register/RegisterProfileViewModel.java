package teneocto.thiemjason.tlu_connect.ui.register;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.regex.Pattern;

import teneocto.thiemjason.tlu_connect.models.UserDTO;
import teneocto.thiemjason.tlu_connect.utils.AppConst;
import teneocto.thiemjason.tlu_connect.utils.Utils;

public class RegisterProfileViewModel extends ViewModel {
    public MutableLiveData<UserDTO> userDTO = new MutableLiveData<UserDTO>();
    public MutableLiveData<Boolean> isVerify = new MutableLiveData<Boolean>();

    public void setUser(UserDTO user) {
        userDTO.setValue(user);
        verifyUserInput();
    }

    private void verifyUserInput() {
        if (userDTO.getValue() == null) {
           UserDTO mUserDTO = new UserDTO();
            userDTO.setValue(mUserDTO);
        }

        Pattern pattern = Pattern.compile(AppConst.REGEX_Email, Pattern.CASE_INSENSITIVE);
        String userEmail = userDTO.getValue().getEmail();

        if (userEmail.equals("")) {
            Log.e(AppConst.TAG_RegisterProfileViewModel, " ==> Fail to email null");
            isVerify.setValue(false);
            return;
        }

        // Email verify
        if (!pattern.matcher(userEmail).matches()
        ) {
            Log.e(AppConst.TAG_RegisterProfileViewModel, " ==> Fail to email not match REGEX");
            isVerify.setValue(false);
            return;
        }

        isVerify.setValue(true);
    }

    public void registerProfile() {
        Log.i(AppConst.TAG_RegisterProfileViewModel, "Registered");
        Utils.registerUserDTO = userDTO.getValue();
    }

}
