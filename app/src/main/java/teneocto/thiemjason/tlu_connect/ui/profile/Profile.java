package teneocto.thiemjason.tlu_connect.ui.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.R;

public class Profile extends AppCompatActivity {
    TabLayout mTabLayout;
    ViewPager mViewPager;
    MainAdapter mAdapter;

    /**
     * Constructor
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        this.initialTabLayout();
        Button backbutton = findViewById(R.id.profile_menu_icon);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Init tablayout
     */
    private void initialTabLayout() {
        mTabLayout = findViewById(R.id.profile_tablayout);
        mViewPager = findViewById(R.id.profile_view_paper);

        mAdapter = new MainAdapter(getSupportFragmentManager(), 12);

        // Add fragment
        mAdapter.AddFragment(new ProfileSocialNetwork(), "Social Network");
        mAdapter.AddFragment(new ProfileInfomation(), "Infomation");

        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private class MainAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        ArrayList<String> stringArrayList = new ArrayList<>();

        public MainAdapter(@NonNull FragmentManager fm, int behavior) {
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