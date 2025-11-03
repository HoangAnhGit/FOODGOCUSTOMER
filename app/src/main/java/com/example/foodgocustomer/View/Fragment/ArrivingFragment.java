package com.example.foodgocustomer.View.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodgocustomer.R;
import com.example.foodgocustomer.databinding.FragmentArrivingBinding;

import java.util.ArrayList;
import java.util.List;


public class ArrivingFragment extends Fragment {


    private FragmentArrivingBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentArrivingBinding.inflate(inflater, container, false);



        return binding.getRoot();
    }
}