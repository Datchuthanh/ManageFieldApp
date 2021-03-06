package com.example.managefield.view.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;


import com.example.managefield.data.enumeration.Result;
import com.example.managefield.databinding.FragmentEditPlayerBasicBinding;

import com.example.managefield.model.Field;
import com.example.managefield.Session.SessionField;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentEditFieldBasic extends BottomSheetDialogFragment {
     private  FragmentEditPlayerBasicBinding binding;
    private SessionField session = SessionField.getInstance();
    private    Map<String, Object> data = new HashMap<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditPlayerBasicBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent(view.getContext());
        observeLiveData(view.getContext());

    }



    private void detach(){
       dismiss();
    }



    private  void initComponent(final Context context){
        binding.imageBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.updateProfile(getUpdateBasic());
            }
        });
    }


    private void observeLiveData(final Context context) {
        session.getResultLiveData().observe(getViewLifecycleOwner(), new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result == null) return;
                if (result == Result.SUCCESS) {
                    session.resetResult();
                    detach();
                    Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                    updateUIPlayer();

                } else if (result == Result.FAILURE) {
                    session.resetResult();
                    detach();
                    Toast.makeText(context, session.getResultMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private Map<String, Object> getUpdateBasic() {
        data.put("email", binding.txtEmail.getText().toString());
        data.put("phone", binding.txtPhone.getText().toString());
        data.put("name", binding.txtName.getText().toString());
        data.put("address", binding.txtAddress.getText().toString());
        return data;
    }

    private  void updateUIPlayer (){
        Field field = SessionField.getInstance().getPlayerLiveData().getValue();
        field.setInforBasic(data);
        SessionField.getInstance().setPlayerLiveData(field);
    }


}
