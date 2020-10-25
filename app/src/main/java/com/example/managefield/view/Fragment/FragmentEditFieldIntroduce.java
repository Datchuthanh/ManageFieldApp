package com.example.managefield.view.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.example.managefield.data.enumeration.Result;
import com.example.managefield.databinding.FragmentEditPlayerIntroduceBinding;
import com.example.managefield.databinding.LoadingLayoutBinding;
import com.example.managefield.model.Field;
import com.example.managefield.viewModel.SessionField;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentEditFieldIntroduce extends BottomSheetDialogFragment {

     private FragmentEditPlayerIntroduceBinding binding;
    private SessionField session = SessionField.getInstance();
    private Dialog loadingDialog;
    private LoadingLayoutBinding loadingLayoutBinding;
    private  Map<String, Object> data = new HashMap<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditPlayerIntroduceBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent(view.getContext());
        observeLiveData(view.getContext());
    }

    private void observeLiveData(final Context context) {
        session.getResultLiveData().observe(getViewLifecycleOwner(), new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result == null) return;
                if (result == Result.SUCCESS) {
                    session.resetResult();
                    loadingDialog.dismiss();
                    Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                    updateUIPlayer();
                  detach();
                } else if (result == Result.FAILURE) {
                    session.resetResult();
                    loadingDialog.dismiss();
                    detach();
                    Toast.makeText(context, session.getResultMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private  void initComponent(final Context context){
        binding.imageBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initLoadingDialog(context);
                session.resetResult();
                loadingDialog.show();
                session.updateProfile(getUpdateIntroduction());
            }
        });
    }

    private void detach(){
       dismiss();
    }

    private void initLoadingDialog(Context context) {
        loadingDialog = new Dialog(context);
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingLayoutBinding = LoadingLayoutBinding.inflate(getLayoutInflater());
        loadingDialog.setContentView(loadingLayoutBinding.getRoot());
//        loadingLayoutBinding.title.setText(R.string.updating_information);
        loadingDialog.setCancelable(false);
    }

    private Map<String, Object> getUpdateIntroduction() {
        data.put("introduce", binding.txtIntroduce.getText().toString());
        return data;
    }

    private  void updateUIPlayer (){
        Field field = SessionField.getInstance().getPlayerLiveData().getValue();
        field.setIntroduce(binding.txtIntroduce.getText().toString());
        SessionField.getInstance().setPlayerLiveData(field);
    }

}
