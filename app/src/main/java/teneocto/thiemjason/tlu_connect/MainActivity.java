package teneocto.thiemjason.tlu_connect;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class MainActivity extends AppCompatActivity {
    public String TAG = "==> Main Activity";
    public static  String SHARED_PREFERENCE_NAME = "QRCode";
    private SharedPreferences sharedPreferences;
    private  Gson gson;
    private QRGEncoder qrgEncoder;
    private String qrKeySharedPre = "QRCODE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        this.gson = new Gson();

        Log.i(TAG, "Create");

        if ( this.checkQRExits() == true ){
            String qrgencodeString = this.sharedPreferences.getString(qrKeySharedPre, null);
            this.qrgEncoder = this.getqrEncoder(qrgencodeString);
            Bitmap bitmap = this.qrgEncoder.getBitmap();
            // Setting Bitmap to ImageView
            ImageView qrImage = findViewById(R.id.imageQRCode);
            qrImage.setImageBitmap(bitmap);

            EditText qrContent = findViewById(R.id.edtQRConntent);
            qrContent.setText(qrgencodeString);

            Log.i(TAG, "TRUE");
        }
    }

    public void generateQRcodeBtnClick (View view) {
        EditText edtQRCodeContent = findViewById(R.id.edtQRConntent);
        String qrCodeContent = edtQRCodeContent.getText().toString();

        DisplayMetrics lDisplayMetrics = getResources().getDisplayMetrics();
        int widthPixels = lDisplayMetrics.widthPixels;
        int heightPixels = lDisplayMetrics.heightPixels;

        Integer qrCodeContentWidth = (int)Math.round(widthPixels * 0.7);

        this.qrgEncoder = new QRGEncoder(qrCodeContent, null, QRGContents.Type.TEXT,  qrCodeContentWidth);
        this.storeQRcode(this.qrgEncoder);
        Log.i(TAG, "False");

        this.qrgEncoder.setColorBlack(Color.BLACK);
        this.qrgEncoder.setColorWhite(Color.WHITE);
        // Getting QR-Code as Bitmap
        Bitmap bitmap = this.qrgEncoder.getBitmap();
        // Setting Bitmap to ImageView
        ImageView qrImage = findViewById(R.id.imageQRCode);
        qrImage.setImageBitmap(bitmap);

    }

    public boolean checkQRExits() {
        String qrCodeJson =  this.sharedPreferences.getString(qrKeySharedPre, null);

        if ( qrCodeJson == null ){
            return false;
        }
        return  true;
    }

    // Store value
    public void storeQRcode( QRGEncoder qrCode ) {
        Log.i("==> Check exits", this.gson.toJson(qrCode));

        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString(qrKeySharedPre, this.gson.toJson(qrCode)).apply();

        String data =  this.sharedPreferences.getString(qrKeySharedPre, null);
        Log.i("==> Restore: ", data);

    }

    public QRGEncoder getqrEncoder( String qrEncoder ) {
        return this.gson.fromJson(qrEncoder, QRGEncoder.class);
    }
}