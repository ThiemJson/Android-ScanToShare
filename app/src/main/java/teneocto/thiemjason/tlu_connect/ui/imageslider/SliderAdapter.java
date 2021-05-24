package teneocto.thiemjason.tlu_connect.ui.imageslider;

import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.ui.models.SliderItemDTO;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHoler> {
    private ArrayList<SliderItemDTO> sliderItemDTOS;

    public SliderAdapter(ArrayList<SliderItemDTO> sliderItemDTOS, ViewPager2 viewPager2) {
        this.sliderItemDTOS = sliderItemDTOS;
    }

    @NonNull
    @Override
    public SliderViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHoler(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slider_item_container,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHoler holder, int position) {
        holder.imageView.setImageResource(sliderItemDTOS.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return sliderItemDTOS.size();
    }

    class SliderViewHoler extends RecyclerView.ViewHolder {

        RoundedImageView imageView;

        public SliderViewHoler(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlider);
        }
    }
}
