package com.example.checkfrige;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    EditText title_in, amount_in;
    Button add_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        title_in = findViewById(R.id.item);
        amount_in = findViewById(R.id.amount);
        add_in = findViewById(R.id.add_button);

        add_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(AddActivity.this);
                dbHelper.addItem(title_in.getText().toString().trim(),amount_in.getText().toString().trim());
            }
        });
    }
}
