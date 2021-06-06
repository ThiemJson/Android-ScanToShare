package teneocto.thiemjason.tlu_connect.ui.home;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.google.zxing.Result;

import androidmads.library.qrgenearator.QRGEncoder;
import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.ui.progressdialog.CustomProgressDialog;
import teneocto.thiemjason.tlu_connect.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeQRScanner#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeQRScanner extends Fragment {

    private CodeScanner mCodeScanner;

    private Dialog mDialog;

    private HomeResultScanner homeResultScanner;

    private CustomProgressDialog progressDialog;
    
    /**
     * Result
     */
    View mResultContainer;
    ImageView mResultCloseBtn;
    ImageView mResultUserImage;
    ImageView mResultUserIcon;
    TextView mResultUserName;
    TextView mResultUserUrl;
    Button mResultCopyBtn;


    /**
     * Empty
     */
    View mEmptyContainer;
    Button mEmptyCopyBtn;
    TextView mEmptyUserUrl;
    ImageView mEmptyCloseBtn;

    /**
     * Loading
     */
    View mLoadingContainer;

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
        initView(root);
        mCodeScanner = new CodeScanner(getActivity(), scannerView);
        homeResultScanner = new HomeResultScanner(getActivity());

        if (mCodeScanner == null) {
            Log.i(TAG, " NULL OBJECT");
        }

        mCodeScanner.setDecodeCallback(result -> getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog = new CustomProgressDialog(container.getContext(), "");
                showDataWhenScanned(result);
            }
        }));
        scannerView.setOnClickListener(view -> mCodeScanner.startPreview());
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
        if (mCodeScanner == null) {
            super.onPause();
            return;
        }
        Log.i(TAG, "On Pause");
        mCodeScanner.releaseResources();
        super.onPause();
    }

    /**
     * Show data when user scanned
     *
     * @param result
     */
    private void showDataWhenScanned(Result result) {
        mDialog = new Dialog(getActivity());
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCancelable(true);
        mDialog.setContentView(R.layout.home_qr_code_result);

        TextView mUsername = mDialog.findViewById(R.id.home_result_user_name);
        TextView mEmail = mDialog.findViewById(R.id.home_result_email);
        TextView mAddress = mDialog.findViewById(R.id.home_result_address);
        TextView mUrl = mDialog.findViewById(R.id.home_url_text);

        ImageView mProfileImage = mDialog.findViewById(R.id.home_profile_image_result);
        ImageView mQRImage = mDialog.findViewById(R.id.home_result_qr_image);

        Button mCancelBtn = mDialog.findViewById(R.id.home_result_cancel_btn);
        Button mSaveBtn = mDialog.findViewById(R.id.home_result_save_btn);
        Button mViewMore = mDialog.findViewById(R.id.home_result_viewmore_btn);

        QRGEncoder qrgEncoder = Utils.generateQRCodeFromContent(getActivity(), result.getText());
        mQRImage.setImageBitmap(qrgEncoder.getBitmap());
        mUrl.setText(result.getText());

        mCancelBtn.setOnClickListener(v -> homeResultScanner.onSaveUserClick());
        mViewMore.setOnClickListener(v -> homeResultScanner.onViewMoreClick());

        if (progressDialog != null) {
            progressDialog.deleteProgressDialog();
        }
        mDialog.show();
    }

    /**
     * Init View
     */
    private void initView(View view) {

        // Result
        mResultContainer = view.findViewById(R.id.home_scanned_result);
        mResultCloseBtn = view.findViewById(R.id.scanned_result_close_btn);
        mResultUserImage = view.findViewById(R.id.scanned_result_user_image);
        mResultUserIcon = view.findViewById(R.id.scanned_result_socialnw_icon);
        mResultUserName = view.findViewById(R.id.scanned_result_username);
        mResultUserUrl = view.findViewById(R.id.scanned_result_user_url);
        mResultCopyBtn = view.findViewById(R.id.scanned_result_copy_btn);

        // Empty
        mEmptyContainer = view.findViewById(R.id.home_scanned_empty);
        mEmptyCopyBtn = view.findViewById(R.id.scanned_empty_copy_btn);
        mEmptyUserUrl = view.findViewById(R.id.scanned_empty_user_url);
        mEmptyCloseBtn = view.findViewById(R.id.scanned_empty_close_btn);

        // Loading
        mLoadingContainer = view.findViewById(R.id.home_scanned_loading);

        // Hide
        mResultContainer.setVisibility(View.GONE);
        mEmptyContainer.setVisibility(View.GONE);
        mLoadingContainer.setVisibility(View.GONE);

        // Close button events onclick
        mEmptyCloseBtn.setOnClickListener(v -> mEmptyContainer.setVisibility(View.GONE));
        mResultCloseBtn.setOnClickListener(v -> mResultContainer.setVisibility(View.GONE));
    }
}