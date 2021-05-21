package teneocto.thiemjason.tlu_connect.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class AppConst {
    public static String notification_channel_id = "channel";
    public static String notification_topic = "messaging";
    public static String mainSharedPrefer = "DATAStore";

    public static Bitmap StringToBitMap(String base64Str) throws IllegalArgumentException
    {
        byte[] decodedBytes = Base64.decode( base64Str.substring(base64Str.indexOf(",") + 1), Base64.DEFAULT );
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static String BitMapToString(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }
}
