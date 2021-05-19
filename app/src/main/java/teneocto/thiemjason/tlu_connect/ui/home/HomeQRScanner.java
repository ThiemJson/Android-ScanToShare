package teneocto.thiemjason.tlu_connect.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import teneocto.thiemjason.tlu_connect.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeQRScanner#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeQRScanner extends Fragment {
    private CodeScanner mCodeScanner;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "==> SCANNER ";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeQRScanner() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeQRScanner.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeQRScanner newInstance(String param1, String param2) {
        HomeQRScanner fragment = new HomeQRScanner();
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
        Log.i(TAG, "On create");
    }

    /**
     * Life cycle
     */
    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "On Start");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "On Stop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "On Destroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "On DestroyView");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "On create view");
        View root = inflater.inflate(R.layout.fragment_home_q_r_scanner, container, false);
        CodeScannerView scannerView = root.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(getActivity(), scannerView);

        if(mCodeScanner == null ){
            Log.i(TAG, " NULL OBJECT");
        }

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
        return root;
    }

    @Override
    public void onResume() {

        super.onResume();
        Log.i(TAG, "On Resume");
    }

    public void startPreview() {
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        if (mCodeScanner == null ){
            super.onPause();
            return;
        }
        Log.i(TAG, "On Pause");
        mCodeScanner.releaseResources();
        super.onPause();
    }
}