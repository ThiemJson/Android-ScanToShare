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
import teneocto.thiemjason.tlu_connect.ui.models.UserDTO;

public class ScanHisAdapter extends RecyclerView.Adapter<ScanHisAdapter.ViewHolder> {
    ScanHisAdapter.OnItemClickListener onItemClickListener;
    private ArrayList<UserDTO> mArrayList;
    private Context context;

    public ScanHisAdapter(ArrayList<UserDTO> mArrayList, Context context) {
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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mEmail.setText(mArrayList.get(position).getEmail());
        holder.mUsername.setText(mArrayList.get(position).getName());
        holder.mUserImage.setImageResource(mArrayList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mUsername;
        TextView mEmail;
        ImageView mUserImage;

        ImageView mDelete;
        ImageView mView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mUserImage = itemView.findViewById(R.id.scanning_his_image);
            mUsername = itemView.findViewById(R.id.scanning_his_username);
            mEmail = itemView.findViewById(R.id.scanning_his_email);
            mDelete = itemView.findViewById(R.id.scanning_his_delete_btn);
            mView = itemView.findViewById(R.id.scanning_his_view_btn);

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
     * Inteface handler when user click on list item
     */
    public interface OnItemClickListener {
        void onDelete(View view, int position);

        void onView(View view, int position);
    }

    public void setOnItemClickListener(final ScanHisAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
