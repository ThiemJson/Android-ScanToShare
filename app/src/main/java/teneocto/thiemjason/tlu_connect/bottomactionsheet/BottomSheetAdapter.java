package teneocto.thiemjason.tlu_connect.bottomactionsheet;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import teneocto.thiemjason.tlu_connect.models.SocialNetwork;

public class BottomSheetAdapter extends RecyclerView.Adapter<BottomSheetAdapter.ViewHolder> {
    SocialNetwork socialNetwork[];
    Context context;

    /**
     * Constructor
     * @param context context
     * @param socialNetworks data list
     */
    public  BottomSheetAdapter(Context context, SocialNetwork socialNetworks[]){
        this.context = context;
        this.socialNetwork = socialNetworks;
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
        return this.socialNetwork.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
