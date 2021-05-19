package teneocto.thiemjason.tlu_connect.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.ui.models.BottomSheetItem;

public class BottomSheetAdapter extends RecyclerView.Adapter<BottomSheetAdapter.ViewHolder> {
    Context context;
    ArrayList<BottomSheetItem> mBottomSheetItems;
    OnItemClickListener onItemClickListener;

    /**
     * Constructor
     * @param context
     * @param bottomSheetItems
     */
    public BottomSheetAdapter(Context context, ArrayList<BottomSheetItem> bottomSheetItems){
        this.context = context;
        this.mBottomSheetItems = bottomSheetItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.action_sheet_item, parent, false);
        return new BottomSheetAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(this.mBottomSheetItems.get(position).getName());
        holder.imageView.setImageResource(this.mBottomSheetItems.get(position).getLogo());
    }

    @Override
    public int getItemCount() {
        return this.mBottomSheetItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.imageView = itemView.findViewById(R.id.action_item_logo);
            this.textView = itemView.findViewById(R.id.action_item_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    /**
     * Inteface handler when user click on list item
     */
    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    public void setOnItemClickListener(final BottomSheetAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
