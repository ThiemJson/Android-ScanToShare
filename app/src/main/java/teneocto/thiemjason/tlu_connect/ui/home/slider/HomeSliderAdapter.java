package teneocto.thiemjason.tlu_connect.ui.home.slider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.ui.models.HomeSliderItemDTO;

public class HomeSliderAdapter extends RecyclerView.Adapter<HomeSliderAdapter.ViewHolder> {
    public ArrayList<HomeSliderItemDTO> homeSliderItemDTOS;

    public HomeSliderAdapter(ArrayList<HomeSliderItemDTO> homeSliderItemDTOS, ViewPager2 viewPager2) {
        this.homeSliderItemDTOS = homeSliderItemDTOS;
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
        holder.image.setImageResource(homeSliderItemDTOS.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return homeSliderItemDTOS.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.home_slider_item_image);
        }
    }
}
