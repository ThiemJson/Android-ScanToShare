package teneocto.thiemjason.tlu_connect.ui.scanninghistory;

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
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.database.DBConst;
import teneocto.thiemjason.tlu_connect.models.NotificationDTO;
import teneocto.thiemjason.tlu_connect.models.ScanningHistoryDTO;
import teneocto.thiemjason.tlu_connect.models.UserDTO;
import teneocto.thiemjason.tlu_connect.ui.adapter.ScanHisAdapter;
import teneocto.thiemjason.tlu_connect.utils.Utils;

public class ScanningHistory extends AppCompatActivity {
    Button mBackButton;
    RecyclerView mRecycleView;
    ScanHisAdapter mAdapter;
    AdView adView;
    View mEmptyImage;

    ArrayList<UserDTO> mUserArrays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning_history);

        mUserArrays = new ArrayList<>();
        this.initView();
        this.initRecycleView();
        this.initialAds();

        if (mUserArrays.size() != 0) {
            mEmptyImage.setVisibility(View.GONE);
        }
    }

    void initView() {
        mBackButton = findViewById(R.id.scanning_menu_icon);
        mBackButton.setOnClickListener(v -> finish());
        mEmptyImage = findViewById(R.id.scanning_history_empty);
    }

    private void initialAds() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
            }
        });

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initRecycleView() {
        this.loadDataFromFirebase();
        mRecycleView = findViewById(R.id.scanning_his_recycle_view);
        mAdapter = new ScanHisAdapter(mUserArrays, this);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter.setOnItemClickListener(new ScanHisAdapter.OnItemClickListener() {
            @Override
            public void onDelete(View view, int position) {
                deleteItem(position);
            }

            @Override
            public void onView(View view, int position) {
                viewItem(position);
            }
        });
    }

    private void deleteItem(int position) {
        mUserArrays.remove(position);
        mAdapter.notifyItemRemoved(position);

        if (mUserArrays.size() == 0) {
            mEmptyImage.setVisibility(View.VISIBLE);
        }
    }

    private void viewItem(int position) {
        // TODO
    }

    /**
     * ===========================> DATA
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void loadDataFromFirebase() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(DBConst.SCAN_TABLE_NAME);
        databaseReference.child("1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        ScanningHistoryDTO scanningHistoryDTO = data.getValue(ScanningHistoryDTO.class);
                        UserDTO userDTO = Utils.getUserDTOFromId(scanningHistoryDTO.getRemoteUserID());
                        mUserArrays.add(userDTO);
                    }

                    mAdapter.notifyDataSetChanged();
                    if (mUserArrays.size() != 0) {
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