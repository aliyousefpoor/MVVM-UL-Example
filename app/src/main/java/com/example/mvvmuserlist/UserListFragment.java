package com.example.mvvmuserlist;

import android.content.Context;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserListFragment extends Fragment {
    private static final String TAG = "UserListFragment";

    private UserListViewModel userListViewModel;
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
        userListViewModel = ViewModelProviders.of(this).get(UserListViewModel.class);

        swipRefreshLayout = view.findViewById(R.id.swiprefreshing);
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

    private void firstmethod() {
        pulldown.setVisibility(View.GONE);
        arrow.setVisibility(View.GONE);
        swipRefreshLayout.setRefreshing(true);
        userListViewModel.getLoadingLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loadingState) {
                if (loadingState) {
                    pulldown.setVisibility(View.GONE);
                    swipRefreshLayout.setRefreshing(true);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    pulldown.setVisibility(View.GONE);
                    swipRefreshLayout.setRefreshing(false);
                    recyclerView.setVisibility(View.VISIBLE);
                }

            }
        });

        userListViewModel.getErrorLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean hasError) {
                if (hasError) {
                    pulldown.setVisibility(View.VISIBLE);
                    swipRefreshLayout.setRefreshing(false);
                    arrow.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Check Your Conecction!", Toast.LENGTH_SHORT).show();
                } else {

                }

            }
        });

        userListViewModel.getUserLiveData().observe(this, new Observer<JsonClass>() {
            @Override
            public void onChanged(JsonClass jsonClass) {
                secondmethod(jsonClass);
            }
        });
    }


    public void secondmethod(JsonClass response) {
        List<Data> data = response.getData();
        RecViewAdapter adapter = new RecViewAdapter(data, getContext(), getFragmentManager());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

}
