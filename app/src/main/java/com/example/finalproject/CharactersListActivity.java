package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.finalproject.models.Characters;

import java.util.ArrayList;

public class CharactersListActivity extends AppCompatActivity {

    public static final String TAG = "CharactersListActivity";

    private ListView lsCharacters;
    private Button btnAddCharacter;
    private CharactersDataAccess da;
    private ArrayList<Characters> allCharacters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters_list);

        lsCharacters = findViewById((R.id.lsCharacters));
        da = new CharactersDataAccess(this);
        //da = new CSVTaskDataAccess(this);
        allCharacters = da.getAllTasks();



        ArrayAdapter<Characters> adapter = new ArrayAdapter<Characters>(this,android.R.layout.simple_list_item_1,allCharacters);
        lsCharacters.setAdapter(adapter);
    }
}