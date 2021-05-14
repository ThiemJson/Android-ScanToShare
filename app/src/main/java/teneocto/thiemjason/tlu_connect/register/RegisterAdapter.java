package teneocto.thiemjason.tlu_connect.register;

import android.content.Context;
import android.service.autofill.TextValueSanitizer;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.models.SocialNetwork;
import teneocto.thiemjason.tlu_connect.recycleview.SocialRecVAdapter;

public class RegisterAdapter extends RecyclerView.Adapter<RegisterAdapter.ViewHolder> {
    OnItemClickListener onItemClickListener;
    ArrayList<SocialNetwork> socialNetworks;
    Context context;

    public RegisterAdapter(Context context, ArrayList<SocialNetwork> socialNetworks) {
        this.context = context;
        this.socialNetworks = socialNetworks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.editText.setText(socialNetworks.get(position).getLink());
        holder.logo.setImageResource(socialNetworks.get(position).getLogo());
    }

    @Override
    public int getItemCount() {
        Log.i("==>COUNT", "" + socialNetworks.size());
        return socialNetworks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView logo;
        ImageView delete;
        EditText editText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.logo = itemView.findViewById(R.id.image_list_item_logo);
            this.delete = itemView.findViewById(R.id.image_list_item_delete);
            this.editText = itemView.findViewById(R.id.edit_list_item_url);

            itemView.setOnClickListener(this);
            this.delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // When user clicked on delete button
            if (v.getId() == this.delete.getId()) {
                onItemClickListener.onDelete(v, getAdapterPosition());
                return;
            }
        }
    }

    /**
     * Inteface handler when user click on list item
     */
    public interface OnItemClickListener {
//        void onItemClick(View view , int position);
        void onDelete(View view, int position);
    }

    public void setOnItemClickListener(final RegisterAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
