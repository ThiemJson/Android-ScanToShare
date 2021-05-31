package teneocto.thiemjason.tlu_connect.ui.imageslider;

import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.ui.uimodels.UISliderItemDTO;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHoler> {
    private ArrayList<UISliderItemDTO> UISliderItemDTOS;

    public SliderAdapter(ArrayList<UISliderItemDTO> UISliderItemDTOS, ViewPager2 viewPager2) {
        this.UISliderItemDTOS = UISliderItemDTOS;
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
        holder.imageView.setImageResource(UISliderItemDTOS.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return UISliderItemDTOS.size();
    }

    class SliderViewHoler extends RecyclerView.ViewHolder {

        RoundedImageView imageView;

        public SliderViewHoler(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlider);
        }
    }
}
