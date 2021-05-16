package teneocto.thiemjason.tlu_connect.home.slider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.main.MainSliderAdapter;
import teneocto.thiemjason.tlu_connect.models.HomeSliderItem;
import teneocto.thiemjason.tlu_connect.models.MainSliderItem;

public class HomeSliderAdapter extends RecyclerView.Adapter<HomeSliderAdapter.ViewHolder> {
    public ArrayList<HomeSliderItem> homeSliderItems;

    public HomeSliderAdapter(ArrayList<HomeSliderItem> homeSliderItems, ViewPager2 viewPager2) {
        this.homeSliderItems = homeSliderItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeSliderAdapter.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.home_slider_item,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.image.setImageResource(homeSliderItems.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return homeSliderItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.home_slider_item_image);
        }
    }
}
