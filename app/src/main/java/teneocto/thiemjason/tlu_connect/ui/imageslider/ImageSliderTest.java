package teneocto.thiemjason.tlu_connect.ui.imageslider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.ui.models.SliderItemDTO;

public class ImageSliderTest extends AppCompatActivity {
    private ViewPager2 mViewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider_test);

        mViewPager2 = findViewById(R.id.viewPaperSlider);

        ArrayList<SliderItemDTO> sliderItemDTOS = new ArrayList<SliderItemDTO>();
        sliderItemDTOS.add(new SliderItemDTO(R.drawable.facebook));
        sliderItemDTOS.add(new SliderItemDTO(R.drawable.instagram));
        sliderItemDTOS.add(new SliderItemDTO(R.drawable.linkedin));
        sliderItemDTOS.add(new SliderItemDTO(R.drawable.sapchat));
        sliderItemDTOS.add(new SliderItemDTO(R.drawable.twiiter));

        mViewPager2.setAdapter(new SliderAdapter(sliderItemDTOS, mViewPager2));

        mViewPager2.setClipToPadding(false);
        mViewPager2.setClipChildren(false);
        mViewPager2.setOffscreenPageLimit(3);
        mViewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(80));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        mViewPager2.setPageTransformer(compositePageTransformer);
    }
}