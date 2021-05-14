package teneocto.thiemjason.tlu_connect.recycleview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import teneocto.thiemjason.tlu_connect.R;

public class SocialRecycleView extends AppCompatActivity {
    RecyclerView recyclerView;
    SocialRecVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_recycle_view);

        recyclerView = findViewById(R.id.mRecycleView);
        adapter = new SocialRecVAdapter(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnItemClickListener(new SocialRecVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // nothing
            }

            @Override
            public void onDelete(View view, int position) {
                adapter.notifyItemRemoved(position);
            }
        });


        FloatingActionButton fab = findViewById(R.id.m_afb);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(SocialRecycleView.this, R.style.Theme_Design_BottomSheetDialog);
                View bottomSheetView = getLayoutInflater().from(SocialRecycleView.this).inflate(R.layout.action_sheet, findViewById(R.id.actionSheetContainer));
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

                View imageView = findViewById(R.id.list_item_logo);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(SocialRecycleView.this, "Nothing", Toast.LENGTH_LONG).show();
                        bottomSheetDialog.dismiss();
                    }
                });
            }
        });
    }
}