package teneocto.thiemjason.tlu_connect.ui.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.utils.CustomProgressDialog;
import teneocto.thiemjason.tlu_connect.utils.Utils;

public class Profile extends AppCompatActivity {
    TabLayout mTabLayout;
    ViewPager mViewPager;
    MainAdapter mAdapter;
    ImageView mImagePicker;

    int SELECT_PHOTO = 1;
    Uri uri;

    // View Model
    ProfileSharedViewModel sharedViewModel;
    CustomProgressDialog progressDialog;

    /**
     * Constructor
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        progressDialog = new CustomProgressDialog(this, "");
        this.initialTabLayout();
        Button backButton = findViewById(R.id.profile_menu_icon);
        backButton.setOnClickListener(v -> finish());
    }

    /**
     * Init Tab layout
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initialTabLayout() {
        mTabLayout = findViewById(R.id.profile_tablayout);
        mViewPager = findViewById(R.id.profile_view_paper);
        mImagePicker = findViewById(R.id.profile_image);
        mImagePicker.setOnClickListener(v -> imagePicker());

        mAdapter = new MainAdapter(getSupportFragmentManager(), 12);
        sharedViewModel = new ViewModelProvider(this).get(ProfileSharedViewModel.class);
        sharedViewModel.loadDataFromFirebase();

        // Update Profile icon
        sharedViewModel.userDataFetched.observe(this, aBoolean -> {
            String imageBase64 = sharedViewModel.userDTO.getImageBase64();
            Bitmap imageBitmap = Utils.getBitmapFromByteArray(imageBase64);

            mImagePicker.setImageBitmap(imageBitmap);

            if (progressDialog != null) {
                progressDialog.deleteProgressDialog();
            }
        });


        // Add fragment
        mAdapter.AddFragment(new ProfileSocialNetwork(), "Social Network");
        mAdapter.AddFragment(new ProfileInformation(), "Information");

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

    /**
     * Image Picker
     */
    void imagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO && data != null && data.getData() != null) {
            uri = data.getData();
            mImagePicker.setImageURI(uri);
        }
    }
}