package teneocto.thiemjason.tlu_connect.ui.policies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import teneocto.thiemjason.tlu_connect.R;

public class PoliciesAndTerm extends AppCompatActivity {
    Button mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policies_and_term);

        this.initView();
    }

    void initView() {
        mBackButton = findViewById(R.id.policies_menu_icon);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}