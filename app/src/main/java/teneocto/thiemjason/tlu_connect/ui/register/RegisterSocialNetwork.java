package teneocto.thiemjason.tlu_connect.ui.register;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.firebase.FirebaseDBHelper;
import teneocto.thiemjason.tlu_connect.models.SharedDTO;
import teneocto.thiemjason.tlu_connect.ui.adapter.RegisterSocialNetworkAdapter;
import teneocto.thiemjason.tlu_connect.ui.bottomactionsheet.BottomSheetFragment;
import teneocto.thiemjason.tlu_connect.ui.drawer.Drawer;
import teneocto.thiemjason.tlu_connect.utils.AppConst;
import teneocto.thiemjason.tlu_connect.ui.progressdialog.CustomProgressDialog;
import teneocto.thiemjason.tlu_connect.utils.Utils;

public class RegisterSocialNetwork extends AppCompatActivity {
    public static String TAG = "==> Register Social Network";

    FloatingActionButton mFloatingButton;
    BottomSheetFragment mBottomSheetFragment;

    // Main recycle view
    RecyclerView mRecyclerView;
    RegisterSocialNetworkAdapter mRegisterSocialNetworkAdapter;
    View mEmpty;

    // Buttons
    Button mBackButton;
    Button mNextButton;

    // View model
    RegisterSocialNetworkViewModel viewModel;
    CustomProgressDialog progressDialog;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_social_network);
        viewModel = ViewModelProviders.of(this).get(RegisterSocialNetworkViewModel.class);
        viewModel.sharedDTOArrays = new ArrayList<>();
        this.initRecycleView();

        this.mFloatingButton = findViewById(R.id.btn_register_social_add);
        this.mFloatingButton.setOnClickListener(v -> fabOnClick());

        mBackButton = findViewById(R.id.btn_register_social_back);
        mNextButton = findViewById(R.id.btn_register_social_facebook_next);
        mEmpty = findViewById(R.id.register_social_network_empty);
        hideShowEmptyImage();

        mBackButton.setOnClickListener(v -> backButton());
        mNextButton.setOnClickListener(v -> nextButton());

        // Add place holder
        addItem("Facebook");
        addItem("Instagram");
    }

    /**
     * Hid show empty image
     */
    private void hideShowEmptyImage(){
        if(viewModel.sharedDTOArrays.size() == 0){
            this.mEmpty.setVisibility(View.VISIBLE);
        }
        else{
         this.mEmpty.setVisibility(View.GONE);
        }
    }

    /**
     * Init recycle view
     */
    private void initRecycleView() {
        this.mRecyclerView = findViewById(R.id.register_social_recycle_view);
        this.mRegisterSocialNetworkAdapter = new RegisterSocialNetworkAdapter(this, viewModel.sharedDTOArrays);
        this.mRecyclerView.setAdapter(this.mRegisterSocialNetworkAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRegisterSocialNetworkAdapter.setOnEditTextChange(new RegisterSocialNetworkAdapter.OnEditTextChange() {
            @Override
            public void beforeTextChanged(int position, String text) {

            }

            @Override
            public void onTextChanged(int position, String text) {

            }

            @Override
            public void afterTextChanged(int position, String text) {
                Log.i(AppConst.TAG_RegisterSocialNetworkViewModel, viewModel.sharedDTOArrays.get(position).getUrl() + " ==> text: " + text);
                viewModel.sharedDTOArrays.get(position).setUrl(text);
            }
        });
        mRegisterSocialNetworkAdapter.setOnItemClickListener((view, position) -> {
            viewModel.sharedDTOArrays.remove(position);
            Log.i(AppConst.TAG_RegisterSocialNetworkViewModel, " tesssttt: position " + position);
            Log.i(AppConst.TAG_RegisterSocialNetworkViewModel, " tesssttt: viewmodel" + viewModel.sharedDTOArrays.size());
            Log.i(AppConst.TAG_RegisterSocialNetworkViewModel, " tesssttt: adapter " + mRegisterSocialNetworkAdapter.sharedDTOArrays.size());

            mRegisterSocialNetworkAdapter.notifyItemRemoved(position);
            mRegisterSocialNetworkAdapter.notifyItemRangeChanged(position, viewModel.sharedDTOArrays.size());
            hideShowEmptyImage();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addItem(String name) {
        SharedDTO sharedDTO = null;

        if ("Instagram".equals(name)) {
            sharedDTO = new SharedDTO(Utils.getRandomUUID(), AppConst.USER_UID_Static, Utils.getSocialNWDTOFromName("Instagram").getId(), "https://instagram.com/");
        }

        if ("Twitter".equals(name)) {
            sharedDTO = new SharedDTO(Utils.getRandomUUID(), AppConst.USER_UID_Static, Utils.getSocialNWDTOFromName("Twitter").getId(), "https://twitter.com/");
        }

        if ("Snapchat".equals(name)) {
            sharedDTO = new SharedDTO(Utils.getRandomUUID(), AppConst.USER_UID_Static, Utils.getSocialNWDTOFromName("Snapchat").getId(), "https://snapchat.com/add/");
        }

        if ("LinkedIn".equals(name)) {
            sharedDTO = new SharedDTO(Utils.getRandomUUID(), AppConst.USER_UID_Static, Utils.getSocialNWDTOFromName("LinkedIn").getId(), "https://linkedin.com/in/");
        }

        if ("Facebook".equals(name)) {
            sharedDTO = new SharedDTO(Utils.getRandomUUID(), AppConst.USER_UID_Static, Utils.getSocialNWDTOFromName("Facebook").getId(), "https://facebook.com/");
        }
        viewModel.addShared(sharedDTO);
        this.mRegisterSocialNetworkAdapter.notifyItemInserted(viewModel.sharedDTOArrays.size());
        hideShowEmptyImage();
    }

    /**
     * Handler when user clicked on Floating Action button
     */

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void fabOnClick() {
        if (mBottomSheetFragment != null) {
            mBottomSheetFragment.dismiss();
        }
        this.mBottomSheetFragment = new BottomSheetFragment(this, (view, position) -> bottomSheetItemClick(view, position));
        mBottomSheetFragment.show(getSupportFragmentManager(), mBottomSheetFragment.getTag());
    }

    /**
     * On UIBottomSheetItemDTO click
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void bottomSheetItemClick(View view, int position) {
        TextView name = view.findViewById(R.id.action_item_name);
        this.mBottomSheetFragment.dismiss();
        addItem(name.getText().toString());
    }

    void backButton() {
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void nextButton() {
        progressDialog = new CustomProgressDialog(this);
        if (!viewModel.verifyUserInput()) {
            if (progressDialog != null) {
                progressDialog.deleteProgressDialog();
            }
            Toast.makeText(this, "Make sure your " + viewModel.errorField + " URL is correct !", Toast.LENGTH_SHORT).show();
            return;
        }

        Utils.sharedDTOArrayList = viewModel.sharedDTOArrays;
        registerUser(Utils.registerUserDTO.getEmail());
    }

    private void registerUser(String userEmail) {
        firebaseSignUpWithEmail(userEmail, AppConst.USER_PASS);
    }

    /**
     * Sign Up
     *
     * @param email    user email
     * @param password immutable password
     */
    public void firebaseSignUpWithEmail(String email, String password) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.i(AppConst.TAG_FirebaseAuthentication, "createUserWithEmail: success");
                        firebaseSignInWithEmail(email, password);
                    } else {
                        Log.w(AppConst.TAG_FirebaseAuthentication, "signInWithEmail:failure", task.getException());
                        Toast.makeText(this, "The email address is already in use by another account", Toast.LENGTH_SHORT).show();
                        progressDialog.deleteProgressDialog();
                    }
                });
    }

    /**
     * Sign In
     *
     * @param email    user email
     * @param password immutable password
     */
    public void firebaseSignInWithEmail(String email, String password) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(AppConst.TAG_FirebaseAuthentication, "signInWithEmail:success");

                        Utils.registerUserDTO.setFirebaseId(mAuth.getCurrentUser().getUid());
                        Utils.setPrefer(getApplicationContext(), AppConst.USER_UID, Utils.registerUserDTO.getId());
                        AppConst.USER_UID_Static = Utils.registerUserDTO.getId();

                        // Sync data into firebase
                        FirebaseDBHelper firebaseDBHelper = new FirebaseDBHelper();
                        firebaseDBHelper.USER_Insert(Utils.registerUserDTO);

                        Log.i(AppConst.TAG_RegisterService, " Current user UID: " + Utils.registerUserDTO.getFirebaseId());
                        for (SharedDTO sharedDTO : Utils.sharedDTOArrayList) {
                            sharedDTO.setUserID(Utils.getUserUUID(getApplicationContext()));
                            firebaseDBHelper.Shared_Insert(sharedDTO);
                            Log.i(AppConst.TAG_RegisterService, " SharedDTO: " + Utils.registerUserDTO.getFirebaseId());
                        }

                        // DONE
                        Toast.makeText(this, "Register successfully !", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, Drawer.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        progressDialog.deleteProgressDialog();
                        startActivity(intent);
                    } else {
                        Log.w(AppConst.TAG_FirebaseAuthentication, "signInWithEmail:failure", task.getException());
                        Toast.makeText(this, "The email address is already in use by another account", Toast.LENGTH_SHORT).show();
                        progressDialog.deleteProgressDialog();
                    }
                });
    }
}