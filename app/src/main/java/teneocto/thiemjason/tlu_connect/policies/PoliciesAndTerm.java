package teneocto.thiemjason.tlu_connect.policies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import teneocto.thiemjason.tlu_connect.R;

public class PoliciesAndTerm extends AppCompatActivity {
    Button mBackbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policies_and_term);

        this.initView();
    }

    void initView() {
        mBackbutton = findViewById(R.id.policies_menu_icon);
        mBackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}