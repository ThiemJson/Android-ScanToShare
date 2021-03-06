package teneocto.thiemjason.tlu_connect.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.ui.uimodels.UIMainSliderItemDTO;

/**
 * Main Slider adapter
 */
public class MainSliderAdapter extends RecyclerView.Adapter<MainSliderAdapter.ViewHolder> {
    public ArrayList<UIMainSliderItemDTO> UIMainSliderItemDTOS;
    public ViewPager2 mViewPager2;

    public MainSliderAdapter(ArrayList<UIMainSliderItemDTO> UIMainSliderItemDTOS, ViewPager2 viewPager2) {
        this.UIMainSliderItemDTOS = UIMainSliderItemDTOS;
        this.mViewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainSliderAdapter.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.main_slider_item,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.content.setText(UIMainSliderItemDTOS.get(position).getContent());
        holder.title.setText(UIMainSliderItemDTOS.get(position).getTitle());
        holder.setImage(UIMainSliderItemDTOS.get(position));

        if (position == UIMainSliderItemDTOS.size() - 2 ) {
            mViewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return UIMainSliderItemDTOS.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView imageView;
        TextView title;
        TextView content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_main_slider_item);
            title = itemView.findViewById(R.id.text_main_slider_title);
            content = itemView.findViewById(R.id.text_main_slider_content);
        }

        void setImage(UIMainSliderItemDTO UIMainSliderItemDTO) {
            imageView.setImageResource(UIMainSliderItemDTO.getImage());
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            UIMainSliderItemDTOS.addAll(UIMainSliderItemDTOS);
            notifyDataSetChanged();
        }
    };
}
