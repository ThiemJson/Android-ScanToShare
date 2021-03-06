package teneocto.thiemjason.tlu_connect.ui.launcher;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.database.DBConst;
import teneocto.thiemjason.tlu_connect.database.DBHelper;
import teneocto.thiemjason.tlu_connect.firebase.FirebaseDBExample;
import teneocto.thiemjason.tlu_connect.models.SocialNetworkDTO;
import teneocto.thiemjason.tlu_connect.models.UserDTO;
import teneocto.thiemjason.tlu_connect.receiver.NetworkReceiver;
import teneocto.thiemjason.tlu_connect.service.SyncLocalDBService;
import teneocto.thiemjason.tlu_connect.ui.drawer.Drawer;
import teneocto.thiemjason.tlu_connect.ui.main.MainActivity;
import teneocto.thiemjason.tlu_connect.utils.AppConst;
import teneocto.thiemjason.tlu_connect.ui.progressdialog.CustomProgressDialog;
import teneocto.thiemjason.tlu_connect.utils.Utils;

/**
 * Loading Activity
 */
public class Launcher extends AppCompatActivity {

    private static String TAG = "Launcher";

    private BroadcastReceiver mNetworkReceiver;
    private DBHelper dbHelper;

    private FirebaseDatabase firebaseDatabase;

    private DatabaseReference databaseReference;

    private FirebaseDBExample firebaseDBExample;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        firebaseDatabase = FirebaseDatabase.getInstance();

        // For debugs ==============> "a0d945aa-8114-4ba0-af66-88637bdbb3e6"
//        Utils.clearSharedPrefer(this);
//        Utils.setPrefer(this, AppConst.USER_UID, "2e6c0fb4-6cc1-4795-a448-2cdc987f2db6");
        // For debugs ==============>


        // App initial
        this.appInitial();

        String userUUID = Utils.getUserUUID(this);
        Log.i(AppConst.TAG_Launcher, " ==>> UUID + " + userUUID);

        if (userUUID == null || userUUID.equals("")) {
            Log.i(AppConst.TAG_Launcher, " ==>> LOGGED IN");
            this.setUpSocialNWFromFirebaseDatabase(false);
            return;
        } else {
            Log.i(AppConst.TAG_Launcher, " ==>> LOGGED OUT");
        }

        this.setUpSocialNWFromFirebaseDatabase(true);
        AppConst.USER_UID_Static = Utils.getUserUUID(this);
        this.setUpUserDTOFromFirebaseDatabase();
//        this.startSyncLocalDBService();

        // SEED DATA
        //  firebaseDBExample = new FirebaseDBExample(this);
        //  firebaseDBExample.FirebaseDataSeeder();
    }

    private void appInitial() {
        new Thread(() -> {
            initFirebaseMessaging();
            setUpPermission();
            setUpReceiver();
        }).start();
    }

    // Start Service
    private void startSyncLocalDBService() {
        new Thread(() -> {
            Intent intentService = new Intent(this, SyncLocalDBService.class);
            startService(intentService);
        }).start();

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

    private void setUpSocialNWFromFirebaseDatabase(Boolean isLoggedIn) {
        Log.i(AppConst.TAG_Launcher, " setUpSocialNWFromFirebaseDatabase ");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(DBConst.SN_TABLE_NAME);
        Utils.socialNetworkDTOArrayList = new ArrayList<>();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Utils.socialNetworkDTOArrayList.add(data.getValue(SocialNetworkDTO.class));
                    }

                    if (!isLoggedIn) {
                        appStart();
                    }
                    Log.i(AppConst.TAG_Launcher, Utils.socialNetworkDTOArrayList.size() + "");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                setUpSQLiteDB();
                Log.i(AppConst.TAG_Launcher, " Error ");
            }
        });
    }

    /**
     * Setting up user from Firebase
     */
    private void setUpUserDTOFromFirebaseDatabase() {
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

                // Go to Home activity
                Intent intent = new Intent(getApplicationContext(), Drawer.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                setUpSQLiteDB();
            }
        });
    }

    /**
     * App started
     */
    private void appStart() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}