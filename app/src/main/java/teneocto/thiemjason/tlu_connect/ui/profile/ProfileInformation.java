package teneocto.thiemjason.tlu_connect.ui.profile;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.models.UserDTO;
import teneocto.thiemjason.tlu_connect.utils.MultipleTextWatcher;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileInformation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileInformation extends Fragment {
    // View model
    ProfileSharedViewModel viewModel;

    // EditText
    EditText mFirstName;
    EditText mLastName;
    EditText mEmail;
    EditText mCompany;
    EditText mPosition;

    // Text Watcher
    MultipleTextWatcher multipleTextWatcher;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileInformation() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileInformation.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileInformation newInstance(String param1, String param2) {
        ProfileInformation fragment = new ProfileInformation();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_infomation, container, false);

        mFirstName = view.findViewById(R.id.profile_first_name);
        mLastName = view.findViewById(R.id.profile_last_name);
        mEmail = view.findViewById(R.id.profile_email);
        mCompany = view.findViewById(R.id.profile_company);
        mPosition = view.findViewById(R.id.profile_position);

        viewModel = new ViewModelProvider(requireActivity()).get(ProfileSharedViewModel.class);
        viewModel.userDataFetched.observe(getViewLifecycleOwner(), aBoolean -> fillData(viewModel.userDTO));
        return view;
    }

    private void fillData(UserDTO userDTO) {
        mFirstName.setText(userDTO.getFirstName());
        mLastName.setText(userDTO.getLastName());
        mEmail.setText(userDTO.getEmail());
        mCompany.setText(userDTO.getCompany());
        mPosition.setText(userDTO.getPosition());

        // Init text change listener
        if (multipleTextWatcher != null) {
            multipleTextWatcher = null;
        }

        // Setup multiple text changed
        multipleTextWatcher = new MultipleTextWatcher()
                .registerEditText(mFirstName)
                .registerEditText(mLastName)
                .registerEditText(mEmail)
                .registerEditText(mCompany)
                .registerEditText(mPosition)
                .setCallback(new MultipleTextWatcher.TextWatcherWithInstance() {
                    @Override
                    public void beforeTextChanged(EditText editText, CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(EditText editText, CharSequence s, int start, int before, int count) {

                    }

                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public void afterTextChanged(EditText editText, Editable editable) {
                        viewModel.hideShowBtnTool.setValue(true);

                        switch (editText.getId()) {
                            case R.id.profile_first_name:
                                viewModel.userDTO.setFirstName(editText.getText().toString());
                                break;
                            case R.id.profile_last_name:
                                viewModel.userDTO.setLastName(editText.getText().toString());
                                break;
                            case R.id.profile_email:
                                viewModel.userDTO.setEmail(editText.getText().toString());
                                break;
                            case R.id.profile_company:
                                viewModel.userDTO.setCompany(editText.getText().toString());
                                break;
                            case R.id.profile_position:
                                viewModel.userDTO.setPosition(editText.getText().toString());
                                break;  
                        }
                    }
                });
    }
}