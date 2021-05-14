package teneocto.thiemjason.tlu_connect.recycleview;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import teneocto.thiemjason.tlu_connect.R;

public class SocialRecVAdapter extends RecyclerView.Adapter<SocialRecVAdapter.viewHolder>{
    Context context;
    OnItemClickListener onItemClickListener;

    public SocialRecVAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_view_item, parent,false);
        return new viewHolder(view );
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }


    public class viewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        ImageView viewHolderDelete;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

//            viewHolderDelete = itemView.findViewById(R.id.list_item_delete);
            itemView.setOnClickListener(this);
            viewHolderDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == viewHolderDelete.getId()) {
                onItemClickListener.onDelete(v, getAdapterPosition());
            }
        }
    }


    public interface OnItemClickListener {
        void onItemClick(View view , int position);
        void onDelete(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
