package teneocto.thiemjason.tlu_connect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import info.androidhive.barcode.BarcodeReader;
import info.androidhive.barcode.camera.CameraSource;

public class MainActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {
    BarcodeReader barcodeReader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);

    }
    @Override
    public void onScanned(Barcode barcode) {
        Log.i("BarCodescanner", barcode.displayValue);

        // playing barcode reader beep sound
        barcodeReader.playBeep();

        Toast.makeText(MainActivity.this, barcode.displayValue + "", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

    }
}