package teneocto.thiemjason.tlu_connect.ui.launcher;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.database.DBConst;
import teneocto.thiemjason.tlu_connect.models.SharedDTO;
import teneocto.thiemjason.tlu_connect.models.SocialNetworkDTO;
import teneocto.thiemjason.tlu_connect.models.UserDTO;
import teneocto.thiemjason.tlu_connect.ui.main.MainActivity;
import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.database.DBHelper;
import teneocto.thiemjason.tlu_connect.receiver.NetworkReceiver;
import teneocto.thiemjason.tlu_connect.utils.AppConst;
import teneocto.thiemjason.tlu_connect.utils.Utils;

public class Launcher extends AppCompatActivity {
    private static String TAG = "Launcher";
    private BroadcastReceiver mNetworkReceiver;
    private DBHelper dbHelper;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        // App initial
        this.initFirebaseMessaging();
        this.setUpPermission();
        this.setUpReceiver();
        this.setUpFirebaseDatabase();

        Thread background = new Thread() {
            public void run() {
                try {
                    sleep(2 * 1000);
                    appStart();
                } catch (Exception e) {
                }
            }
        };
        // start thread
        background.start();
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

    private void setUpFirebaseDatabase() {
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference(DBConst.SN_TABLE_NAME);
        Utils.socialNetworkDTOArrayList = new ArrayList<>();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Utils.socialNetworkDTOArrayList.add(data.getValue(SocialNetworkDTO.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                setUpSQLiteDB();
            }
        });

        databaseReference = firebaseDatabase.getReference(DBConst.USER_TABLE_NAME);
        Utils.userDTOArrayList = new ArrayList<>();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Utils.userDTOArrayList.add(data.getValue(UserDTO.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                setUpSQLiteDB();
            }
        });
    }

    private void appStart() throws InterruptedException {
        Thread.sleep(3000);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}