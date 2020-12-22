package com.example.managefield.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.managefield.Interface.CallBack;
import com.example.managefield.Session.SessionField;
import com.example.managefield.data.enumeration.LoadingState;
import com.example.managefield.data.enumeration.Result;
import com.example.managefield.data.enumeration.Status;
import com.example.managefield.data.repository.MatchRepository;
import com.example.managefield.model.Booking;
import com.example.managefield.model.Match;
import com.example.managefield.view.Adapter.RecycleViewAdapterListBookingVertical;
import com.example.managefield.view.Adapter.RecycleViewAdapterListMatchVertical;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListMatchViewModel extends ViewModel{
    private MatchRepository matchRepository = MatchRepository.getInstance();
    private RecycleViewAdapterListMatchVertical adapterListMatchVertical = new RecycleViewAdapterListMatchVertical();
    private MutableLiveData<List<Match>> listBookingFieldLiveData = new MutableLiveData<>();
    private MutableLiveData<Result> resultUpdateScore = new MutableLiveData<>();
    private MutableLiveData<LoadingState> teamLoadState = new MutableLiveData<>(LoadingState.INIT);
    private MutableLiveData<LoadingState> executeState = new MutableLiveData<>();
    private MutableLiveData<Status> statusData = new MutableLiveData<>(Status.NO_DATA);

    public ListMatchViewModel(){
        getListMatch();
    }

    public void  getListMatch(){
        teamLoadState.setValue(LoadingState.INIT);
        matchRepository.getListMatch(SessionField.getInstance().getFiledLiveData().getValue().getId(),new CallBack<List<Match>, String>() {
            @Override
            public void onSuccess(List<Match> matchList) {
                teamLoadState.setValue(LoadingState.LOADED);
                if(matchList == null){
                    statusData.setValue(Status.NO_DATA);
                    listBookingFieldLiveData.setValue(new ArrayList<Match>());
                    adapterListMatchVertical.setListMatch(new ArrayList<Match>());
                    adapterListMatchVertical.notifyDataSetChanged();
                }else{
                    statusData.setValue(Status.EXIST_DATA);
                    listBookingFieldLiveData.setValue(matchList);
                    adapterListMatchVertical.setListMatch(matchList);
                    adapterListMatchVertical.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(String message) {

            }
        });
    }


    public RecycleViewAdapterListMatchVertical getAdapterListMatch() {
        return adapterListMatchVertical;
    }

    public void updateScoreMatch(Map<String,Object> map){
        executeState.setValue(LoadingState.INIT);
        matchRepository.updateScoreMatch(map, new CallBack<String, String>() {

            @Override
            public void onSuccess(String s) {
                executeState.setValue(LoadingState.LOADED);
                resultUpdateScore.setValue(Result.SUCCESS);
            }

            @Override
            public void onFailure(String s) {
                resultUpdateScore.setValue(Result.FAILURE);
            }
        });
    }

    public MutableLiveData<Result> getResultUpdateScore() {
        return resultUpdateScore;
    }

    public MutableLiveData<LoadingState> getTeamLoadState() {
        return teamLoadState;
    }

    public void setTeamLoadState(MutableLiveData<LoadingState> teamLoadState) {
        this.teamLoadState = teamLoadState;
    }

    public MutableLiveData<LoadingState> getExecuteState() {
        return executeState;
    }

    public void setExecuteState(MutableLiveData<LoadingState> executeState) {
        this.executeState = executeState;
    }

    public MutableLiveData<Status> getStatusData() {
        return statusData;
    }

    public void setStatusData(MutableLiveData<Status> statusData) {
        this.statusData = statusData;
    }
}
