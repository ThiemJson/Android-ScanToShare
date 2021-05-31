package teneocto.thiemjason.tlu_connect.ui.bottomactionsheet;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import teneocto.thiemjason.tlu_connect.ui.uimodels.UISocialNetworkDTO;

public class BottomSheetAdapter extends RecyclerView.Adapter<BottomSheetAdapter.ViewHolder> {
    UISocialNetworkDTO UISocialNetworkDTO[];
    Context context;

    /**
     * Constructor
     * @param context context
     * @param UISocialNetworkDTOS data list
     */
    public  BottomSheetAdapter(Context context, UISocialNetworkDTO UISocialNetworkDTOS[]){
        this.context = context;
        this.UISocialNetworkDTO = UISocialNetworkDTOS;
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
        return this.UISocialNetworkDTO.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
