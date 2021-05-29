package teneocto.thiemjason.tlu_connect.ui.notification;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.database.DBConst;
import teneocto.thiemjason.tlu_connect.models.NotificationDTO;
import teneocto.thiemjason.tlu_connect.models.SharedDTO;
import teneocto.thiemjason.tlu_connect.ui.adapter.NotificationAdapter;

public class Notification extends AppCompatActivity {
    Button mBackButton;
    AdView adView;
    View mEmptyImage;

    // RecycleView
    NotificationAdapter mAdapter;
    ArrayList<NotificationDTO> arrayList;
    RecyclerView mRecycleView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        arrayList = new ArrayList<>();
        mBackButton = findViewById(R.id.notification_menu_icon);
        mRecycleView = findViewById(R.id.notification_recycle_view);
        mEmptyImage = findViewById(R.id.notification_empty);
        mBackButton.setOnClickListener(v -> finish());
        this.initRecycleView();
        this.loadAd();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initRecycleView() {
        loadDataFromFirebase();
        mAdapter = new NotificationAdapter(arrayList, this);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setOnItemClickListener((view, position) -> viewNotification());

        if (arrayList.size() != 0) {
            mEmptyImage.setVisibility(View.GONE);
        }
    }

    private void viewNotification() {
        // TODO
    }

    /**
     * Init ads
     */
    void loadAd() {
        MobileAds.initialize(this, initializationStatus -> {
        });

        adView = findViewById(R.id.notification_ads_view);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    /**
     * ===========================> DATA
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void loadDataFromFirebase() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(DBConst.NOTIFICATION_TABLE_NAME);
        databaseReference.child("1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        arrayList.add(data.getValue(NotificationDTO.class));
                    }
                    mAdapter.notifyDataSetChanged();
                    if (arrayList.size() != 0) {
                        mEmptyImage.setVisibility(View.GONE);
                    } else {
                        mEmptyImage.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadDataFromSQLite();
            }
        });
    }

    private void loadDataFromSQLite() {
    }
}