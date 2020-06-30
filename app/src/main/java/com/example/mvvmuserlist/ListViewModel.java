package com.example.mvvmuserlist;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Observable;

public class ListViewModel extends ViewModel {

    private RecyclerView recyclerView;


    private MutableLiveData<Data> dataMutableLiveData;

    public MutableLiveData<Data> getDataMutableLiveData() {
        if (dataMutableLiveData == null) {
            dataMutableLiveData = new MutableLiveData<Data>();
        }
        return dataMutableLiveData;
    }

}
