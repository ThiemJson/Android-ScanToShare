package teneocto.thiemjason.tlu_connect.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    HomeFragment.HomeAdapter adapter;

//    // Fragment
    HomeQRImage homeQRImage;
    HomeQRScanner homeQRScanner;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "==> HOME FRAGMENT";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
        Log.i("HOME FRAGMENT", "Contructor");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        Log.i(TAG, "On Create");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Log.i(TAG, "On View Create");
        this.initTabLayout(view, container, savedInstanceState);
        return view;
    }

    /**
     * Life cycle
     */
    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "On Start");
    }

    @Override
    public void onPause() {
        super.onPause();

        if ( this.homeQRScanner != null) {
            this.homeQRScanner.onPause();
        }
        else {
            Log.i(TAG, "On Pause => QR SCANNER NULL");
        }

        Log.i(TAG, "On Pause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "On Stop");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "On Resume");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "On Destroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "On DestroyView");
        homeQRScanner = null;
        homeQRImage = null;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        Log.i("HOME FRAGMENT", "new instance");
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * Init tablayout
     */
    void initTabLayout(View view, ViewGroup viewGroup, Bundle bundle) {
        Log.i(TAG, "On Init tablayout");
        tabLayout = view.findViewById(R.id.home_tablayout);
        viewPager = view.findViewById(R.id.home_view_paper);

        adapter = new HomeFragment.HomeAdapter(getFragmentManager(), 12);

        HomeQRImage homeQRImage = new HomeQRImage();
        HomeQRScanner homeQRScanner = new HomeQRScanner();

        this.homeQRImage = homeQRImage;
        this.homeQRScanner = homeQRScanner;
        adapter.AddFragment(homeQRImage, "QR List");
        adapter.AddFragment(homeQRScanner, "Scan QR");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        homeQRScanner.onPause();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        homeQRScanner.onPause();
                        break;
                    case 1:
                        homeQRScanner.startPreview();
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

    public class HomeAdapter extends FragmentPagerAdapter {
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