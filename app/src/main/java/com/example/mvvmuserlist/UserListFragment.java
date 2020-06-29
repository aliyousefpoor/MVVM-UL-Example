package com.example.mvvmuserlist;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserListFragment extends Fragment {
    private static final String TAG = "UserListFragment";

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipRefreshLayout;
    private TextView pulldown;
    private ImageView arrow;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userlist_layout, container, false);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.swiprefreshing);
        pulldown = view.findViewById(R.id.pulldown);
        recyclerView = view.findViewById(R.id.rcview);
        arrow = view.findViewById(R.id.arrow);

        pulldown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstmethod();
            }
        });

        swipRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                firstmethod();
            }
        });

        firstmethod();

    }
    private void firstmethod(){
        pulldown.setVisibility(View.GONE);
        arrow.setVisibility(View.GONE);
        swipRefreshLayout.setRefreshing(true);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://reqres.in/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        final UserInterface api = retrofit.create(UserInterface.class);
        Call<JsonClass> call = api.getString();

        call.enqueue(new Callback<JsonClass>() {
            @Override
            public void onResponse(Call<JsonClass> call, Response<JsonClass> response) {
                pulldown.setVisibility(View.GONE);
                swipRefreshLayout.setRefreshing(false);
                recyclerView.setVisibility(View.VISIBLE);
                Log.d(TAG, "onResponse: " +response.body().toString());

                if (response.isSuccessful()){

                    if (response.body() !=null){
                        secondmethod(response.body());
                    }

                    else {
                        Log.d(TAG, "onResponse : Return Empty Response");
                    }
                }


            }

            @Override
            public void onFailure(Call<JsonClass> call, Throwable t) {
                pulldown.setVisibility(View.VISIBLE);
                swipRefreshLayout.setRefreshing(false);
                arrow.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);

                Toast.makeText(getContext(),"Check Your Conecction!",Toast.LENGTH_SHORT).show();

                t.printStackTrace();

            }
        });


    }


    public void secondmethod(JsonClass response){
        List<Data> data = response.getData();
        RecViewAdapter adapter = new RecViewAdapter(data,getContext(),getFragmentManager());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
