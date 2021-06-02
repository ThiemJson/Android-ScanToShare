package teneocto.thiemjason.tlu_connect.ui.register;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Base64;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.database.DBHelper;
import teneocto.thiemjason.tlu_connect.models.UserDTO;
import teneocto.thiemjason.tlu_connect.utils.Utils;

public class RegisterProfile extends AppCompatActivity {
    private RegisterProfileViewModel mRegisterProfileViewModel;
    private UserDTO userDTO;
    public static String TAG = "RegisterProfile";
    ImageView mImagePicker;
    int SELECT_PHOTO = 1;
    Uri uri;

    // Buttons
    Button mBackButton;
    Button mNext;

    // Elements
    EditText mFirstName;
    EditText mLastName;
    EditText mEmail;
    EditText mPosition;
    EditText mCompany;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_your_profile);
        this.initActivity();

        mBackButton = findViewById(R.id.register_profile_menu_icon);
        mNext = findViewById(R.id.register_btn_main_ac_skip);
        mLastName = findViewById(R.id.register_edt_last_name);
        mFirstName = findViewById(R.id.register_edt_first_name);
        mEmail = findViewById(R.id.register_edt_email);
        mPosition = findViewById(R.id.register_edt_position);
        mCompany = findViewById(R.id.register_edt_company);

        if (Utils.registerUserDTO != null) {
            mLastName.setText(Utils.registerUserDTO.getLastName());
            mFirstName.setText(Utils.registerUserDTO.getFirstName());
            mEmail.setText(Utils.registerUserDTO.getEmail());
            mPosition.setText(Utils.registerUserDTO.getPosition());
            mCompany.setText(Utils.registerUserDTO.getCompany());

            Bitmap imageBitmap = Utils.getBitmapFromByteArray(Utils.registerUserDTO.getImageBase64());
            mImagePicker.setImageBitmap(imageBitmap);
        }

        mBackButton.setOnClickListener(v -> backButton());
        mNext.setOnClickListener(v -> nextButton());
        mRegisterProfileViewModel = ViewModelProviders.of(this).get(RegisterProfileViewModel.class);

        userDTO = Utils.registerUserDTO;
        if (userDTO == null) {
            userDTO = new UserDTO();
        }
        mRegisterProfileViewModel.isVerify.observe(this, aBoolean -> {
            // False
            if (!aBoolean) {
                Toast.makeText(this, "Please make sure your email address is correct", Toast.LENGTH_SHORT).show();
                return;
            }
            // True
            mRegisterProfileViewModel.registerProfile();
            Intent intent = new Intent(this, RegisterSocialNetwork.class);
            startActivity(intent);
        });
    }

    void initActivity() {
        mImagePicker = findViewById(R.id.register_profile_image);
        mImagePicker.setOnClickListener(v -> imagePicker());
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

    void backButton() {
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void nextButton() {
        String userCompany = mCompany.getText().toString().trim();
        if (userCompany.equals("")){
            userCompany = "Your company";
        }

        String userLastName = mLastName.getText().toString().trim();
        if (userLastName.equals("")){
            userLastName = "Your last name";
        }

        String userFirstName = mFirstName.getText().toString().trim();
        if (userFirstName.equals("")){
            userFirstName = "Your first name";
        }

        String userPosition = mPosition.getText().toString().trim();
        if (userPosition.equals("")){
            userPosition  = "Your position";
        }


        userDTO.setCompany(userCompany);
        userDTO.setLastName(userLastName);
        userDTO.setFirstName(userFirstName);
        userDTO.setEmail(mEmail.getText(). toString().trim());
        userDTO.setPosition(userPosition);
        userDTO.setId(Utils.getRandomUUID());
        BitmapDrawable drawable = (BitmapDrawable) mImagePicker.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        byte[] imageBase64 = Utils.getBitmapAsByteArray(bitmap);
        userDTO.setImageBase64(Base64.getEncoder().encodeToString(imageBase64));
        mRegisterProfileViewModel.setUser(userDTO);
    }
}