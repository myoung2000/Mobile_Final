package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void buttonPressed(View v){
        //Toast.makeText(this, R.string.toast_sample, Toast.LENGTH_LONG).show();
        int idNumber = v.getId();
        String idName = getResources().getResourceEntryName(idNumber);
        //Toast.makeText(this, idName, Toast.LENGTH_LONG).show();

        Intent i;


        switch (idName) {
            case "btnCharacterList":
                i = new Intent(this, CharactersListActivity.class);
                startActivity(i);
                break;
        }
    }
}