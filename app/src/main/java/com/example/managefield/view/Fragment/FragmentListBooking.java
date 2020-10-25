package com.example.managefield.view.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.managefield.data.enumeration.DataState;
import com.example.managefield.data.enumeration.Result;
import com.example.managefield.databinding.FragmentListBookingBinding;
import com.example.managefield.view.Adapter.RecycleViewAdapterListBookingVertical;
import com.example.managefield.viewModel.ListBookingViewModel;
import com.example.managefield.viewModel.SessionStateData;

public class FragmentListBooking extends Fragment {
    private ListBookingViewModel viewModel ;
    private FragmentListBookingBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentListBookingBinding.inflate(inflater);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ListBookingViewModel.class);
        RecycleViewAdapterListBookingVertical adapter = viewModel.getAdapterListBooking();
        adapter.setFm(getParentFragmentManager());
        binding.recycleViewListBookingVertical.setAdapter(viewModel.getAdapterListBooking());
        binding.recycleViewListBookingVertical.setLayoutManager(new LinearLayoutManager(getContext()));
        observerLiveDate();
    }

    private void observerLiveDate() {
        SessionStateData.getInstance().getDatalistBooking().observe(getViewLifecycleOwner(), new Observer<DataState>() {
            @Override
            public void onChanged(DataState dataState) {
                viewModel.getListBooking();
            }
        });
    }



}
