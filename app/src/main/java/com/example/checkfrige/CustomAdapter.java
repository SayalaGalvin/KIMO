package com.example.checkfrige;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyView> {

    private Context context;
    Activity activity;
    private ArrayList list_id, list_title, list_amount;

    Animation translate_anim;

    CustomAdapter(Activity activity,Context context, ArrayList list_id, ArrayList list_title, ArrayList list_amount){
        this.activity = activity;
        this.context = context;
        this.list_id = list_id;
        this.list_title = list_title;
        this.list_amount = list_amount;
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_row, viewGroup, false);
        return new MyView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView myView, final int i) {
        myView.item_ID.setText(String.valueOf(list_id.get(i)));
        myView.item_Title.setText(String.valueOf(list_title.get(i)));
        myView.item_Amount.setText(String.valueOf(list_amount.get(i)));
        myView.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(list_id.get(i)));
                intent.putExtra("title", String.valueOf(list_title.get(i)));
                intent.putExtra("amount", String.valueOf(list_amount.get(i)));
                activity.startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_id.size();
    }

    public class MyView extends RecyclerView.ViewHolder{
        TextView item_ID, item_Title, item_Amount;
        LinearLayout mainLayout;
        public MyView(@NonNull View itemView) {
            super(itemView);
            item_ID = itemView.findViewById(R.id.itemID);
            item_Title = itemView.findViewById(R.id.itemTitle);
            item_Amount = itemView.findViewById(R.id.itemAmount);
            mainLayout = itemView.findViewById(R.id.myLayout);

            translate_anim = AnimationUtils.loadAnimation(context,R.anim.trans_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}
