package teneocto.thiemjason.tlu_connect.service;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.database.DBConst;
import teneocto.thiemjason.tlu_connect.database.DBHelper;
import teneocto.thiemjason.tlu_connect.models.NotificationDTO;
import teneocto.thiemjason.tlu_connect.models.ScannedDTO;
import teneocto.thiemjason.tlu_connect.models.SharedDTO;
import teneocto.thiemjason.tlu_connect.models.SocialNetworkDTO;
import teneocto.thiemjason.tlu_connect.models.UserDTO;
import teneocto.thiemjason.tlu_connect.utils.AppConst;
import teneocto.thiemjason.tlu_connect.utils.Utils;

/**
 * LOCAL Service
 */
public class SyncLocalDBService extends Service {
    /**
     * Flag check
     */
    public MutableLiveData<Integer> isFetched = new MutableLiveData<Integer>();

    /**
     * Firebase Database
     */
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    /**
     * SQLite Database Helper
     */
    private DBHelper dbHelper;

    /**
     * DATA SET
     */
    private ArrayList<UserDTO> userDTOList = new ArrayList<>();

    private ArrayList<SharedDTO> sharedDTOList = new ArrayList<>();

    private ArrayList<SocialNetworkDTO> socialNetworkDTOList = new ArrayList<>();

    private ArrayList<ScannedDTO> scannedDTOArrayList = new ArrayList<>();

    private ArrayList<NotificationDTO> notificationDTOList = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(AppConst.TAG_SyncLocalDBService, " sync data");
        return super.onStartCommand(intent, flags, startId);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        dbHelper = new DBHelper(getApplicationContext());
        isFetched.setValue(0);
        loadDataFromFirebase();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        firebaseDatabase = null;
        dbHelper = null;
        userDTOList = null;
        sharedDTOList = null;
        socialNetworkDTOList = null;
        scannedDTOArrayList = null;
        notificationDTOList = null;
        isFetched = null;
        Log.i(AppConst.TAG_SyncLocalDBService, " service destroyed");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Load data from Firebase
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void loadDataFromFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        dbHelper = new DBHelper(getApplicationContext());
        syncNotification();
        syncScanningHistory();
        syncShared();
        syncUser();
        syncSocialNW();
    }

    /**
     * Sync Notification
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void syncNotification() {
        DatabaseReference databaseReference = firebaseDatabase.getReference(DBConst.NOTIFICATION_TABLE_NAME);
        databaseReference.child(AppConst.USER_UID_Static).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        notificationDTOList.add(data.getValue(NotificationDTO.class));
                        Log.i(AppConst.SyncLocalDatabaseService, "NOTIFICATION FETCH: " + data.getValue(NotificationDTO.class).getTitle());
                    }

                }

                // Store data
                dbHelper.deleteRecord(DBConst.NOTIFICATION_TABLE_NAME);
                if (notificationDTOList.size() != 0) {
                    // Sync data
                    dbHelper.deleteRecord(DBConst.NOTIFICATION_TABLE_NAME);
                    for (NotificationDTO notificationDTO : notificationDTOList) {
                        dbHelper.NOTIFICATION_Insert(notificationDTO);
                        Log.i(AppConst.SyncLocalDatabaseService, "Notification DATA INSERT: " + notificationDTO.getTitle());
                    }
                } else {

                }

                isFetched.setValue(isFetched.getValue() + 1);
                Log.i(AppConst.TAG_SyncLocalDBService, " service notification count: " + isFetched.getValue());
                if (isFetched.getValue() == 5) {
                    stopSelf();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(AppConst.TAG_SyncLocalDBService, " service notification: Failure");
            }
        });
    }

    /**
     * Sync Scanning History
     */
    private void syncScanningHistory() {
        DatabaseReference databaseReference = firebaseDatabase.getReference(DBConst.SCAN_TABLE_NAME);
        databaseReference.child(AppConst.USER_UID_Static).addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        ScannedDTO scannedDTO = data.getValue(ScannedDTO.class);
                        scannedDTOArrayList.add(scannedDTO);
                        Log.i(AppConst.SyncLocalDatabaseService, "SCANNING HIT FETCH: " + data.getValue(ScannedDTO.class).getName());
                    }
                }

                // Sync data
                dbHelper.deleteRecord(DBConst.SCAN_TABLE_NAME);
                if (scannedDTOArrayList.size() == 0) {

                } else {
                    for (ScannedDTO scannedDTO : scannedDTOArrayList) {
                        dbHelper.SCANNING_HISTORY_Insert(scannedDTO);
                        Log.i(AppConst.SyncLocalDatabaseService, "ScanningHis DATA INSERT: " + scannedDTO.getUserId());
                    }
                }

                isFetched.setValue(isFetched.getValue() + 1);
                Log.i(AppConst.TAG_SyncLocalDBService, " service scanning count: " + isFetched.getValue());
                if (isFetched.getValue() == 5) {
                    stopSelf();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(AppConst.TAG_SyncLocalDBService, " service scanning: Failure");
            }
        });
    }

    /**
     * Sync User Shared
     */
    private void syncShared() {
        DatabaseReference databaseReference = firebaseDatabase.getReference(DBConst.SHARED_TABLE_NAME);
        databaseReference.child(AppConst.USER_UID_Static).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        sharedDTOList.add(data.getValue(SharedDTO.class));
                        Log.i(AppConst.SyncLocalDatabaseService, "Shared FETCH: " + data.getValue(SharedDTO.class).getSocialNetWorkID());
                    }

                    // Sync data
                    dbHelper.deleteRecord(DBConst.SHARED_TABLE_NAME);
                    if (sharedDTOList.size() != 0) {
                        for (SharedDTO sharedDTO : sharedDTOList) {
                            dbHelper.SHARED_Insert(sharedDTO);
                            Log.i(AppConst.SyncLocalDatabaseService, "Shared DATA INSERT: " + sharedDTO.getSocialNetWorkID());
                        }
                    }

                    isFetched.setValue(isFetched.getValue() + 1);
                    Log.i(AppConst.TAG_SyncLocalDBService, " service shared count: " + isFetched.getValue());
                    if (isFetched.getValue() == 5) {
                        stopSelf();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(AppConst.TAG_SyncLocalDBService, " service shared Failure: ");
            }
        });
    }

    /**
     * Sync User Notification
     */
    private void syncUser() {
        DatabaseReference databaseReference = firebaseDatabase.getReference(DBConst.USER_TABLE_NAME);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        userDTOList.add(data.getValue(UserDTO.class));
                        Log.i(AppConst.SyncLocalDatabaseService, "USer FETCH: " + data.getValue(UserDTO.class).getLastName());
                    }
                    // Sync data
                    dbHelper.deleteRecord(DBConst.USER_TABLE_NAME);

                    if (userDTOList.size() != 0) {
                        for (UserDTO userDTO : userDTOList) {
                            dbHelper.USER_Insert(userDTO);
                            Log.i(AppConst.SyncLocalDatabaseService, "User DATA INSERT: " + userDTO.getLastName());
                        }
                    }

                    isFetched.setValue(isFetched.getValue() + 1);
                    Log.i(AppConst.TAG_SyncLocalDBService, " service user count: " + isFetched.getValue());
                    if (isFetched.getValue() == 5) {
                        stopSelf();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(AppConst.TAG_SyncLocalDBService, " service user Failure: ");
            }
        });
    }

    /**
     * Sync User Social Network
     */
    private void syncSocialNW() {
        DatabaseReference databaseReference = firebaseDatabase.getReference(DBConst.SN_TABLE_NAME);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        socialNetworkDTOList.add(data.getValue(SocialNetworkDTO.class));
                        Log.i(AppConst.SyncLocalDatabaseService, "Social NW FETCH: " + data.getValue(SocialNetworkDTO.class).getName());
                    }
                    // Sync data
                    dbHelper.deleteRecord(DBConst.SN_TABLE_NAME);
                    if (socialNetworkDTOList.size() != 0) {
                        for (SocialNetworkDTO socialNetworkDTO : socialNetworkDTOList) {
                            dbHelper.SOCIAL_NETWORK_Insert(socialNetworkDTO);
                            Log.i(AppConst.SyncLocalDatabaseService, "Social NW DATA INSERT: " + socialNetworkDTO.getName());
                        }
                    }

                    isFetched.setValue(isFetched.getValue() + 1);
                    Log.i(AppConst.TAG_SyncLocalDBService, " service social count: " + isFetched.getValue());
                    if (isFetched.getValue() == 5) {
                        stopSelf();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(AppConst.TAG_SyncLocalDBService, " service social nw Failure: ");
            }
        });
    }
}
