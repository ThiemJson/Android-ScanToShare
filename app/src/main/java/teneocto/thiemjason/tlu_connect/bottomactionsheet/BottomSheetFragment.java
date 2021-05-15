package teneocto.thiemjason.tlu_connect.bottomactionsheet;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.adapter.BottomSheetAdapter;
import teneocto.thiemjason.tlu_connect.models.BottomSheetItem;
import teneocto.thiemjason.tlu_connect.register.RegisterSocialNetwork;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    Context context;
    ArrayList<BottomSheetItem> bottomSheetItems;
    OnItemClick listener;
    RecyclerView recyclerView;
    BottomSheetAdapter adapter;

    public BottomSheetFragment(Context context, OnItemClick listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet, null);
        bottomSheetDialog.setContentView(view);

        this.initDummyDataBottomSheet();

        recyclerView = view.findViewById(R.id.bottom_sheet_recycle_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new BottomSheetAdapter(context, bottomSheetItems);
        adapter.setOnItemClickListener((view1, position) -> {
            listener.onItemClick(view1,position);
        });
        recyclerView.setAdapter(adapter);

        return bottomSheetDialog;
    }

    /**
     * DUMMY Data for bottomSheetItem
     */
    private void initDummyDataBottomSheet() {
        this.bottomSheetItems = new ArrayList<BottomSheetItem>();

        this.bottomSheetItems.add(new BottomSheetItem(R.drawable.facebook, "Facebook"));
        this.bottomSheetItems.add(new BottomSheetItem(R.drawable.instagram, "Instagram"));
        this.bottomSheetItems.add(new BottomSheetItem(R.drawable.twiiter, "Twitter"));
        this.bottomSheetItems.add(new BottomSheetItem(R.drawable.sapchat, "Snapchat"));
        this.bottomSheetItems.add(new BottomSheetItem(R.drawable.linkedin, "LinkedIn"));
    }

    /**
     * Inteface handler when user click on list item
     */
    public interface OnItemClick {
        void onItemClick(View view , int position);
    }
}
