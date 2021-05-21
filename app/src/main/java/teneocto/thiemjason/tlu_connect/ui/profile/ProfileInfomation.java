package teneocto.thiemjason.tlu_connect.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.Gson;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.ui.models.User;
import teneocto.thiemjason.tlu_connect.utils.AppConst;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileInfomation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileInfomation extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileInfomation() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileInfomation.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileInfomation newInstance(String param1, String param2) {
        ProfileInfomation fragment = new ProfileInfomation();
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
        getDataFromSharedPrefer(view);
        return view;
    }

    void getDataFromSharedPrefer(View viewGroup){
        Gson gson = new Gson();
        SharedPreferences prefs =  viewGroup.getContext().getSharedPreferences(AppConst.mainSharedPrefer,
                Context.MODE_PRIVATE);
        String userJson = prefs.getString("USERDATA", "NULL");
        User user = gson.fromJson(userJson, User.class);

        EditText firstName = viewGroup.findViewById(R.id.profile_frag_first_name);
        EditText lastName = viewGroup.findViewById(R.id.profile_frag_last_name);
        EditText email = viewGroup.findViewById(R.id.profile_frag_email);
        EditText company = viewGroup.findViewById(R.id.profile_frag_company);
        EditText position = viewGroup.findViewById(R.id.profile_frag_position);

        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        email.setText(user.getEmail());
        company.setText(user.getLocation());
        position.setText(user.getLocation());
        position.setText(user.getLocation());

    }
}