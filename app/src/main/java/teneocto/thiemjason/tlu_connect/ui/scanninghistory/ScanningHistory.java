package teneocto.thiemjason.tlu_connect.ui.scanninghistory;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.ui.adapter.ScanHisAdapter;
import teneocto.thiemjason.tlu_connect.utils.AppConst;
import teneocto.thiemjason.tlu_connect.ui.progressdialog.CustomProgressDialog;

/**
 * Scanning History Activity
 */
public class ScanningHistory extends AppCompatActivity {
    /**
     * Components
     */
    Button mBackButton;
    RecyclerView mRecycleView;
    ScanHisAdapter mAdapter;
    AdView adView;
    View mEmptyImage;
    CustomProgressDialog progressDialog;

    /**
     * View models
     */
    ScanningHistoryViewModel viewModel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning_history);
        progressDialog = new CustomProgressDialog(this, "");

        // Init ViewModel
        viewModel = new ViewModelProvider(this).get(ScanningHistoryViewModel.class);
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
     * @param position Position
     */
    private void deleteItem(int position) {
        Log.i(AppConst.TAG_ScanningHistory, "Selected position: " + position);
        Log.i(AppConst.TAG_ScanningHistory, "Arrays size: " + viewModel.mUserScanned.size());

        viewModel.mUserScanned.remove(position);
        mAdapter.notifyItemRemoved(position);
        mAdapter.notifyItemRangeChanged(position, viewModel.mUserScanned.size());
        this.hideEmptyImage();
    }

    /**
     * Handle when user show item
     * @param position Position
     */
    private void viewItem(int position) {
        Log.i(AppConst.TAG_ScanningHistory, "Selected position: " + position);
        Log.i(AppConst.TAG_ScanningHistory, "Arrays size: " + viewModel.mUserScanned.size());
        // TODO
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}