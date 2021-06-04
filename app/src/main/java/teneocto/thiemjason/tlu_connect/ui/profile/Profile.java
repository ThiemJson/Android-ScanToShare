package teneocto.thiemjason.tlu_connect.ui.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.ui.progressdialog.CustomProgressDialog;
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

    // Tab bar buttons
    Button mCancelBtn;
    Button mSaveBtn;

    // Confirm dialog
    Button dialogSaveBtn;
    Button dialogNotSaveBtn;
    Dialog confirmDialog;

    /**
     * Constructor
     *
     * @param savedInstanceState
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        progressDialog = new CustomProgressDialog(this, "");
        this.initialTabLayout();
        Button backButton = findViewById(R.id.profile_menu_icon);
        backButton.setOnClickListener(v -> onBackButtonClick());
    }

    /**
     * Init Tab layout
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initialTabLayout() {
        mTabLayout = findViewById(R.id.profile_tablayout);
        mViewPager = findViewById(R.id.profile_view_paper);
        mSaveBtn = findViewById(R.id.profile_save_button);
        mCancelBtn = findViewById(R.id.profile_cancel_button);
        mImagePicker = findViewById(R.id.profile_image);
        mImagePicker.setOnClickListener(v -> imagePicker());

        mSaveBtn.setOnClickListener(v -> toolBarSaveBtn());
        mCancelBtn.setOnClickListener(v -> toolBarCancelBtn());

        // Hide tool button
        mSaveBtn.setVisibility(View.INVISIBLE);
        mCancelBtn.setVisibility(View.INVISIBLE);

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

        // Handle when user change profile
        sharedViewModel.hideShowBtnTool.setValue(false);
        sharedViewModel.hideShowBtnTool.observe(this, aBoolean -> {
            if (aBoolean) {
                mSaveBtn.setVisibility(View.VISIBLE);
                mCancelBtn.setVisibility(View.VISIBLE);
            } else {
                mSaveBtn.setVisibility(View.INVISIBLE);
                mCancelBtn.setVisibility(View.INVISIBLE);
            }
        });

        // Handle when user change data
        sharedViewModel.isDataSubmitted.observe(this, aBoolean -> {
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
            sharedViewModel.hideShowBtnTool.setValue(true);
        }
    }

    /**
     * Handle when user click on back button
     */
    public void onBackButtonClick() {
        if (!sharedViewModel.hideShowBtnTool.getValue()) {
            finish();
            return;
        }

        this.showConfirmDialog();
    }

    /**
     * Confirm dialog
     * OK -> Commit data changed
     * Cancel => Cancel data changed
     */
    private void showConfirmDialog() {
        confirmDialog = new Dialog(this);
        confirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        confirmDialog.setContentView(R.layout.profile_setting_confirm_dialog);
        confirmDialog.setCancelable(false);
        confirmDialog.show();

        dialogSaveBtn = confirmDialog.findViewById(R.id.profile_confirm_dialog_save_btn);
        dialogNotSaveBtn = confirmDialog.findViewById(R.id.profile_confirm_dialog_doesnt_save_btn);

        dialogSaveBtn.setOnClickListener(v -> confirmDialogSaveBtn());
        dialogNotSaveBtn.setOnClickListener(v -> confirmDialogCancelBtn());
    }

    private void confirmDialogSaveBtn() {
        confirmDialog.dismiss();
        progressDialog = new CustomProgressDialog(this, "");
        // Submit data
    }

    private void confirmDialogCancelBtn() {
        confirmDialog.dismiss();
        progressDialog = new CustomProgressDialog(this, "");
        // Reset Data
        sharedViewModel.revertData();
    }

    private void toolBarSaveBtn(){

    }

    private void toolBarCancelBtn(){
        sharedViewModel.revertData();
    }
}