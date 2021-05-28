package teneocto.thiemjason.tlu_connect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import teneocto.thiemjason.tlu_connect.database.SubDBHelper;

public class TestDB extends AppCompatActivity {
    SubDBHelper dbHelper;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_d_b);

        Button btn_insert = findViewById(R.id.db_insert);
        Button btn_Show = findViewById(R.id.db_show);
        imageView = findViewById(R.id.db_image);

        dbHelper = new SubDBHelper(this);

        btn_insert.setOnClickListener(v -> dbHelper.insertData("Nguyen Cao Thiem", 21));
//        btn_insert.setOnClickListener(v -> dbHelper.dropDB());
        btn_Show.setOnClickListener(v -> dbHelper.printData(imageView));
    }
}