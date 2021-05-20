package teneocto.thiemjason.tlu_connect.ui.home;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.ui.home.slider.HomeSliderAdapter;
import teneocto.thiemjason.tlu_connect.ui.models.HomeSliderItem;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeQRImage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeQRImage extends Fragment {
    public ArrayList<HomeSliderItem> homeSliderItems;
    public ViewPager2 viewPager2;
    public Gson gson;

    // Element
    TextView itemName;
    TextView itemUrl;
    ImageView qrImage;
    View mRULContainer;
    Button mSharImageBtn;

    // URL container
    private ClipboardManager mClipboard;
    private ClipData mClipData;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_slider_contaiter, container, false);

        itemName = view.findViewById(R.id.home_slider_item_name);
        qrImage = view.findViewById(R.id.home_qr_image);
        itemUrl = view.findViewById(R.id.home_url_text);
        mRULContainer = view.findViewById(R.id.home_view_url_container);
        mSharImageBtn = view.findViewById(R.id.home_slider_share_image);
        mRULContainer.setOnClickListener(v -> copyDataToClipboard());
        mSharImageBtn.setOnClickListener(v -> shareImage(container.getContext()));

        Log.i(TAG, "On View Create");
        this.initSlider(view);
        return view;
    }

    private void initSlider(View view) {
        this.gson = new Gson();
        viewPager2 = view.findViewById(R.id.home_view_slider_container);

        this.initalData();
        HomeSliderAdapter homeSliderAdapter = new HomeSliderAdapter(homeSliderItems, viewPager2);
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
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                pageChange();
            }
        });

        // Back - Forward button
        ImageView backButton = view.findViewById(R.id.home_back_arrow);
        ImageView forwardButton = view.findViewById(R.id.home_forward_arrow);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButtonOnLick();
            }
        });
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forwardButtonOnLick();
            }
        });
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
        if (viewPager2.getCurrentItem() == homeSliderItems.size()) {
            return;
        }
        viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
    }

    private void initalData() {
        homeSliderItems = new ArrayList<>();
        homeSliderItems.add(new HomeSliderItem(R.drawable.facebook, "Facebook",
                serializeQREncoder(generateQRCodeFromContent("https://facebook.com/thiemtinhte")),
                "https://facebook.com/thiemtinhte"));
        homeSliderItems.add(new HomeSliderItem(R.drawable.linkedin, "LinkedIn",
                serializeQREncoder(generateQRCodeFromContent("https://www.linkedin.com/in/cao-thiem-nguyen-628945206/")),
                "https://www.linkedin.com/in/cao-thiem-nguyen-628945206/"));
        homeSliderItems.add(new HomeSliderItem(R.drawable.sapchat, "Snapchat",
                serializeQREncoder(generateQRCodeFromContent("https://www.snapchat.com/add/magicmenlive")),
                "https://www.snapchat.com/add/magicmenlive"));
        homeSliderItems.add(new HomeSliderItem(R.drawable.twiiter, "Twitter",
                serializeQREncoder(generateQRCodeFromContent("https://twitter.com/ThiemJaso")),
                "https://twitter.com/ThiemJason"));
        homeSliderItems.add(new HomeSliderItem(R.drawable.instagram, "Instagram",
                serializeQREncoder(generateQRCodeFromContent("https://www.instagram.com/thiemjason/")),
                "https://www.instagram.com/thiemjason/"));

        pageChange();
    }

    private QRGEncoder generateQRCodeFromContent(String content) {
        DisplayMetrics lDisplayMetrics = getResources().getDisplayMetrics();
        int widthPixels = lDisplayMetrics.widthPixels;
        int heightPixels = lDisplayMetrics.heightPixels;
        Integer qrCodeContentWidth = (int) Math.round(widthPixels * 1);

        return new QRGEncoder(content, null, QRGContents.Type.TEXT, qrCodeContentWidth);
    }

    private String serializeQREncoder(QRGEncoder qrgEncoder) {
        return this.gson.toJson(qrgEncoder);
    }

    private QRGEncoder deserialQREncoder(String qrEncoder) {
        return this.gson.fromJson(qrEncoder, QRGEncoder.class);
    }

    private void pageChange() {
        int position = viewPager2.getCurrentItem();

        itemUrl.setText(homeSliderItems.get(position).getUrl());
        itemName.setText(homeSliderItems.get(position).getName());

        String qrgEncoderJson = homeSliderItems.get(position).getQrImage();
        QRGEncoder qrgEncoder = this.gson.fromJson(qrgEncoderJson, QRGEncoder.class);
        qrImage.setImageBitmap(qrgEncoder.getBitmap());
    }

    private void copyDataToClipboard(){
        ClipboardManager _clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Nothing", itemUrl.getText());
        _clipboard.setPrimaryClip(clip);
        Toast.makeText(getActivity(), "Copy to clipboard", Toast.LENGTH_SHORT).show();
    }

    private void shareImage(Context context){
        int position = viewPager2.getCurrentItem();
        String qrgEncoderJson = homeSliderItems.get(position).getQrImage();
        QRGEncoder qrgEncoder = this.gson.fromJson(qrgEncoderJson, QRGEncoder.class);

        Uri uri = getImageUri(context, qrgEncoder.getBitmap());

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareIntent, "Select"));
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Image", null);
        return Uri.parse(path);
    }
}