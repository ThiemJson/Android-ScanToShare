package teneocto.thiemjason.tlu_connect.ui.notification;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import teneocto.thiemjason.tlu_connect.utils.AppConst;
import teneocto.thiemjason.tlu_connect.utils.CustomProgressDialog;

public class Notification extends AppCompatActivity {
    Button mBackButton;
    AdView adView;
    View mEmptyImage;

    // RecycleView
    NotificationAdapter mAdapter;
    RecyclerView mRecycleView;

    // View model
    NotificationViewModel viewModel;
    CustomProgressDialog progressDialog;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        progressDialog = new CustomProgressDialog(this, "");

        mBackButton = findViewById(R.id.notification_menu_icon);
        mRecycleView = findViewById(R.id.notification_recycle_view);
        mEmptyImage = findViewById(R.id.notification_empty);
        mBackButton.setOnClickListener(v -> finish());

        viewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        viewModel.isFetched.observe(this, aBoolean -> {
            mAdapter.notifyDataSetChanged();
            hideEmptyImage();

            if(progressDialog != null){
                progressDialog.deleteProgressDialog();
            }
        });
        viewModel.loadDataFromFirebase();

        this.initRecycleView();
        this.loadAd();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initRecycleView() {
        mAdapter = new NotificationAdapter(viewModel.notificationDTOArrayList, this);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setOnItemClickListener((view, position) -> viewNotification(position));
        hideEmptyImage();

    }

    private void hideEmptyImage() {
        if (viewModel.notificationDTOArrayList.size() != 0) {
            mEmptyImage.setVisibility(View.GONE);
        } else {
            mEmptyImage.setVisibility(View.VISIBLE);
        }
    }

    private void viewNotification(int position) {
        Log.i(AppConst.TAG_Notification, "Selected position: " + position);
        Log.i(AppConst.TAG_Notification, "Arrays size: " + viewModel.notificationDTOArrayList.size());
        // TODO

        Intent viewNotificationIntent = new Intent(this, NotificationWebView.class);
        viewNotificationIntent.putExtra("URL", viewModel.notificationDTOArrayList.get(position).getUrl());
        startActivity(viewNotificationIntent);
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
}