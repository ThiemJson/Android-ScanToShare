package teneocto.thiemjason.tlu_connect.ui.register;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Base64;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.database.DBHelper;
import teneocto.thiemjason.tlu_connect.firebase.FirebaseDBHelper;
import teneocto.thiemjason.tlu_connect.models.NotificationDTO;
import teneocto.thiemjason.tlu_connect.models.ScanningHistoryDTO;
import teneocto.thiemjason.tlu_connect.models.SharedDTO;
import teneocto.thiemjason.tlu_connect.models.SocialNetworkDTO;
import teneocto.thiemjason.tlu_connect.models.UserDTO;
import teneocto.thiemjason.tlu_connect.utils.Utils;

public class RegisterProfile extends AppCompatActivity {
    public static String TAG = "RegisterProfile";
    ImageView mImagePicker;
    DBHelper dbHelper;
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

        mBackButton.setOnClickListener(v -> backButton());
        mNext.setOnClickListener(v -> nextButton());
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
        Intent intent = new Intent(this, RegisterSocialNetwork.class);
        startActivity(intent);
    }
}