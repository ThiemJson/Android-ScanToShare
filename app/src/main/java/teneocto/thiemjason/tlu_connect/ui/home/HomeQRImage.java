package teneocto.thiemjason.tlu_connect.ui.home;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import androidmads.library.qrgenearator.QRGEncoder;
import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.database.DBConst;
import teneocto.thiemjason.tlu_connect.firebase.FirebaseDBHelper;
import teneocto.thiemjason.tlu_connect.models.SharedDTO;
import teneocto.thiemjason.tlu_connect.models.SocialNetworkDTO;
import teneocto.thiemjason.tlu_connect.models.UserDTO;
import teneocto.thiemjason.tlu_connect.ui.home.slider.HomeSliderAdapter;
import teneocto.thiemjason.tlu_connect.utils.AppConst;
import teneocto.thiemjason.tlu_connect.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeQRImage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeQRImage extends Fragment {
    public ArrayList<SharedDTO> sharedDTOArrays;
    public ViewPager2 viewPager2;
    public Gson gson;

    // Firebase
    FirebaseDBHelper firebaseDBHelper;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    // Element
    TextView itemName;
    TextView itemUrl;
    ImageView qrImage;
    View emptyImage;
    View mRULContainer;
    Button mShareImageBtn;
    LinearLayout sliderContainer;

    // URL container
    private ClipboardManager mClipboard;
    private ClipData mClipData;

    // Adapter
    HomeSliderAdapter homeSliderAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "==> HOME QR IMAGE";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeQRImage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeQRImage.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeQRImage newInstance(String param1, String param2) {
        HomeQRImage fragment = new HomeQRImage();
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
        Log.i(TAG, "On Create");
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
    public void onPause() {
        super.onPause();

        Log.i(TAG, "On Pause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "On Stop");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "On Resume");
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_slider_contaiter, container, false);

        sharedDTOArrays = new ArrayList<>();
        itemName = view.findViewById(R.id.home_slider_item_name);
        qrImage = view.findViewById(R.id.home_qr_image);
        itemUrl = view.findViewById(R.id.home_url_text);
        emptyImage = view.findViewById(R.id.home_slider_con_empty);
        mRULContainer = view.findViewById(R.id.home_view_url_container);
        mShareImageBtn = view.findViewById(R.id.home_slider_share_image);
        sliderContainer = view.findViewById(R.id.home_slider_linearlayout);
        mRULContainer.setOnClickListener(v -> copyDataToClipboard());
        mShareImageBtn.setOnClickListener(v -> shareImage(container.getContext()));

        this.loadDataFromFirebase();
        this.initSlider(view);
        return view;
    }

    private void initSlider(View view) {
        Log.i(TAG, "==> INIT SLIDER");
        this.gson = new Gson();
        viewPager2 = view.findViewById(R.id.home_view_slider_container);

        homeSliderAdapter = new HomeSliderAdapter(sharedDTOArrays, viewPager2);
        viewPager2.setAdapter(homeSliderAdapter);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.setOrientation(viewPager2.ORIENTATION_HORIZONTAL);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_ALWAYS);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(1));

        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(1f + r * 0.2f);
            page.setScaleX(1f + r * 0.2f);
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                pageChange();
            }
        });

        // Back - Forward button
        ImageView backButton = view.findViewById(R.id.home_back_arrow);
        ImageView forwardButton = view.findViewById(R.id.home_forward_arrow);
        backButton.setOnClickListener(v -> backButtonOnLick());
        forwardButton.setOnClickListener(v -> forwardButtonOnLick());

        hideShowHomeComponent();
    }

    private void hideShowHomeComponent(){
        if(sharedDTOArrays.size() == 0){
            this.qrImage.setVisibility(View.GONE);
            this.sliderContainer.setVisibility(View.GONE);
            this.mRULContainer.setVisibility(View.GONE);
            this.mShareImageBtn.setVisibility(View.GONE);

            this.emptyImage.setVisibility(View.VISIBLE);
        }
        else{
            this.qrImage.setVisibility(View.VISIBLE);
            this.emptyImage.setVisibility(View.GONE);
            this.qrImage.setVisibility(View.VISIBLE);
            this.sliderContainer.setVisibility(View.VISIBLE);
            this.mRULContainer.setVisibility(View.VISIBLE);
            this.mShareImageBtn.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Back or forward by button
     */
    private void backButtonOnLick() {
        if (viewPager2.getCurrentItem() == 0) {
            return;
        }
        viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
    }

    private void forwardButtonOnLick() {
        if (viewPager2.getCurrentItem() == sharedDTOArrays.size()) {
            return;
        }
        viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void pageChange() {
        int position = viewPager2.getCurrentItem();
        String socialNWId = sharedDTOArrays.get(position).getSocialNetWorkID();
        List<SocialNetworkDTO> socialNetworkDTO = Utils.socialNetworkDTOArrayList.stream().filter(x -> x.getId().equals(socialNWId)).collect(Collectors.toList());
        itemUrl.setText(sharedDTOArrays.get(position).getUrl());
        itemName.setText(socialNetworkDTO.get(0).getName());
        QRGEncoder qrgEncoder = Utils.generateQRCodeFromContent(getActivity(), sharedDTOArrays.get(position).getUrl());
        qrImage.setImageBitmap(qrgEncoder.getBitmap());
    }

    private void copyDataToClipboard() {
        ClipboardManager _clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Nothing", itemUrl.getText());
        _clipboard.setPrimaryClip(clip);
        Toast.makeText(getActivity(), "Copy to clipboard", Toast.LENGTH_SHORT).show();
    }

    private void shareImage(Context context) {
        int position = viewPager2.getCurrentItem();
        QRGEncoder qrgEncoder = Utils.generateQRCodeFromContent(getActivity(), sharedDTOArrays.get(position).getUrl());
        Uri uri = Utils.getImageUri(context, qrgEncoder.getBitmap());
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareIntent, "Select"));
    }

    /**
     * ===========================> DATA
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void loadDataFromFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(DBConst.SHARED_TABLE_NAME);
        databaseReference.child(AppConst.USER_UID_Static).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        sharedDTOArrays.add(data.getValue(SharedDTO.class));
                    }
                    homeSliderAdapter.notifyDataSetChanged();
                    pageChange();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadDataFromSQLite();
            }
        });
    }

    private void loadDataFromSQLite() {
    }
}