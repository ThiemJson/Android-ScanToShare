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
import java.net.MalformedURLException;
import java.net.URISyntaxException;
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

    /**
     * Flag for check user updated new information
     */
    public static Boolean isUserUpdatedData = false;

    /**
     * Generate QRGEncoder form content
     * @param activity Activity
     * @param content Content
     * @return QRGEncoder
     */
    public static QRGEncoder generateQRCodeFromContent(Activity activity, String content) {
        DisplayMetrics lDisplayMetrics = activity.getResources().getDisplayMetrics();
        int widthPixels = lDisplayMetrics.widthPixels;
        int heightPixels = lDisplayMetrics.heightPixels;
        Integer qrCodeContentWidth = (int) Math.round(widthPixels * 1);

        return new QRGEncoder(content, null, QRGContents.Type.TEXT, qrCodeContentWidth);
    }

    /**
     * Get Byte base64 from Bitmap
     * @param bitmap Bitmap
     * @return byte[]
     */
    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    /**
     * Get Bitmap from byte[]
     * @param byteBase64 bytp[] base64
     * @return Bitmap
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Bitmap getBitmapFromByteArray(String byteBase64) {
        byte[] imageBase64 = Base64.getDecoder().decode(byteBase64);
        return BitmapFactory.decodeByteArray(imageBase64, 0, imageBase64.length, null);
    }

    /**
     * Get Social Network form UD
     * @param id ID
     * @return Social Network DTO
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static SocialNetworkDTO getSocialNWDTOFromId(String id) {
        return Utils.socialNetworkDTOArrayList.stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList()).get(0);
    }

    /**
     * Get Social Network form name
     * @param name ID
     * @return Social Network DTO
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static SocialNetworkDTO getSocialNWDTOFromName(String name) {
        return Utils.socialNetworkDTOArrayList.stream().filter(x -> x.getName().equals(name)).collect(Collectors.toList()).get(0);
    }

    /**
     * Get UserDTO from
     * @param id ID
     * @return UserDTO
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static UserDTO getUserDTOFromId(String id) {
        return Utils.userDTOArrayList.stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList()).get(0);
    }

    /**
     * Serialize QREncoder
     * @param qrgEncoder QRGEncoder
     * @return String
     */
    public static String serializeQREncoder(QRGEncoder qrgEncoder) {
        Gson gson = new Gson();
        return gson.toJson(qrgEncoder);
    }

    /**
     * Deserialize QREncoder
     * @param qrEncoder QREncdoer
     * @return QRGEncoder
     */
    public static QRGEncoder deserialQREncoder(String qrEncoder) {
        Gson gson = new Gson();
        return gson.fromJson(qrEncoder, QRGEncoder.class);
    }

    /**
     * Get Image Uri form Bitmap
     * @param inContext context
     * @param inImage image Bitmap
     * @return Uri
     */
    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Image", null);
        return Uri.parse(path);
    }

    /**
     * Random ID
     * @return String
     */
    public static String getRandomUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * Set data to Shared Preferences
     * @param context Context
     * @param key Key
     * @param data Value
     */
    public static void setPrefer(Context context, String key, String data) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConst.SHARED_PREFER_CONTAINER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, data);
        editor.apply();
    }

    /**
     * Get data from Shared Preferences
     * @param context Context
     * @param key Key
     * @return value
     */
    public static String getPrefer(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConst.SHARED_PREFER_CONTAINER, Context.MODE_PRIVATE);
        String text = sharedPreferences.getString(key, "");
        return text;
    }

    /**
     * Get User UUID from Shared Preferences
     * @param context Context
     * @return String
     */
    public static String getUserUUID(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConst.SHARED_PREFER_CONTAINER, Context.MODE_PRIVATE);
        String text = sharedPreferences.getString(AppConst.USER_UID, "");
        return text;
    }

    /**
     * Clone SharedDTO List from another Arrays List ( Help to disable Deep Copy )
     * @param arrayList ArrayList
     * @return SharedDTO ArrayList
     */
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

    /**
     * Clone UserDTO from another
     * @param userDTO UserDTO
     * @return UserDTO
     */
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

    /**
     * Change Image Resolution
     */
    public static Bitmap prettyBitmap(Bitmap bitmap){
        Bitmap resultBitmap = bitmap;
        if(
                (bitmap.getWidth() > 1080 && bitmap.getHeight() < 1080)
                || (bitmap.getWidth() > 1080 && bitmap.getHeight() < 1080)
                || (bitmap.getWidth() < 1080 && bitmap.getHeight() < 1080)
        ){
            return resultBitmap;
        }
        Double ratio = 1080.0 / bitmap.getWidth();
        int newWidth = (int) Math.round(bitmap.getWidth() * ratio);
        int newHeight = (int) Math.round(bitmap.getHeight() * ratio);
        resultBitmap = Bitmap.createScaledBitmap(bitmap, Math.round(newWidth), Math.round(newHeight), true);
        return resultBitmap;
    }
}
