package com.example.checkfrige;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    EditText id_update, title_update, amount_update;
    Button btn_update,btn_delete;

    String id, title, amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        id_update = findViewById(R.id.updateid);
        title_update = findViewById(R.id.updateitem);
        amount_update = findViewById(R.id.updateamount);
        btn_update = findViewById(R.id.update_button);
        btn_delete = findViewById(R.id.delete_button);
        getAndSetIntentData();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(title_update.getText().toString());
        }

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(UpdateActivity.this);
                dbHelper.updateItem(id_update.getText().toString().trim(), title_update.getText().toString().trim(), amount_update.getText().toString().trim());
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") && getIntent().hasExtra("amount")){
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            amount = getIntent().getStringExtra("amount");

            id_update.setText(id);
            title_update.setText(title);
            amount_update.setText(amount);
        }else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete "+ title+ " ?");
        builder.setMessage("Do you want to delete this?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBHelper dbHelper = new DBHelper(UpdateActivity.this);
                dbHelper.deleteOneRow(id_update.getText().toString());
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();


    }
}
