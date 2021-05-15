package teneocto.thiemjason.tlu_connect.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.adapter.BottomSheetAdapter;
import teneocto.thiemjason.tlu_connect.adapter.RegisterAdapter;
import teneocto.thiemjason.tlu_connect.bottomactionsheet.BottomSheetFragment;
import teneocto.thiemjason.tlu_connect.models.BottomSheetItem;
import teneocto.thiemjason.tlu_connect.models.SocialNetwork;

public class RegisterSocialNetwork extends AppCompatActivity {
    public static String TAG = "==> Register Social Network";

    FloatingActionButton mainFAB;
    BottomSheetFragment bottomSheetFragment;

    // Main recycle view
    RecyclerView recyclerView;
    RegisterAdapter registerAdapter;
    ArrayList<SocialNetwork> socialNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_social_network);
        this.initDummyData();
        this.initRecycleView();

        this.mainFAB = findViewById(R.id.btn_register_social_add);
        this.mainFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabOnClick();
            }
        });
    }


    /**
     * Init recycle view
     */
    private void initRecycleView() {
        this.recyclerView = findViewById(R.id.register_social_recycle_view);
        this.registerAdapter = new RegisterAdapter(this, this.socialNetwork);
        this.recyclerView.setAdapter(this.registerAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        registerAdapter.setOnItemClickListener(new RegisterAdapter.OnItemClickListener() {
            @Override
            public void onDelete(View view, int position) {
                removeItem(position);
            }
        });
    }

    /**
     * Add / remove item func
     */
    public void removeItem(int position) {
        socialNetwork.remove(position);
        this.registerAdapter.notifyItemRemoved(position);
    }

    public void addItem(String name) {
        switch (name) {
            case "Facebook":
                this.socialNetwork.add(new SocialNetwork(R.drawable.facebook, "fb.com/thiemtinhte"));
                break;
            case "Instagram":
                this.socialNetwork.add(new SocialNetwork(R.drawable.instagram, "instagram.com/thiemjason"));
                break;
            case "Twitter":
                this.socialNetwork.add(new SocialNetwork(R.drawable.twiiter, "twitter.com/thiemtinhte"));
                break;
            case "Snapchat":
                this.socialNetwork.add(new SocialNetwork(R.drawable.sapchat, "snapchat.com/thiem"));
                break;
            case "LinkedIn":
                this.socialNetwork.add(new SocialNetwork(R.drawable.linkedin, "linkedIn.com/thiemtinhte"));
                break;
        }
        this.registerAdapter.notifyItemInserted(this.socialNetwork.size());
    }

    /**
     * DUMMY DATA
     */
    private void initDummyData() {
        this.socialNetwork = new ArrayList<SocialNetwork>();

        this.socialNetwork.add(new SocialNetwork(R.drawable.facebook, "fb.com/thiemtinhte"));
        this.socialNetwork.add(new SocialNetwork(R.drawable.linkedin, "linkedin.com/thiemjason"));
    }

    /**
     * Handler when user clicked on Floating Action button
     */

    private void fabOnClick() {
        this.bottomSheetFragment = new BottomSheetFragment(this, new BottomSheetFragment.OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                bottomSheetItemClick(view, position);
            }
        });
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    /**
     * On BottomSheetItem click
     */
    private void bottomSheetItemClick(View view, int position) {
        TextView name = view.findViewById(R.id.action_item_name);
        this.bottomSheetFragment.dismiss();
        addItem(name.getText().toString());
    }
}