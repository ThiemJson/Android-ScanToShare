package teneocto.thiemjason.tlu_connect.ui.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.ui.adapter.RegisterAdapter;
import teneocto.thiemjason.tlu_connect.ui.bottomactionsheet.BottomSheetFragment;
import teneocto.thiemjason.tlu_connect.ui.drawer.Drawer;
import teneocto.thiemjason.tlu_connect.ui.models.SocialNetworkDTO;

public class RegisterSocialNetwork extends AppCompatActivity {
    public static String TAG = "==> Register Social Network";

    FloatingActionButton mFloatingButton;
    BottomSheetFragment mBottomSheetFragment;

    // Main recycle view
    RecyclerView mRecyclerView;
    RegisterAdapter mRegisterAdapter;
    ArrayList<SocialNetworkDTO> socialNetworkDTO;

    // Buttons
    Button mBackButton;
    Button mNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_social_network);
        this.initDummyData();
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
        this.mRegisterAdapter = new RegisterAdapter(this, this.socialNetworkDTO);
        this.mRecyclerView.setAdapter(this.mRegisterAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRegisterAdapter.setOnItemClickListener((view, position) -> removeItem(position));
    }

    /**
     * Add / remove item func
     */
    public void removeItem(int position) {
        socialNetworkDTO.remove(position);
        this.mRegisterAdapter.notifyItemRemoved(position);
    }

    public void addItem(String name) {
        switch (name) {
            case "Facebook":
                this.socialNetworkDTO.add(new SocialNetworkDTO(R.drawable.facebook, "fb.com/thiemtinhte"));
                break;
            case "Instagram":
                this.socialNetworkDTO.add(new SocialNetworkDTO(R.drawable.instagram, "instagram.com/thiemjason"));
                break;
            case "Twitter":
                this.socialNetworkDTO.add(new SocialNetworkDTO(R.drawable.twiiter, "twitter.com/thiemtinhte"));
                break;
            case "Snapchat":
                this.socialNetworkDTO.add(new SocialNetworkDTO(R.drawable.sapchat, "snapchat.com/thiem"));
                break;
            case "LinkedIn":
                this.socialNetworkDTO.add(new SocialNetworkDTO(R.drawable.linkedin, "linkedIn.com/thiemtinhte"));
                break;
        }
        this.mRegisterAdapter.notifyItemInserted(this.socialNetworkDTO.size());
    }

    /**
     * DUMMY DATA
     */
    private void initDummyData() {
        this.socialNetworkDTO = new ArrayList<SocialNetworkDTO>();

        this.socialNetworkDTO.add(new SocialNetworkDTO(R.drawable.facebook, "fb.com/thiemtinhte"));
        this.socialNetworkDTO.add(new SocialNetworkDTO(R.drawable.linkedin, "linkedin.com/thiemjason"));
    }

    /**
     * Handler when user clicked on Floating Action button
     */

    private void fabOnClick() {
        this.mBottomSheetFragment = new BottomSheetFragment(this, new BottomSheetFragment.OnItemClick() {
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
    private void bottomSheetItemClick(View view, int position) {
        TextView name = view.findViewById(R.id.action_item_name);
        this.mBottomSheetFragment.dismiss();
        addItem(name.getText().toString());
    }

    void backButton() {
        finish();
    }

    void nextButton() {
        Intent intent = new Intent(this, Drawer.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}