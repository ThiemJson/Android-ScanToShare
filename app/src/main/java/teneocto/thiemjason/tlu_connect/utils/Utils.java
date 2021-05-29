package teneocto.thiemjason.tlu_connect.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.DisplayMetrics;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class Utils {
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

    public static QRGEncoder generateQRCodeFromContent(Context context, String content) {
        DisplayMetrics lDisplayMetrics = context.getResources().getDisplayMetrics();
        int widthPixels = lDisplayMetrics.widthPixels;
        int heightPixels = lDisplayMetrics.heightPixels;
        Integer qrCodeContentWidth = (int) Math.round(widthPixels * 1);
        return new QRGEncoder(content, null, QRGContents.Type.TEXT, qrCodeContentWidth);
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
