package teneocto.thiemjason.tlu_connect.ui.bottomactionsheet;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import teneocto.thiemjason.tlu_connect.ui.models.SocialNetworkDTO;

public class BottomSheetAdapter extends RecyclerView.Adapter<BottomSheetAdapter.ViewHolder> {
    SocialNetworkDTO socialNetworkDTO[];
    Context context;

    /**
     * Constructor
     * @param context context
     * @param socialNetworkDTOS data list
     */
    public  BottomSheetAdapter(Context context, SocialNetworkDTO socialNetworkDTOS[]){
        this.context = context;
        this.socialNetworkDTO = socialNetworkDTOS;
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return this.socialNetworkDTO.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
