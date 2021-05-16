package teneocto.thiemjason.tlu_connect.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.home.slider.HomeSliderAdapter;
import teneocto.thiemjason.tlu_connect.models.HomeSliderItem;
import teneocto.thiemjason.tlu_connect.models.MainSliderItem;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeQRImage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeQRImage extends Fragment {
    public ArrayList<HomeSliderItem> homeSliderItems;
    public ViewPager2 viewPager2;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_slider_contaiter, container, false);
        this.initSlider(view);
        return view;
    }

    private void initSlider(View view) {
        viewPager2 = view.findViewById(R.id.home_view_slider_container);

        homeSliderItems = new ArrayList<>();
        homeSliderItems.add(new HomeSliderItem(R.drawable.facebook, "Facebook", 12, "NguyenCaoThiem"));
        homeSliderItems.add(new HomeSliderItem(R.drawable.linkedin, "LinkedIn", 12, "NguyenCaoThiem"));
        homeSliderItems.add(new HomeSliderItem(R.drawable.sapchat, "Snapchat", 12, "NguyenCaoThiem"));
        homeSliderItems.add(new HomeSliderItem(R.drawable.twiiter, "Twitter", 12, "NguyenCaoThiem"));
        homeSliderItems.add(new HomeSliderItem(R.drawable.instagram, "Instagram", 12, "NguyenCaoThiem"));

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
        if (viewPager2.getCurrentItem() == 0 ){
            return;
        }
        viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
    }

    private void forwardButtonOnLick() {
        if (viewPager2.getCurrentItem() == homeSliderItems.size() ){
            return;
        }
        viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
    }
}