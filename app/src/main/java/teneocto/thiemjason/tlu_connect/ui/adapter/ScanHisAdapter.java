package teneocto.thiemjason.tlu_connect.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.models.ScannedDTO;
import teneocto.thiemjason.tlu_connect.models.UserDTO;
import teneocto.thiemjason.tlu_connect.utils.Utils;

/**
 * Scanning History Adapter
 */
public class ScanHisAdapter extends RecyclerView.Adapter<ScanHisAdapter.ViewHolder> {

    ScanHisAdapter.OnItemClickListener onItemClickListener;

    private ArrayList<ScannedDTO> mArrayList;

    private Context context;

    public ScanHisAdapter(ArrayList<ScannedDTO> mArrayList, Context context) {
        this.mArrayList = mArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.scanning_history_item, parent, false);
        return new ScanHisAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mUrl.setText(mArrayList.get(position).getUrl());
        holder.mUsername.setText(String.format("%s", mArrayList.get(position).getName()));

        Bitmap socialImageBitmap = Utils.getBitmapFromByteArray(Utils.getSocialNWDTOFromId(mArrayList.get(position).getSocialNetWorkID()).getImageBase64());
        holder.mUserSocial.setImageBitmap(socialImageBitmap);

        Bitmap imageBitmap = Utils.getBitmapFromByteArray(mArrayList.get(position).getImageBase64());
        holder.mUserImage.setImageBitmap(Utils.prettyBitmap(imageBitmap));
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mUsername;
        TextView mUrl;
        ImageView mUserImage;
        ImageView mUserSocial;

        ImageView mDelete;
        ImageView mView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mUserImage = itemView.findViewById(R.id.scanning_his_image);
            mUsername = itemView.findViewById(R.id.scanning_his_username);
            mUrl = itemView.findViewById(R.id.scanning_his_email);
            mDelete = itemView.findViewById(R.id.scanning_his_delete_btn);
            mView = itemView.findViewById(R.id.scanning_his_view_btn);
            mUserSocial = itemView.findViewById(R.id.scanning_his_social);

            mDelete.setOnClickListener(this);
            mView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == mDelete.getId()) {
                onItemClickListener.onDelete(v, getAdapterPosition());
            }

            if (v.getId() == mView.getId()) {
                onItemClickListener.onView(v, getAdapterPosition());
            }
        }
    }

    /**
     * Interface handler when user click on list item
     */
    public interface OnItemClickListener {
        void onDelete(View view, int position);

        void onView(View view, int position);
    }

    public void setOnItemClickListener(final ScanHisAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
