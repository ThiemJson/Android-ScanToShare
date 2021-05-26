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
import teneocto.thiemjason.tlu_connect.ui.models.NotificationDTO;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    NotificationAdapter.OnItemClickListener onItemClickListener;
    ArrayList<NotificationDTO> arrayList;
    Context context;

    public NotificationAdapter(ArrayList<NotificationDTO> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.notification_list_item, parent, false);
        return new NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mImage.setImageResource(arrayList.get(position).getImage());
        holder.mContent.setText(arrayList.get(position).getContent());
        holder.mTitle.setText(arrayList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public interface OnItemClickListener {
        void onView(View view, int position);
    }

    public void setOnItemClickListener(final NotificationAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mImage;
        TextView mTitle;
        TextView mContent;

        ImageView mView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mImage = itemView.findViewById(R.id.notification_image);
            mTitle = itemView.findViewById(R.id.notification_title);
            mContent = itemView.findViewById(R.id.notification_content);
            mView = itemView.findViewById(R.id.notification_view);

            mView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == mView.getId()) {
                return;
            }
        }
    }
}