package teneocto.thiemjason.tlu_connect.ui.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.ui.notification.Notification;
import teneocto.thiemjason.tlu_connect.ui.profile.Profile;

public class Home extends AppCompatActivity {
    TabLayout mTabLayout;
    ViewPager mViewPager;
    HomeAdapter mAdapter;
    Button mNotification;
    CircleImageView mMainImage;

    // Fragment
    HomeQRImage mHomeQRImage;
    HomeQRScanner mHomeQRScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.initTabLayout();

        mNotification = findViewById(R.id.home_notification_icon);
        mMainImage = findViewById(R.id.home_profile_image);
        mMainImage.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Profile.class)));
        mNotification.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Notification.class)));
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Init tablayout
     */
    void initTabLayout() {
        mTabLayout = findViewById(R.id.home_tablayout);
        mViewPager = findViewById(R.id.home_view_paper);

        mAdapter = new Home.HomeAdapter(getSupportFragmentManager(), 12);

        mHomeQRImage = new HomeQRImage();
        mHomeQRScanner = new HomeQRScanner();
        mAdapter.AddFragment(mHomeQRImage, "QR List");
        mAdapter.AddFragment(mHomeQRScanner, "Scan QR");

        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mHomeQRScanner.onPause();

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        mHomeQRScanner.onPause();
                        break;
                    case 1:
                        mHomeQRScanner.startPreview();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private class HomeAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        ArrayList<String> stringArrayList = new ArrayList<>();

        public HomeAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void AddFragment(Fragment fragment, String s) {
            fragmentArrayList.add(fragment);
            stringArrayList.add(s);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentArrayList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentArrayList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return stringArrayList.get(position);
        }
    }
}