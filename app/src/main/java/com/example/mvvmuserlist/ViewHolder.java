package com.example.mvvmuserlist;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    CardView cv;
    ImageView avatar;
    TextView first_name;
    TextView last_name;
    TextView email;
    Integer id;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        cv =(CardView) itemView.findViewById(R.id.cardview);
        avatar = (ImageView) itemView.findViewById(R.id.avatar);
        first_name = (TextView) itemView.findViewById(R.id.first_name);
        last_name = (TextView) itemView.findViewById(R.id.last_name);
        email = (TextView)itemView.findViewById(R.id.email);
    }
}
