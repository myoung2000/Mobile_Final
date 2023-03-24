package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.finalproject.models.Characters;
import com.example.finalproject.models.Players;

import java.util.ArrayList;

public class PlayersListActivity extends AppCompatActivity {

    ListView lsPlayers;
    Button btnAddPlayer;
    private PlayersDataAccess da;
    private ArrayList<Players> allPlayers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_list);

        btnAddPlayer = findViewById(R.id.btnAddPlayer);
        btnAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PlayersListActivity.this, PlayersDetailsActivity.class);
                startActivity(i);
            }
        });

        lsPlayers = findViewById((R.id.lsPlayers));
        da = new PlayersDataAccess(this);
        //da = new CSVTaskDataAccess(this);
        allPlayers = da.getAllPlayers();



        ArrayAdapter<Players> adapter = new ArrayAdapter<Players>(this,android.R.layout.simple_list_item_1,allPlayers);
        lsPlayers.setAdapter(adapter);

        lsPlayers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Players selectedPlayers = allPlayers.get(i);

                Intent intent = new Intent(PlayersListActivity.this, PlayersDetailsActivity.class);
                intent.putExtra(PlayersDetailsActivity.EXTRA_PLAYERS_ID, selectedPlayers.getId());
                startActivity(intent);
            }
        });
    }
}