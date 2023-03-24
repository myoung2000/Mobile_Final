package com.example.finalproject;

import android.content.Context;
import android.util.Log;

import com.example.finalproject.models.Players;

import java.util.ArrayList;
import java.util.Date;

public class PlayersDataAccess {

    private Context context;

    public PlayersDataAccess(Context c){
        this.context = c;
    }

    public static final String TAG = "PlayersDataAccess";


    private static ArrayList<Players> allPlayers = new ArrayList<Players>(){{
        add(new Players(1, "Devin", "McCoy", true));
        add(new Players(2, "Noah", "Hanson", false));
        add(new Players(3, "Eli", "Aasen", false));
    }};

    public Players insertPlayer(Players p) throws Exception {
        Log.d(TAG, "Attempting");
        if(p.isValid()) {
            Log.d(TAG, "Inserting Player");
            allPlayers.add(p);
            p.setId(getMaxId() + 1);
            return p;
        }else{
            throw new Exception("INSERT FAILED INVALID PLAYER");
        }
    }


    private long getMaxId(){
        long maxId = 0;
        for(Players p : allPlayers){
            long pId = p.getId();
            maxId = pId > maxId ? pId : maxId;
        }
        return maxId;
    }

    public ArrayList<Players> getAllPlayers(){
        ArrayList<Players>copyList = new ArrayList();
        for(Players p : allPlayers){
            Players copy = new Players(p.getId(), p.getFirstName(), p.getLastName(), p.isActive());
            copyList.add(copy);
        }
        return copyList;
    }

    public Players getPlayerById(long id){
        for(Players p : allPlayers){
            if(p.getId() == id){
                return new Players(p.getId(), p.getFirstName(), p.getLastName(), p.isActive());
            }
        }
        return null;
    }

    public Players updatePlayer(Players p) throws Exception {
        if(p.isValid()) {
            for (Players currentPlayer : allPlayers) {
                if (currentPlayer.getId() == p.getId()) {
                    currentPlayer.setFirstName((p.getFirstName()));
                    currentPlayer.setLastName(p.getLastName());
                    currentPlayer.setActive(p.isActive());
                    break;
                }
            }
            return p;
        }else{
            throw new Exception("UPDATE FAILED, INVALID PLAYER");
        }
    }

    public int deletePlayer(Players p){
        for(Players currentPlayers : allPlayers){
            if(currentPlayers.getId() == p.getId()){
                allPlayers.remove(currentPlayers);
                return 1;
            }
        }
        return 0;
    }
}
