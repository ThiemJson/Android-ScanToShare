package teneocto.thiemjason.tlu_connect.ui.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.ui.adapter.RegisterAdapter;
import teneocto.thiemjason.tlu_connect.ui.bottomactionsheet.BottomSheetFragment;
import teneocto.thiemjason.tlu_connect.ui.models.SocialNetwork;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileSocialNetwork#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileSocialNetwork extends Fragment {
    RecyclerView mSocialRecyclerView;
    RegisterAdapter mSocialAdapter;
    ArrayList<SocialNetwork> socialNetwork;

    FloatingActionButton mFloatingButton;
    BottomSheetFragment mBottomSheetFragment;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileSocialNetwork() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileSocialNetwork.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileSocialNetwork newInstance(String param1, String param2) {
        ProfileSocialNetwork fragment = new ProfileSocialNetwork();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile_social_network, container, false);

        this.initRecycleView(view);
        return view;
    }

    private void initDummyData() {
        this.socialNetwork = new ArrayList<SocialNetwork>();
        this.socialNetwork.add(new SocialNetwork(R.drawable.facebook, "fb.com/thiemtinhte"));
        this.socialNetwork.add(new SocialNetwork(R.drawable.linkedin, "linkedin.com/thiemjason"));
    }

    private void initRecycleView(View view) {
        this.initDummyData();
        this.mFloatingButton = view.findViewById(R.id.tab_view_social_fab);
        this.mSocialRecyclerView = view.findViewById(R.id.tab_view_social_recycle_view);

        this.mSocialAdapter = new RegisterAdapter(getActivity(), this.socialNetwork);
        this.mSocialRecyclerView.setAdapter(this.mSocialAdapter);
        this.mSocialRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        this.mSocialAdapter.setOnItemClickListener(new RegisterAdapter.OnItemClickListener() {
            @Override
            public void onDelete(View view, int position) {
                removeItem(position);
            }
        });

        this.mFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabOnClick();
            }
        });
    }

    /**
     * Add / remove item func
     */
    public void removeItem(int position) {
        socialNetwork.remove(position);
        this.mSocialAdapter.notifyItemRemoved(position);
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
        this.mSocialAdapter.notifyItemInserted(this.socialNetwork.size());
    }

    /**
     * Handler when user clicked on Floating Action button
     */
    private void fabOnClick() {
        this.mBottomSheetFragment = new BottomSheetFragment(getActivity(), new BottomSheetFragment.OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                bottomSheetItemClick(view, position);
            }
        });
        mBottomSheetFragment.show( getFragmentManager(), mBottomSheetFragment.getTag());
    }

    /**
     * On BottomSheetItem click
     */
    private void bottomSheetItemClick(View view, int position) {
        TextView name = view.findViewById(R.id.action_item_name);
        this.mBottomSheetFragment.dismiss();
        addItem(name.getText().toString());
    }
}