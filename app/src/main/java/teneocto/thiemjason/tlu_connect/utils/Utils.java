package teneocto.thiemjason.tlu_connect.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.DisplayMetrics;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import teneocto.thiemjason.tlu_connect.models.SharedDTO;
import teneocto.thiemjason.tlu_connect.models.SocialNetworkDTO;
import teneocto.thiemjason.tlu_connect.models.UserDTO;

public class Utils {
    public static List<SocialNetworkDTO> socialNetworkDTOArrayList;
    public static List<UserDTO> userDTOArrayList;
    public static ArrayList<SharedDTO> sharedDTOArrayList;
    public static UserDTO registerUserDTO;

    public static QRGEncoder generateQRCodeFromContent(Activity activity, String content) {
        DisplayMetrics lDisplayMetrics = activity.getResources().getDisplayMetrics();
        int widthPixels = lDisplayMetrics.widthPixels;
        int heightPixels = lDisplayMetrics.heightPixels;
        Integer qrCodeContentWidth = (int) Math.round(widthPixels * 1);

        return new QRGEncoder(content, null, QRGContents.Type.TEXT, qrCodeContentWidth);
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Bitmap getBitmapFromByteArray(String byteBase64) {
        byte[] imageBase64 = Base64.getDecoder().decode(byteBase64);
        return BitmapFactory.decodeByteArray(imageBase64, 0, imageBase64.length, null);
    }

    public static QRGEncoder generateQRCodeFromContent(Context context, String content) {
        DisplayMetrics lDisplayMetrics = context.getResources().getDisplayMetrics();
        int widthPixels = lDisplayMetrics.widthPixels;
        int heightPixels = lDisplayMetrics.heightPixels;
        Integer qrCodeContentWidth = (int) Math.round(widthPixels * 1);
        return new QRGEncoder(content, null, QRGContents.Type.TEXT, qrCodeContentWidth);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static SocialNetworkDTO getSocialNWDTOFromId(String id) {
        return Utils.socialNetworkDTOArrayList.stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList()).get(0);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static SocialNetworkDTO getSocialNWDTOFromName(String name) {
        return Utils.socialNetworkDTOArrayList.stream().filter(x -> x.getName().equals(name)).collect(Collectors.toList()).get(0);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static UserDTO getUserDTOFromId(String id) {
        return Utils.userDTOArrayList.stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList()).get(0);
    }

    public static String serializeQREncoder(QRGEncoder qrgEncoder) {
        Gson gson = new Gson();
        return gson.toJson(qrgEncoder);
    }

    public static QRGEncoder deserialQREncoder(String qrEncoder) {
        Gson gson = new Gson();
        return gson.fromJson(qrEncoder, QRGEncoder.class);
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Image", null);
        return Uri.parse(path);
    }

    public static String getRandomUUID() {
        return UUID.randomUUID().toString();
    }

    public static void setPrefer(Context context, String key, String data) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConst.SHARED_PREFER_CONTAINER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, data);
        editor.apply();
    }

    public static String getPrefer(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConst.SHARED_PREFER_CONTAINER, Context.MODE_PRIVATE);
        String text = sharedPreferences.getString(key, "");
        return text;
    }

    public static String getUserUUID(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConst.SHARED_PREFER_CONTAINER, Context.MODE_PRIVATE);
        String text = sharedPreferences.getString(AppConst.USER_UID, "");
        return text;
    }

    public static Bitmap getFacebookProfilePicture(String grapUrl) throws IOException {
        URL imageURL = new URL(grapUrl + "?type=large");
        Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());

        return bitmap;
    }

    //    String userID, String socialNetWorkID, String url
    public static ArrayList<SharedDTO> cloneSharedDTO(ArrayList<SharedDTO> arrayList) {
        ArrayList<SharedDTO> result = new ArrayList<>();
        for (SharedDTO sharedDTO : arrayList) {
            result.add(new SharedDTO(
                    sharedDTO.getId(),
                    sharedDTO.getUserID(),
                    sharedDTO.getSocialNetWorkID(),
                    sharedDTO.getUrl()
            ));
        }
        return result;
    }

    public static UserDTO cloneUserDTO(UserDTO userDTO) {
        UserDTO result = new UserDTO(
                userDTO.getId(),
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getEmail(),
                userDTO.getPosition(),
                userDTO.getCompany(),
                userDTO.getImageBase64()
        );

        result.setFirebaseId(userDTO.getFirebaseId());
        return result;
    }
}
