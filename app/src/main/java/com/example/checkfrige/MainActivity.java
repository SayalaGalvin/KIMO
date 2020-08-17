package com.example.checkfrige;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_Button;
    CustomAdapter customAdapter;
    ImageView nodataImage;
    TextView nodata;

    DBHelper dbHelper;

    ArrayList<String> item_id, item_title, item_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycelview);
        add_Button = findViewById(R.id.add_Button);
        nodataImage = findViewById(R.id.imageView);
        nodata = findViewById(R.id.textView);

        add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        dbHelper = new DBHelper(MainActivity.this);
        item_id = new ArrayList<String>();
        item_title = new ArrayList<String>();
        item_amount = new ArrayList<String>();

        storeInArray();

        customAdapter = new CustomAdapter(MainActivity.this, this, item_id, item_title, item_amount);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }


    public void storeInArray(){
        Cursor cursor;
        cursor = dbHelper.getItem();
        if(cursor.getCount() == 0){
           nodata.setVisibility(View.VISIBLE);
           nodataImage.setVisibility(View.VISIBLE);
        }
        else{
            while (cursor.moveToNext()) {
                {
                    item_id.add(cursor.getString(0));
                    item_title.add(cursor.getString(1));
                    item_amount.add(cursor.getString(2));
                }
            }
            nodata.setVisibility(View.GONE);
            nodataImage.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater =  getMenuInflater();
        menuInflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            confirmDialog();
        }
        if(item.getItemId() == R.id.setTime){
            Intent intent = new Intent(MainActivity.this, setTime.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete");
        builder.setMessage("Do you want to delete all?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBHelper dbHelper = new DBHelper(MainActivity.this);
                dbHelper.deleteAll();
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
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
