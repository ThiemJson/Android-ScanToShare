package teneocto.thiemjason.tlu_connect.ui.home;

import android.database.CursorJoiner;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Pattern;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import teneocto.thiemjason.tlu_connect.database.DBConst;
import teneocto.thiemjason.tlu_connect.models.SharedDTO;
import teneocto.thiemjason.tlu_connect.ui.scanninghistory.ScanningHistory;
import teneocto.thiemjason.tlu_connect.utils.AppConst;
import teneocto.thiemjason.tlu_connect.utils.Utils;

public class HomeQRScannerViewModel extends ViewModel {

    public MutableLiveData<String> emptyURL = new MutableLiveData<>();

    public MutableLiveData<String> resultURL = new MutableLiveData<>();

    public SharedDTO sharedDTO = new SharedDTO();

    public MutableLiveData<Boolean> showLoading = new MutableLiveData<>();

    /**
     * Is URL Invalid ?
     */
    public Boolean isURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return false;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Data handler
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void resultHandler(Result result) {
        String resultString = result.getText();

        // Check URL Invalid ?
        if (!isURL(resultString)) {
            emptyURL.setValue(resultString);
            return;
        }

        // Check is social network
        if (urlFilter(resultString).equals(AppConst.Facebook)) {
            resultURL.setValue(resultString);
            showLoading.setValue(true);
            return;
        }

        if (urlFilter(resultString).equals(AppConst.Instagram)) {
            resultURL.setValue(resultString);
            showLoading.setValue(true);
            return;
        }

        if (urlFilter(resultString).equals(AppConst.Twitter)) {
            resultURL.setValue(resultString);
            showLoading.setValue(true);
            return;
        }

        if (urlFilter(resultString).equals(AppConst.Snapchat)) {
            resultURL.setValue(resultString);
            showLoading.setValue(true);
            return;
        }

        if (urlFilter(resultString).equals(AppConst.LinkedIn)) {
            resultURL.setValue(resultString);
            showLoading.setValue(true);
            return;
        }

        // Unreachable
        if (urlFilter(resultString).equals(AppConst.Unreachable)) {
            emptyURL.setValue(resultString);
            return;
        }
    }

    /**
     * Social network filter
     */
    public String urlFilter(String url) {
        Pattern pattern = Pattern.compile(AppConst.REGEX_Facebook_URL, Pattern.CASE_INSENSITIVE);
        Boolean result = pattern.matcher(url.trim()).matches();
        if (result) {
            return AppConst.Facebook;
        }

        pattern = Pattern.compile(AppConst.REGEX_Twitter_URL, Pattern.CASE_INSENSITIVE);
        result = pattern.matcher(url.trim()).matches();
        if (result) {
            return AppConst.Twitter;
        }

        pattern = Pattern.compile(AppConst.REGEX_Instagram_URL, Pattern.CASE_INSENSITIVE);
        result = pattern.matcher(url.trim()).matches();
        if (result) {
            return AppConst.Instagram;
        }

        pattern = Pattern.compile(AppConst.REGEX_LinkedIn_URL, Pattern.CASE_INSENSITIVE);
        result = pattern.matcher(url.trim()).matches();
        if (result) {
            return AppConst.LinkedIn;
        }

        pattern = Pattern.compile(AppConst.REGEX_Snapchat_URL, Pattern.CASE_INSENSITIVE);
        result = pattern.matcher(url.trim()).matches();
        if (result) {
            return AppConst.Snapchat;
        }

        return AppConst.Unreachable;
    }
}
