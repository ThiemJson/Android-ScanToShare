package teneocto.thiemjason.tlu_connect.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import info.androidhive.barcode.BarcodeReader;
import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.profile.Profile;

public class Home extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    HomeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.initTabLayout();
    }

    /**
     * Init tablayout
     */
    void initTabLayout() {
        tabLayout = findViewById(R.id.home_tablayout);
        viewPager = findViewById(R.id.home_view_paper);

        adapter = new Home.HomeAdapter(getSupportFragmentManager(), 12);

        adapter.AddFragment(new HomeQRImage() , "QR List");
        adapter.AddFragment(new HomeQRScanner() , "Scan QR");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
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