package teneocto.thiemjason.tlu_connect.bottomactionsheet;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import teneocto.thiemjason.tlu_connect.R;
import teneocto.thiemjason.tlu_connect.adapter.BottomSheetAdapter;
import teneocto.thiemjason.tlu_connect.models.BottomSheetItem;
import teneocto.thiemjason.tlu_connect.register.RegisterSocialNetwork;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    // Bottom sheet list view
    BottomSheetAdapter bottomSheetAdapter;
    RecyclerView bottomRecycleView;
    ArrayList<BottomSheetItem> bottomSheetItems;

    public static BottomSheetFragment newInstance() {
        BottomSheetFragment fragment = new BottomSheetFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.bottom_sheet, null);
        dialog.setContentView(contentView);
        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    private void initRecycleView() {
        this.bottomRecycleView = findViewById(R.id.bottom_sheet_recycle_view);
        this.bottomSheetAdapter = new BottomSheetAdapter(RegisterSocialNetwork.this, this.bottomSheetItems);
        this.bottomRecycleView.setAdapter(this.bottomSheetAdapter);
        this.bottomRecycleView.setLayoutManager(new LinearLayoutManager(RegisterSocialNetwork.this));
        this.bottomSheetAdapter.setOnItemClickListener(new BottomSheetAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                bottomItemClick(bottomSheetItems.get(position));
            }
        });
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
}
