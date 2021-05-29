package teneocto.thiemjason.tlu_connect.ui.loading;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;

import teneocto.thiemjason.tlu_connect.ui.main.MainActivity;
import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.database.DBHelper;
import teneocto.thiemjason.tlu_connect.receiver.NetworkReceiver;
import teneocto.thiemjason.tlu_connect.utils.AppConst;

public class Launcher extends AppCompatActivity {
    private static String TAG = "Launcher";
    private BroadcastReceiver mNetworkReceiver;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        // App initial
        this.initFirebaseMessaging();
        this.setUpPermission();
        this.setUpReceiver();
//        this.setUpSQLiteDB();

        // App started
        this.appStart();
    }

    /**
     * Request Firebase Messaging
     */
    void initFirebaseMessaging() {
        FirebaseMessaging.getInstance().subscribeToTopic(AppConst.NOTIFICATION_TOPIC)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.e(TAG, "==> Notification failure");
                    }
                    Log.i(TAG, "==> Notification successful");
                });
    }


    /**
     * Request Permission
     */
    private void setUpPermission() {
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest();
        }
    }

    private void makeRequest() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1888);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * Request Broadcast Receiver
     */
    private void setUpReceiver() {
        mNetworkReceiver = new NetworkReceiver();
        registerNetworkBroadcastForNougat();
    }

    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    /**
     * Request SQLite Database
     */
    private void setUpSQLiteDB() {
        dbHelper = new DBHelper(this);
    }


    private void appStart() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}