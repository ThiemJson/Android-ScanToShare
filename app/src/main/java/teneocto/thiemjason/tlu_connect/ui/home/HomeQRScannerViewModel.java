package teneocto.thiemjason.tlu_connect.ui.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Base64;
import java.util.regex.Pattern;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.Result;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.firebase.FirebaseDBHelper;
import teneocto.thiemjason.tlu_connect.models.ScannedDTO;
import teneocto.thiemjason.tlu_connect.utils.AppConst;
import teneocto.thiemjason.tlu_connect.utils.Utils;

public class HomeQRScannerViewModel extends ViewModel {
    public Context context;

    public MutableLiveData<String> emptyURL = new MutableLiveData<>();

    public MutableLiveData<Boolean> isScanned = new MutableLiveData<>();

    public ScannedDTO scannedDTO = new ScannedDTO();

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
            Log.i(AppConst.TAG_QRScannedViewModel, " is Not string");
            return false;
        } catch (MalformedURLException e) {
            Log.i(AppConst.TAG_QRScannedViewModel, " is Not string");
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
            Log.i(AppConst.TAG_QRScannedViewModel, "URL wrong");
            emptyURL.setValue(resultString);
            return;
        }

        // Check is social network
        if (urlFilter(resultString).equals(AppConst.Facebook)) {
            Log.i(AppConst.TAG_QRScannedViewModel, "is Facebook");
            showLoading.setValue(true);
            crawlerFacebook(resultString);
            return;
        }

        if (urlFilter(resultString).equals(AppConst.Instagram)) {
            showLoading.setValue(true);
            Log.i(AppConst.TAG_QRScannedViewModel, "is Instagram");
            scannedDTO.setUrl(resultString);
            scannedDTO.setName("Unreachable " + AppConst.Instagram + " user");
            scannedDTO.setSocialNetWorkID(Utils.getSocialNWDTOFromName(AppConst.Instagram).getId());
            scannedDTO.setId(Utils.getRandomUUID());
            scannedDTO.setSocialNWIcon(R.drawable.instagram);
            isScanned.setValue(false);

            return;
        }

        if (urlFilter(resultString).equals(AppConst.Twitter)) {
            showLoading.setValue(true);
            Log.i(AppConst.TAG_QRScannedViewModel, "is twitter");
            scannedDTO.setUrl(resultString);
            scannedDTO.setName("Unreachable " + AppConst.Twitter + " user");
            scannedDTO.setSocialNetWorkID(Utils.getSocialNWDTOFromName(AppConst.Twitter).getId());
            scannedDTO.setId(Utils.getRandomUUID());
            scannedDTO.setSocialNWIcon(R.drawable.twiiter);
            isScanned.setValue(false);

            return;
        }

        if (urlFilter(resultString).equals(AppConst.Snapchat)) {
            showLoading.setValue(true);
            Log.i(AppConst.TAG_QRScannedViewModel, "is Snapchat");
            scannedDTO.setUrl(resultString);
            scannedDTO.setName("Unreachable " + AppConst.Snapchat + " user");
            scannedDTO.setSocialNetWorkID(Utils.getSocialNWDTOFromName(AppConst.Snapchat).getId());
            scannedDTO.setId(Utils.getRandomUUID());
            scannedDTO.setSocialNWIcon(R.drawable.sapchat);
            isScanned.setValue(false);

            return;
        }

        if (urlFilter(resultString).equals(AppConst.LinkedIn)) {
            showLoading.setValue(true);

            scannedDTO.setUrl(resultString);
            scannedDTO.setName("Unreachable " + AppConst.LinkedIn + " user");
            scannedDTO.setSocialNetWorkID(Utils.getSocialNWDTOFromName(AppConst.LinkedIn).getId());
            scannedDTO.setId(Utils.getRandomUUID());
            scannedDTO.setSocialNWIcon(R.drawable.linkedin);
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
                    scannedDTO.setUrl(url);
                    scannedDTO.setName("Unreachable " + AppConst.Facebook + " user");
                    scannedDTO.setSocialNetWorkID(Utils.getSocialNWDTOFromName(AppConst.Facebook).getId());
                    scannedDTO.setId(Utils.getRandomUUID());
                    scannedDTO.setSocialNWIcon(R.drawable.facebook);
                    isScanned.postValue(false);

                    Log.i(AppConst.TAG_QRScannedViewModel, " parse HTML false");
                    Log.i(AppConst.TAG_QRScannedViewModel, getMobileURLFacebook(url));
                    return;
                }

                new Thread(() -> {
                    try {
                        URL newURL = new URL(userImage);
                        Bitmap profilePic = BitmapFactory.decodeStream(newURL.openConnection().getInputStream());

                        scannedDTO.setId(Utils.getRandomUUID());
                        scannedDTO.setImageBase64(Base64.getEncoder().encodeToString(Utils.getBitmapAsByteArray(Utils.prettyBitmap(profilePic))));
                        scannedDTO.setName(userName);
                        scannedDTO.setSocialNetWorkID(Utils.getSocialNWDTOFromName(AppConst.Facebook).getId());
                        scannedDTO.setSocialNWIcon(R.drawable.facebook);
                        scannedDTO.setUrl(url);
                        isScanned.postValue(true);

                        // Store
                        scannedDTO.setUserId(Utils.getUserUUID(context));
                        storeScanned(scannedDTO);

                    } catch (IOException e) {
                        e.printStackTrace();

                        scannedDTO.setUrl(url);
                        scannedDTO.setName("Unreachable " + AppConst.Facebook + " user");
                        scannedDTO.setSocialNetWorkID(Utils.getSocialNWDTOFromName(AppConst.Facebook).getId());
                        scannedDTO.setId(Utils.getRandomUUID());
                        scannedDTO.setSocialNWIcon(R.drawable.facebook);
                        isScanned.postValue(false);

                        Log.i(AppConst.TAG_QRScannedViewModel, getMobileURLFacebook(url));
                        Log.i(AppConst.TAG_QRScannedViewModel, " query IMAGE False");
                    }
                }).start();
            }
        }, error -> {
            // On Error
            scannedDTO.setUrl(url);
            scannedDTO.setName("Unreachable " + AppConst.Facebook + " user");
            scannedDTO.setSocialNetWorkID(Utils.getSocialNWDTOFromName(AppConst.Facebook).getId());
            scannedDTO.setId(Utils.getRandomUUID());
            isScanned.postValue(false);
            Log.i(AppConst.TAG_QRScannedViewModel, "get HTML False");
            Log.i(AppConst.TAG_QRScannedViewModel, getMobileURLFacebook(url));
        });
        requestQueue.add(stringRequest);
    }

    private String getMobileURLFacebook(String url) {
        String stringResult = url;
        if ((stringResult.charAt(stringResult.length() - 1) + "").equals("/")) {
            stringResult = stringResult.substring(0, stringResult.length() - 1);
        }

        if (stringResult.contains("m.facebook")) {
            return stringResult;
        } else if (stringResult.contains("wwww.")) {
            stringResult = stringResult.replace("wwww.", "m.");
        } else if ((!stringResult.contains("wwww.")) && stringResult.contains("facebook")) {
            stringResult = stringResult.replace("www.facebook", "m.facebook");
        } else if ((!stringResult.contains("wwww.")) && stringResult.contains("fb")) {
            stringResult = stringResult.replace("fb", "m.facebook");
        } else if (stringResult.contains("/facebook.com")) {
            stringResult = stringResult.replace("/facebook.com", "/m.facebook.com");
        } else if (stringResult.contains("/fb.com")) {
            stringResult = stringResult.replace("/fb.com", "/m.facebook.com");
        }

        Log.i(AppConst.TAG_QRScannedViewModel, "string result: " + stringResult);
        return stringResult;
    }

    /**
     * Store scanned history into firebase database
     *
     * @param scannedDTO Scanned Histry
     */
    private void storeScanned(ScannedDTO scannedDTO) {
        FirebaseDBHelper firebaseDBHelper = new FirebaseDBHelper();
        firebaseDBHelper.Scanned_History_Insert(scannedDTO);
    }
}
