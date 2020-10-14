package com.example.managefield.Interface;


import com.example.managefield.model.Team;

import java.util.List;

public interface LoadListOtherTeamCallBack {
    void onSuccess(List<Team> listTeams);

    void onFailure(String message);
}
