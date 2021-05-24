package teneocto.thiemjason.tlu_connect.ui.bottomactionsheet;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.ui.adapter.BottomSheetAdapter;
import teneocto.thiemjason.tlu_connect.ui.models.BottomSheetItemDTO;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    Context context;
    ArrayList<BottomSheetItemDTO> bottomSheetItemDTOS;
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

        adapter = new BottomSheetAdapter(context, bottomSheetItemDTOS);
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
        this.bottomSheetItemDTOS = new ArrayList<BottomSheetItemDTO>();

        this.bottomSheetItemDTOS.add(new BottomSheetItemDTO(R.drawable.facebook, "Facebook"));
        this.bottomSheetItemDTOS.add(new BottomSheetItemDTO(R.drawable.instagram, "Instagram"));
        this.bottomSheetItemDTOS.add(new BottomSheetItemDTO(R.drawable.twiiter, "Twitter"));
        this.bottomSheetItemDTOS.add(new BottomSheetItemDTO(R.drawable.sapchat, "Snapchat"));
        this.bottomSheetItemDTOS.add(new BottomSheetItemDTO(R.drawable.linkedin, "LinkedIn"));
    }

    /**
     * Inteface handler when user click on list item
     */
    public interface OnItemClick {
        void onItemClick(View view , int position);
    }
}
