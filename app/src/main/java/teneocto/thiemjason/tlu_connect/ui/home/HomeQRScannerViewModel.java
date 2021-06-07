package teneocto.thiemjason.tlu_connect.ui.home;

import android.content.Context;
import android.content.Intent;
import android.database.CursorJoiner;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.regex.Pattern;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.database.DBConst;
import teneocto.thiemjason.tlu_connect.firebase.FirebaseDBHelper;
import teneocto.thiemjason.tlu_connect.models.ScannedResultDTO;
import teneocto.thiemjason.tlu_connect.models.SharedDTO;
import teneocto.thiemjason.tlu_connect.ui.drawer.Drawer;
import teneocto.thiemjason.tlu_connect.ui.scanninghistory.ScanningHistory;
import teneocto.thiemjason.tlu_connect.utils.AppConst;
import teneocto.thiemjason.tlu_connect.utils.Utils;

public class HomeQRScannerViewModel extends ViewModel {
    public Context context;

    public MutableLiveData<String> emptyURL = new MutableLiveData<>();

    public MutableLiveData<Boolean> isScanned = new MutableLiveData<>();

    public ScannedResultDTO scannedResultDTO = new ScannedResultDTO();

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
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void resultHandler(Result result) {
        String resultString = result.getText();

        // Check URL Invalid ?
        if (!isURL(resultString)) {
            emptyURL.setValue(resultString);
            return;
        }

        // Check is social network
        if (urlFilter(resultString).equals(AppConst.Facebook)) {
            showLoading.setValue(true);
            crawlerFacebook(resultString);
            return;
        }

        if (urlFilter(resultString).equals(AppConst.Instagram)) {
            showLoading.setValue(true);

            scannedResultDTO.setUrl(resultString);
            scannedResultDTO.setName("Unreachable " + AppConst.Instagram + " user");
            scannedResultDTO.setSocialNetWorkID(Utils.getSocialNWDTOFromName(AppConst.Instagram).getId());
            scannedResultDTO.setId(Utils.getRandomUUID());
            scannedResultDTO.setSocialNWIcon(R.drawable.instagram);
            isScanned.setValue(false);

            return;
        }

        if (urlFilter(resultString).equals(AppConst.Twitter)) {
            showLoading.setValue(true);

            scannedResultDTO.setUrl(resultString);
            scannedResultDTO.setName("Unreachable " + AppConst.Twitter + " user");
            scannedResultDTO.setSocialNetWorkID(Utils.getSocialNWDTOFromName(AppConst.Twitter).getId());
            scannedResultDTO.setId(Utils.getRandomUUID());
            scannedResultDTO.setSocialNWIcon(R.drawable.twiiter);
            isScanned.setValue(false);

            return;
        }

        if (urlFilter(resultString).equals(AppConst.Snapchat)) {
            showLoading.setValue(true);

            scannedResultDTO.setUrl(resultString);
            scannedResultDTO.setName("Unreachable " + AppConst.Snapchat + " user");
            scannedResultDTO.setSocialNetWorkID(Utils.getSocialNWDTOFromName(AppConst.Snapchat).getId());
            scannedResultDTO.setId(Utils.getRandomUUID());
            scannedResultDTO.setSocialNWIcon(R.drawable.sapchat);
            isScanned.setValue(false);

            return;
        }

        if (urlFilter(resultString).equals(AppConst.LinkedIn)) {
            showLoading.setValue(true);

            scannedResultDTO.setUrl(resultString);
            scannedResultDTO.setName("Unreachable " + AppConst.LinkedIn + " user");
            scannedResultDTO.setSocialNetWorkID(Utils.getSocialNWDTOFromName(AppConst.LinkedIn).getId());
            scannedResultDTO.setId(Utils.getRandomUUID());
            scannedResultDTO.setSocialNWIcon(R.drawable.linkedin);
            isScanned.setValue(false);

            return;
        }

        // Not any link
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

    /**
     * Facebook crawler
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void crawlerFacebook(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getMobileURLFacebook(url), response -> {
            Document document = Jsoup.parse(response);
            if (document != null) {
                String userName;
                String userImage;

                // Crawl User profile
                Elements elementName = document.select("#cover-name-root");
                Elements elementProfile = document.select("img.profpic");

                try {
                    userName = elementName.get(0).getElementsByTag("h3").first().text();
                    userImage = elementProfile.first().attr("src");
                } catch (Exception ex) {
                    scannedResultDTO.setUrl(url);
                    scannedResultDTO.setName("Unreachable " + AppConst.Facebook + " user");
                    scannedResultDTO.setSocialNetWorkID(Utils.getSocialNWDTOFromName(AppConst.Facebook).getId());
                    scannedResultDTO.setId(Utils.getRandomUUID());
                    isScanned.postValue(false);
                    return;
                }

                new Thread(() -> {
                    try {
                        URL newURL = new URL(userImage);
                        Bitmap profilePic = BitmapFactory.decodeStream(newURL.openConnection().getInputStream());

                        scannedResultDTO.setId(Utils.getRandomUUID());
                        scannedResultDTO.setImageBase64(Base64.getEncoder().encodeToString(Utils.getBitmapAsByteArray(Utils.prettyBitmap(profilePic))));
                        scannedResultDTO.setName(userName);
                        scannedResultDTO.setSocialNetWorkID(Utils.getSocialNWDTOFromName(AppConst.Facebook).getId());
                        scannedResultDTO.setUrl(url);
                        isScanned.postValue(true);

                    } catch (IOException e) {
                        e.printStackTrace();

                        scannedResultDTO.setUrl(url);
                        scannedResultDTO.setName("Unreachable " + AppConst.Facebook + " user");
                        scannedResultDTO.setSocialNetWorkID(Utils.getSocialNWDTOFromName(AppConst.Facebook).getId());
                        scannedResultDTO.setId(Utils.getRandomUUID());
                        isScanned.postValue(false);
                    }
                }).start();
            }
        }, error -> {
            // On Error
            scannedResultDTO.setUrl(url);
            scannedResultDTO.setName("Unreachable " + AppConst.Facebook + " user");
            scannedResultDTO.setSocialNetWorkID(Utils.getSocialNWDTOFromName(AppConst.Facebook).getId());
            scannedResultDTO.setId(Utils.getRandomUUID());
            isScanned.postValue(false);
        });
        requestQueue.add(stringRequest);
    }

    private String getMobileURLFacebook(String url) {
        String stringResult = url;
        if (url.contains("m.facebook")) {
            return url;
        } else if (url.contains("wwww.")) {
            stringResult = url.replace("wwww.", "m.");
        } else if ((!url.contains("wwww.")) && url.contains("facebook")) {
            stringResult = url.replace("facebook", "m.facebook");
        } else if ((!url.contains("wwww.")) && url.contains("fb")) {
            stringResult = url.replace("fb", "m.facebook");
        } else if (url.contains("/facebook.com")) {
            stringResult = url.replace("/facebook.com", "/m.facebook.com");
        } else if (url.contains("/fb.com")) {
            stringResult = url.replace("/fb.com", "/m.facebook.com");
        }
        return stringResult;
    }
}
