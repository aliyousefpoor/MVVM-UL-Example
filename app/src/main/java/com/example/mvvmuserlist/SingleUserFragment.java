package com.example.mvvmuserlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;

import java.io.Serializable;

public class SingleUserFragment extends Fragment {
    TextView Name;
    TextView Family;
    TextView Email;
    ImageView Avatar;

    public static SingleUserFragment newInstance() {

        SingleUserFragment fragment = new SingleUserFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.single_row, container, false);


        Data data = getArguments().getParcelable("Data_key");


        Name = view.findViewById(R.id.name);
        Family = view.findViewById(R.id.family);
        Email = view.findViewById(R.id.emaill);
        Avatar = view.findViewById(R.id.avatarr);
        Name.setText(data.getFirstName());
        Family.setText(data.getLastName());
        Email.setText(data.getEmail());
        Glide.with(this).load(data.getAvatar()).into(Avatar);

        Toast.makeText(getContext(), "Transaction Succesfull", Toast.LENGTH_LONG).show();


        return view;

    }

}
