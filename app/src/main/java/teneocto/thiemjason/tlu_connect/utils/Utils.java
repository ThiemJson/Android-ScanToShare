package teneocto.thiemjason.tlu_connect.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

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
}
