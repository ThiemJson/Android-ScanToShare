package teneocto.thiemjason.tlu_connect.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.models.SocialNetwork;

public class RegisterSocialNetwork extends AppCompatActivity {
    public static String TAG = "==> Register Social Network";
    RecyclerView recyclerView;
    RegisterAdapter registerAdapter;
    ArrayList<SocialNetwork>  socialNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_social_network);

        this.initDummyData();
        this.initRecycleView();

        Log.i(TAG, this.socialNetwork.size() + "");
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

    public void addItem(int positionn) {
        this.socialNetwork.add(new SocialNetwork(R.drawable.facebook, "fb.com/thiemtinhte"));
        this.registerAdapter.notifyItemInserted(this.socialNetwork.size());
    }

    /**
     *
     */
    private void initDummyData() {
        this.socialNetwork = new ArrayList<SocialNetwork>();

        this.socialNetwork.add(new SocialNetwork(R.drawable.facebook, "fb.com/thiemtinhte"));
        this.socialNetwork.add(new SocialNetwork(R.drawable.linkedin, "linkedin.com/thiemjason"));
        this.socialNetwork.add(new SocialNetwork(R.drawable.sapchat, "snapchat.com/thiemtinhte"));
        this.socialNetwork.add(new SocialNetwork(R.drawable.twiiter, "twitter.com/thiemtinhte"));
        this.socialNetwork.add(new SocialNetwork(R.drawable.instagram, "instagram.com/thiemtinhte"));
        this.socialNetwork.add(new SocialNetwork(R.drawable.instagram, "instagram.com/thiemtinhte"));
        this.socialNetwork.add(new SocialNetwork(R.drawable.instagram, "instagram.com/thiemtinhte"));
        this.socialNetwork.add(new SocialNetwork(R.drawable.instagram, "instagram.com/thiemtinhte"));
        this.socialNetwork.add(new SocialNetwork(R.drawable.instagram, "instagram.com/thiemtinhte"));
        this.socialNetwork.add(new SocialNetwork(R.drawable.instagram, "instagram.com/thiemtinhte"));
        this.socialNetwork.add(new SocialNetwork(R.drawable.instagram, "instagram.com/thiemtinhte"));
        this.socialNetwork.add(new SocialNetwork(R.drawable.instagram, "instagram.com/thiemtinhte"));

    }
}