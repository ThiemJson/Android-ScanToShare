package teneocto.thiemjason.tlu_connect.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.DisplayMetrics;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import teneocto.thiemjason.tlu_connect.models.SocialNetworkDTO;
import teneocto.thiemjason.tlu_connect.models.UserDTO;

public class Utils {
    public static List<SocialNetworkDTO> socialNetworkDTOArrayList;
    public static List<UserDTO> userDTOArrayList;

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
    public static SocialNetworkDTO getSocialNWDTOFromId(int id) {
        return Utils.socialNetworkDTOArrayList.stream().filter(x -> x.getId() == id).collect(Collectors.toList()).get(0);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static UserDTO getUserDTOFromId(int id){
        return Utils.userDTOArrayList.stream().filter(x -> x.getId() == id).collect(Collectors.toList()).get(0);
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
}
