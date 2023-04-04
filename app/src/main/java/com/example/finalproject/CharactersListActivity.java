package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.finalproject.models.Characters;
import com.example.finalproject.models.Players;

import java.util.ArrayList;

public class CharactersListActivity extends AppCompatActivity {

    public static final String TAG = "CharactersListActivity";

    ListView lsCharacters;
    Button btnAddCharacter;
    Button btnBack;
    private CharactersDataAccess da;
    private ArrayList<Characters> allCharacters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters_list);
        btnAddCharacter = findViewById(R.id.btnAddCharacter);
        lsCharacters = findViewById((R.id.lsCharacters));
        da = new CharactersDataAccess(this);
        //da = new CSVTaskDataAccess(this);
        allCharacters = da.getAllTasks();

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CharactersListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        btnAddCharacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CharactersListActivity.this, CharactersDetailsActivity.class);
                startActivity(intent);
            }
        });

        cusAdap();

    }

    public void cusAdap() {
        ArrayAdapter<Characters> adapter = new ArrayAdapter<Characters>(this, R.layout.custom_character_layout, R.id.lblName, allCharacters) {
            @Override
            public View getView(int position, View convertView, ViewGroup parentListView) {
                View listItemView = super.getView(position, convertView, parentListView);
                TextView lblName = listItemView.findViewById(R.id.lblName);
                TextView lblBuild = listItemView.findViewById(R.id.lblBuild);


                Characters currentCharacter = allCharacters.get(position);
                lblName.setText(currentCharacter.getName());
                lblBuild.setText("level " + currentCharacter.getLvl() + " " + currentCharacter.getRace() + " " + currentCharacter.getBuild());


                listItemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Characters selectedCharacter = allCharacters.get(position);
                        Intent intent = new Intent(CharactersListActivity.this, CharactersDetailsActivity.class);
                        intent.putExtra(CharactersDetailsActivity.EXTRA_CHARACTERS_ID, selectedCharacter.getId());
                        startActivity(intent);


                    }
                });
                return listItemView;
            }
        };
        lsCharacters.setAdapter(adapter);
    }
}