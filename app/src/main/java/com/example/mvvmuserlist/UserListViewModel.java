package com.example.mvvmuserlist;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserListViewModel extends ViewModel {
    private static final String TAG = "UserListViewModel";

    public UserListViewModel() {
        getData();
    }

    private MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();

    public MutableLiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    private MutableLiveData<Boolean> errorState = new MutableLiveData<>();

    public MutableLiveData<Boolean> getErrorLoading() {

        return errorState;
    }


    private MutableLiveData<JsonClass> userLiveData = new MutableLiveData<>();

    public MutableLiveData<JsonClass> getUserLiveData() {

        return userLiveData;
    }



    public void getData() {
        Log.d(TAG, "getData: ");
        loadingLiveData.setValue(true);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://reqres.in/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        final UserInterface api = retrofit.create(UserInterface.class);
        Call<JsonClass> call = api.getString();

        call.enqueue(new Callback<JsonClass>() {
            @Override
            public void onResponse(Call<JsonClass> call, Response<JsonClass> response) {
                loadingLiveData.setValue(false);
                errorState.setValue(false);

                if (response.isSuccessful()) {

                    if (response.body() != null) {
                        userLiveData.setValue(response.body());
                    } else {
                        errorState.setValue(true);
                    }
                }


            }

            @Override
            public void onFailure(Call<JsonClass> call, Throwable t) {
                loadingLiveData.setValue(false);
                errorState.setValue(true);
                t.printStackTrace();

            }
        });


    }


}
