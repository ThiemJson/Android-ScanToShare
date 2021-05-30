package teneocto.thiemjason.tlu_connect.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.regex.Pattern;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.models.SharedDTO;
import teneocto.thiemjason.tlu_connect.models.SocialNetworkDTO;
import teneocto.thiemjason.tlu_connect.utils.AppConst;
import teneocto.thiemjason.tlu_connect.utils.Utils;

public class RegisterAdapter extends RecyclerView.Adapter<RegisterAdapter.ViewHolder> {
    OnItemClickListener onItemClickListener;
    ArrayList<SharedDTO> sharedDTOArrays;
    Context context;

    public RegisterAdapter(Context context, ArrayList<SharedDTO> sharedDTOArrays) {
        this.context = context;
        this.sharedDTOArrays = sharedDTOArrays;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_view_item, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.editText.setText(sharedDTOArrays.get(position).getUrl());

        SocialNetworkDTO socialNetworkDTO = Utils.getSocialNWDTOFromId(sharedDTOArrays.get(position).getSocialNetWorkID());
        Bitmap imageBitmap = Utils.getBitmapFromByteArray(socialNetworkDTO.getImageBase64());
        holder.logo.setImageBitmap(imageBitmap);
    }

    @Override
    public int getItemCount() {
        return sharedDTOArrays.size();
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
        void onDelete(View view, int position);
    }

    public void setOnItemClickListener(final RegisterAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
