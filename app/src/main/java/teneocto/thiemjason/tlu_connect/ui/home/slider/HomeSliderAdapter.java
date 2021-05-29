package teneocto.thiemjason.tlu_connect.ui.home.slider;

import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.makeramen.roundedimageview.RoundedImageView;

import java.math.BigInteger;
import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.models.SharedDTO;
import teneocto.thiemjason.tlu_connect.models.SocialNetworkDTO;
import teneocto.thiemjason.tlu_connect.ui.models.HomeSliderItemDTO;
import teneocto.thiemjason.tlu_connect.utils.Utils;

public class HomeSliderAdapter extends RecyclerView.Adapter<HomeSliderAdapter.ViewHolder> {
    public ArrayList<SharedDTO> arraysListSharedDTO;

    public HomeSliderAdapter(ArrayList<SharedDTO> arraysListSharedDTO, ViewPager2 viewPager2) {
        this.arraysListSharedDTO = arraysListSharedDTO;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SocialNetworkDTO socialNetworkDTO = Utils.getSocialNWDTOFromId(arraysListSharedDTO.get(position).getSocialNetWorkID());
        Bitmap imageBitmap = Utils.getBitmapFromByteArray(socialNetworkDTO.getImageBase64());
        holder.image.setImageBitmap(imageBitmap);
    }

    @Override
    public int getItemCount() {
        return arraysListSharedDTO.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.home_slider_item_image);
        }
    }
}
