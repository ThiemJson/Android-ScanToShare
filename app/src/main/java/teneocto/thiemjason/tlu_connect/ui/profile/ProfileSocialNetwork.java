package teneocto.thiemjason.tlu_connect.ui.profile;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.models.SharedDTO;
import teneocto.thiemjason.tlu_connect.ui.adapter.RegisterAdapter;
import teneocto.thiemjason.tlu_connect.ui.bottomactionsheet.BottomSheetFragment;
import teneocto.thiemjason.tlu_connect.utils.AppConst;
import teneocto.thiemjason.tlu_connect.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileSocialNetwork#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileSocialNetwork extends Fragment {
    RecyclerView mSocialRecyclerView;
    View mEmptyImage;
    RegisterAdapter mSocialAdapter;
    FloatingActionButton mFloatingButton;
    BottomSheetFragment mBottomSheetFragment;

    // View model
    ProfileSharedViewModel viewModel;
    String tempText = "";

    // Handle for edit text auto fill

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
        View view = inflater.inflate(R.layout.fragment_profile_social_network, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ProfileSharedViewModel.class);
        this.mFloatingButton = view.findViewById(R.id.tab_view_social_fab);
        this.mSocialRecyclerView = view.findViewById(R.id.tab_view_social_recycle_view);
        this.mEmptyImage = view.findViewById(R.id.profile_social_network_empty);

        this.initRecycleView();
        viewModel.dataFetched.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                mSocialAdapter.notifyDataSetChanged();
                hideShowEmptyImage();
            } else {
                mSocialAdapter.notifyDataSetChanged();
                mSocialAdapter.notifyItemRangeChanged(0, viewModel.sharedDTOLiveData.size());
                hideShowEmptyImage();
            }
        });

        // Revert Data
        viewModel.isDataReverted.observe(getViewLifecycleOwner(), aBoolean -> {
            Log.i(AppConst.TAG_ProfileSharedViewModel, "revert social network");
            viewModel.sharedDTOLiveData.clear();
            mSocialAdapter.notifyItemRangeRemoved(0, viewModel.sharedDTOLiveData.size());
            viewModel.sharedDTOLiveData = Utils.cloneSharedDTO(viewModel.oldSharedDTOs);

            this.initRecycleView();
            hideShowEmptyImage();
            viewModel.hideShowBtnTool.setValue(false);
        });

        hideShowEmptyImage();
        return view;
    }

    private void initRecycleView() {
        this.mSocialAdapter = new RegisterAdapter(getActivity(), viewModel.sharedDTOLiveData);
        this.mSocialRecyclerView.setAdapter(this.mSocialAdapter);
        this.mSocialRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        this.mFloatingButton.setOnClickListener(v -> fabOnClick());

        mSocialAdapter.setOnEditTextChange(new RegisterAdapter.OnEditTextChange() {
            @Override
            public void beforeTextChanged(int position, String text) {
                tempText = text;
            }

            @Override
            public void onTextChanged(int position, String text) {

            }

            @Override
            public void afterTextChanged(int position, String text) {
                Log.i(AppConst.TAG_Profile_Social_NW, viewModel.sharedDTOLiveData.get(position).getUrl() + " ==> text: " + text);
                viewModel.sharedDTOLiveData.get(position).setUrl(text);
                viewModel.hideShowBtnTool.setValue(true);
            }
        });

        // Delete item
        mSocialAdapter.setOnItemClickListener((view1, position) -> {
            viewModel.sharedDTOLiveData.remove(position);
            Log.i(AppConst.TAG_Profile_Social_NW, " tesssttt: position " + position);
            Log.i(AppConst.TAG_Profile_Social_NW, " tesssttt: viewmodel" + viewModel.sharedDTOLiveData.size());
            Log.i(AppConst.TAG_Profile_Social_NW, " tesssttt: adapter " + viewModel.sharedDTOLiveData.size());

            mSocialAdapter.notifyItemRemoved(position);
            mSocialAdapter.notifyItemRangeChanged(position, viewModel.sharedDTOLiveData.size());
            hideShowEmptyImage();

            viewModel.hideShowBtnTool.setValue(true);
        });
    }

    private void hideShowEmptyImage() {
        if (viewModel.sharedDTOLiveData.size() == 0) {
            this.mSocialRecyclerView.setVisibility(View.INVISIBLE);
            this.mEmptyImage.setVisibility(View.VISIBLE);
        } else {
            this.mSocialRecyclerView.setVisibility(View.VISIBLE);
            this.mEmptyImage.setVisibility(View.GONE);
        }
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
        this.mSocialAdapter.notifyItemInserted(viewModel.sharedDTOLiveData.size());
        hideShowEmptyImage();
        viewModel.hideShowBtnTool.setValue(true);
    }

    /**
     * Handler when user clicked on Floating Action button
     */
    private void fabOnClick() {
        if (mBottomSheetFragment != null) {
            mBottomSheetFragment.dismiss();
        }

        this.mBottomSheetFragment = new BottomSheetFragment(getActivity(), new BottomSheetFragment.OnItemClick() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(View view, int position) {
                bottomSheetItemClick(view, position);
            }
        });
        mBottomSheetFragment.show(getFragmentManager(), mBottomSheetFragment.getTag());
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
}