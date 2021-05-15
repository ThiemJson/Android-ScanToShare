package teneocto.thiemjason.tlu_connect.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.adapter.RegisterAdapter;
import teneocto.thiemjason.tlu_connect.bottomactionsheet.BottomSheetFragment;
import teneocto.thiemjason.tlu_connect.models.SocialNetwork;

public class Profile extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    MainAdapter adapter;

    /**
     * Constructor
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        this.initialTabLayout();
    }

    /**
     * Init tablayout
     */
    private void initialTabLayout() {
        tabLayout = findViewById(R.id.profile_tablayout);
        viewPager = findViewById(R.id.profile_view_paper);

        adapter = new MainAdapter(getSupportFragmentManager(), 12);

        // Add fragment
        adapter.AddFragment(new ProfileSocialNetwork(), "Social Network");
        adapter.AddFragment(new ProfileInfomation(), "Infomation");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
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