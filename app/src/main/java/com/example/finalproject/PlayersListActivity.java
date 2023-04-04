package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.finalproject.models.Characters;
import com.example.finalproject.models.Players;

import java.util.ArrayList;

public class PlayersListActivity extends AppCompatActivity {

    public static final String TAG = "PlayersListActivity";

    ListView lsPlayers;
    Button btnAddPlayer;
    Button btnBack;
    private PlayersDataAccess da;
    private ArrayList<Players> allPlayers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_list);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlayersListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnAddPlayer = findViewById(R.id.btnAddPlayer);

        btnAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlayersListActivity.this, PlayersDetailsActivity.class);
                startActivity(intent);
            }
        });


        lsPlayers = findViewById((R.id.lsPlayers));
        da = new PlayersDataAccess(this);
        //da = new CSVTaskDataAccess(this);
        allPlayers = da.getAllPlayers();



        cusAdap();



    }
    public void cusAdap () {
        ArrayAdapter<Players> adapter = new ArrayAdapter<Players>(this, R.layout.custom_player_list, R.id.lblFirstName, allPlayers) {
            @Override
            public View getView(int position, View convertView, ViewGroup parentListView) {
                View listItemView = super.getView(position, convertView, parentListView);
                TextView lblFirstName = listItemView.findViewById(R.id.lblFirstName);
                TextView lblLastName = listItemView.findViewById(R.id.lblLastName);


                Players currentPlayer = allPlayers.get(position);
                lblFirstName.setText(currentPlayer.getFirstName());
                lblLastName.setText(currentPlayer.getLastName());





                listItemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Players selectedPlayer = allPlayers.get(position);
                        Intent intent = new Intent(PlayersListActivity.this, PlayersDetailsActivity.class);
                        intent.putExtra(PlayersDetailsActivity.EXTRA_PLAYERS_ID, selectedPlayer.getId());
                        startActivity(intent);


                    }
                });


                return listItemView;
            }
        };
        lsPlayers.setAdapter(adapter);
    }
}