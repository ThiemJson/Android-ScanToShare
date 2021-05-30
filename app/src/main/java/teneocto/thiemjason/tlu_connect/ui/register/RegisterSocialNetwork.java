package teneocto.thiemjason.tlu_connect.ui.register;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
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

import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.models.SharedDTO;
import teneocto.thiemjason.tlu_connect.ui.adapter.RegisterAdapter;
import teneocto.thiemjason.tlu_connect.ui.bottomactionsheet.BottomSheetFragment;
import teneocto.thiemjason.tlu_connect.ui.drawer.Drawer;
import teneocto.thiemjason.tlu_connect.ui.models.SocialNetworkDTO;
import teneocto.thiemjason.tlu_connect.utils.AppConst;
import teneocto.thiemjason.tlu_connect.utils.Utils;

public class RegisterSocialNetwork extends AppCompatActivity {
    public static String TAG = "==> Register Social Network";

    FloatingActionButton mFloatingButton;
    BottomSheetFragment mBottomSheetFragment;

    // Main recycle view
    RecyclerView mRecyclerView;
    RegisterAdapter mRegisterAdapter;
    ArrayList<SharedDTO> sharedDTOArrays;

    // Buttons
    Button mBackButton;
    Button mNextButton;

    // View model
    RegisterSocialNetworkViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_social_network);
        sharedDTOArrays = new ArrayList<>();
        viewModel = new RegisterSocialNetworkViewModel();
        this.initRecycleView();

        this.mFloatingButton = findViewById(R.id.btn_register_social_add);
        this.mFloatingButton.setOnClickListener(v -> fabOnClick());

        mBackButton = findViewById(R.id.btn_register_social_back);
        mNextButton = findViewById(R.id.btn_register_social_facebook_next);

        mBackButton.setOnClickListener(v -> backButton());
        mNextButton.setOnClickListener(v -> nextButton());
    }


    /**
     * Init recycle view
     */
    private void initRecycleView() {
        this.mRecyclerView = findViewById(R.id.register_social_recycle_view);
        this.mRegisterAdapter = new RegisterAdapter(this, this.sharedDTOArrays);
        this.mRecyclerView.setAdapter(this.mRegisterAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRegisterAdapter.setOnItemClickListener((view, position) -> removeItem(position));
    }

    /**
     * Add / remove item func
     */
    public void removeItem(int position) {
        sharedDTOArrays.remove(position);
        this.mRegisterAdapter.notifyItemRemoved(position);
        viewModel.sharedDTOLiveData.getValue().remove(position);
        viewModel.sharedDTOLiveData.setValue(viewModel.sharedDTOLiveData.getValue());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addItem(String name) {
        SharedDTO sharedDTO;
        switch (name) {
            case "Facebook":
                sharedDTO = new SharedDTO(1, AppConst.SP_CURRENT_USER_ID, Utils.getSocialNWDTOFromName("Facebook").getId(), "facebook.com/");
                this.sharedDTOArrays.add(sharedDTO);
                break;
            case "Instagram":
                sharedDTO = new SharedDTO(1, AppConst.SP_CURRENT_USER_ID, Utils.getSocialNWDTOFromName("Instagram").getId(), "instagram.com/");
                this.sharedDTOArrays.add(sharedDTO);
                break;
            case "Twitter":
                sharedDTO = new SharedDTO(1, AppConst.SP_CURRENT_USER_ID, Utils.getSocialNWDTOFromName("Twitter").getId(), "twitter.com/");
                this.sharedDTOArrays.add(sharedDTO);
                break;
            case "Snapchat":
                sharedDTO = new SharedDTO(1, AppConst.SP_CURRENT_USER_ID, Utils.getSocialNWDTOFromName("Snapchat").getId(), "snapchat.com/add/");
                this.sharedDTOArrays.add(sharedDTO);
                break;
            case "LinkedIn":
                sharedDTO = new SharedDTO(1, AppConst.SP_CURRENT_USER_ID, Utils.getSocialNWDTOFromName("LinkedIn").getId(), "linkedin.com/in/");
                this.sharedDTOArrays.add(sharedDTO);
                break;
            default:
                sharedDTO = new SharedDTO(1, AppConst.SP_CURRENT_USER_ID, Utils.getSocialNWDTOFromName("Facebook").getId(), "facebook.com/");
        }
        this.mRegisterAdapter.notifyItemInserted(this.sharedDTOArrays.size());
        viewModel.addShared(sharedDTO);
    }

    /**
     * Handler when user clicked on Floating Action button
     */

    private void fabOnClick() {
        this.mBottomSheetFragment = new BottomSheetFragment(this, new BottomSheetFragment.OnItemClick() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(View view, int position) {
                bottomSheetItemClick(view, position);
            }
        });
        mBottomSheetFragment.show(getSupportFragmentManager(), mBottomSheetFragment.getTag());
    }

    /**
     * On BottomSheetItemDTO click
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

    void nextButton() {
        if (viewModel.isVerify.getValue() == null || viewModel.isVerify.getValue() == false) {
            Toast.makeText(this, "Make sure all social network url is correct", Toast.LENGTH_SHORT).show();
        }

        viewModel.registerSocialNetwork();
        Log.i(AppConst.TAG_RegisterSocialNetworkViewModel, viewModel.sharedDTOLiveData.getValue().size() + "");
        Intent intent = new Intent(this, Drawer.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}