package com.example.mvvmuserlist;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;


public class RecViewAdapter extends RecyclerView.Adapter<ViewHolder> {
    Integer id;
    private List<Data> list;
    Context context;
    FragmentManager fragmentManager;

    public RecViewAdapter(List<Data> list,Context context , FragmentManager fragmentManager){

        this.list = list;
        this.context = context;
        this.fragmentManager = fragmentManager;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position){

        id= list.get(position).getId();
        holder.first_name.setText(list.get(position).getFirstName());
        holder.last_name.setText(list.get(position).getLastName());
        holder.email.setText(list.get(position).getEmail());
        Glide.with(context).load(list.get(position).getAvatar()).into(holder.avatar);

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SingleUserFragment singleFragment = new SingleUserFragment();

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.framelayout,singleFragment).addToBackStack(null);
                transaction.commit();

                Bundle bundle = new Bundle();
                bundle.putString("FirstName", String.valueOf(list.get(position).getFirstName()));
                bundle.putString("LastName",String.valueOf(list.get(position).getLastName()));
                bundle.putString("Email",String.valueOf(list.get(position).getEmail()));
                bundle.putString("Avatar",String.valueOf(list.get(position).getAvatar()));

                singleFragment.setArguments(bundle);

                Log.d("ID",String.valueOf(id));
                Log.d("Name",list.get(position).getFirstName());
                Log.d("Family",list.get(position).getLastName());
                Log.d("Email",list.get(position).getEmail());
                Log.d("Image",list.get(position).getAvatar());

            }
        });
    }

    @Override
    public int getItemCount(){
        return list.size();
    }

}