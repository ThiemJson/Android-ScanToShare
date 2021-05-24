package teneocto.thiemjason.tlu_connect.ui.scanninghistory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.ui.adapter.ScanHisAdapter;
import teneocto.thiemjason.tlu_connect.ui.models.User;

public class ScanningHistory extends AppCompatActivity {
    Button mBackButton;
    RecyclerView mRecycleView;
    ScanHisAdapter mAdapter;
    AdView adView;
    View mEmptyImage;

    // DUMMY DATA
    ArrayList<User> mUserArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning_history);

        this.initView();
        this.initRecycleView();
        this.initialAds();

        if (mUserArrayList.size() != 0) {
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

    private void initRecycleView() {
        this.initDummyData();
        mRecycleView = findViewById(R.id.scanning_his_recycle_view);
        mAdapter = new ScanHisAdapter(mUserArrayList, this);
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
        mUserArrayList.remove(position);
        mAdapter.notifyItemRemoved(position);

        if (mUserArrayList.size() == 0) {
            mEmptyImage.setVisibility(View.VISIBLE);
        }
    }

    private void viewItem(int position) {
        // TODO
    }

    private void initDummyData() {
        mUserArrayList = new ArrayList<>();
        mUserArrayList.add(new User("Nguyen Cao Thiem", R.drawable.user_example, "nguyencaothiem292@gmail.com"));
        mUserArrayList.add(new User("Nguyen Cao Thiem", R.drawable.user_example, "nguyencaothiem292@gmail.com"));
        mUserArrayList.add(new User("Nguyen Cao Thiem", R.drawable.user_example, "nguyencaothiem292@gmail.com"));
        mUserArrayList.add(new User("Nguyen Cao Thiem", R.drawable.user_example, "nguyencaothiem292@gmail.com"));
        mUserArrayList.add(new User("Nguyen Cao Thiem", R.drawable.user_example, "nguyencaothiem292@gmail.com"));
        mUserArrayList.add(new User("Nguyen Cao Thiem", R.drawable.user_example, "nguyencaothiem292@gmail.com"));
        mUserArrayList.add(new User("Nguyen Cao Thiem", R.drawable.user_example, "nguyencaothiem292@gmail.com"));
    }
}