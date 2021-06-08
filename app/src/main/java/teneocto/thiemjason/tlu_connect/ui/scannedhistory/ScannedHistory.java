package teneocto.thiemjason.tlu_connect.ui.scannedhistory;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.database.DBConst;
import teneocto.thiemjason.tlu_connect.models.ScannedDTO;
import teneocto.thiemjason.tlu_connect.ui.adapter.ScanHisAdapter;
import teneocto.thiemjason.tlu_connect.utils.AppConst;
import teneocto.thiemjason.tlu_connect.ui.progressdialog.CustomProgressDialog;
import teneocto.thiemjason.tlu_connect.utils.Utils;

/**
 * Scanning History Activity
 */
public class ScannedHistory extends AppCompatActivity {
    /**
     * Components
     */
    Button mBackButton;

    RecyclerView mRecycleView;

    ScanHisAdapter mAdapter;

    AdView adView;

    View mEmptyImage;

    CustomProgressDialog progressDialog;

    Dialog confirmDialog;

    /**
     * View models
     */
    ScannedHistoryViewModel viewModel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning_history);
        progressDialog = new CustomProgressDialog(this, "");

        // Init ViewModel
        viewModel = new ViewModelProvider(this).get(ScannedHistoryViewModel.class);
        viewModel.loadDataFromFirebase();

        this.initView();
        this.initRecycleView();
        this.initialAds();

        viewModel.isFetched.observe(this, aBoolean -> {
            mAdapter.notifyDataSetChanged();
            this.hideEmptyImage();

            if (progressDialog != null) {
                progressDialog.deleteProgressDialog();
            }
        });
    }

    /**
     * Hide or Show Empty Image
     */
    private void hideEmptyImage() {
        if (viewModel.mUserScanned.size() == 0) {
            mEmptyImage.setVisibility(View.VISIBLE);
        } else {
            mEmptyImage.setVisibility(View.GONE);
        }
    }

    /**
     * init view
     */
    void initView() {
        mBackButton = findViewById(R.id.scanning_menu_icon);
        mBackButton.setOnClickListener(v -> finish());
        mEmptyImage = findViewById(R.id.scanning_history_empty);
    }

    /**
     * Init Google Ads Mob
     */
    private void initialAds() {
        MobileAds.initialize(this, initializationStatus -> {
        });
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    /**
     * Init Recycle View
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initRecycleView() {
        mRecycleView = findViewById(R.id.scanning_his_recycle_view);
        mAdapter = new ScanHisAdapter(viewModel.mUserScanned, this);
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

    /**
     * Handle when user delete item
     *
     * @param position Position
     */
    private void deleteItem(int position) {
        Log.i(AppConst.TAG_ScanningHistory, "Selected position: " + position);
        Log.i(AppConst.TAG_ScanningHistory, "Arrays size: " + viewModel.mUserScanned.size());

        confirmDialog = new Dialog(this);
        confirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        confirmDialog.setContentView(R.layout.scanned_history_delete);
        confirmDialog.setCancelable(true);
        confirmDialog.show();

        Button deleteBtn = confirmDialog.findViewById(R.id.scanned_confirm_dialog_delete_btn);
        Button closeBtn = confirmDialog.findViewById(R.id.scanned_confirm_dialog_close_btn);

        closeBtn.setOnClickListener(v -> confirmDialog.dismiss());
        deleteBtn.setOnClickListener(v -> {
            confirmDialog.dismiss();
            progressDialog = new CustomProgressDialog(this, "");
            deleteItemInFirebase(viewModel.mUserScanned.get(position), position);
        });


    }

    /**
     * Delete item in Firebase Database
     */
    private void deleteItemInFirebase(ScannedDTO scannedDTO, int position) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(DBConst.SCAN_TABLE_NAME);

        databaseReference.child(Utils.getUserUUID(this)).child(scannedDTO.getId()).removeValue().addOnCompleteListener(task -> {
            viewModel.mUserScanned.remove(position);
            mAdapter.notifyItemRemoved(position);
            mAdapter.notifyItemRangeChanged(position, viewModel.mUserScanned.size());
            hideEmptyImage();

            Toast.makeText(this, "Deleted !", Toast.LENGTH_SHORT).show();
            if (progressDialog != null) {
                progressDialog.deleteProgressDialog();
            }
        }).addOnCanceledListener(() -> {
            Toast.makeText(this, "Something wrong \n Please check your internet connection !", Toast.LENGTH_SHORT).show();
            if (progressDialog != null) {
                progressDialog.deleteProgressDialog();
            }
        });
    }

    /**
     * Handle when user show item
     *
     * @param position Position
     */
    private void viewItem(int position) {
        Log.i(AppConst.TAG_ScanningHistory, "Selected position: " + position);
        Log.i(AppConst.TAG_ScanningHistory, "Arrays size: " + viewModel.mUserScanned.size());

        Intent viewScannedUserIntent = new Intent(this, ScannedHistoryWebView.class);
        viewScannedUserIntent.putExtra(AppConst.SCANNED_userUrl, viewModel.mUserScanned.get(position).getUrl());
        startActivity(viewScannedUserIntent);
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}