package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.models.Players;

import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayersDetailsActivity extends AppCompatActivity {
    public static final String TAG = "PlayersDetailsActivity";
    public static final String EXTRA_PLAYERS_ID = "playersId";

    PlayersDataAccess da;

    Players player;

    EditText txtFirstName;
    EditText txtLastName;
    CheckBox chkActive;
    Button btnSave;
    Button btnDelete;

    @Override
    public <T extends View> T findViewById(int id) {
        return super.findViewById(id);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_details);

        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById((R.id.txtLastName));
        chkActive = findViewById(R.id.chkActive);
        btnDelete = findViewById(R.id.btnDelete);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(save()){
                    Intent i = new Intent(PlayersDetailsActivity.this, PlayersListActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(PlayersDetailsActivity.this, "Unable to save player", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d(TAG, "DELETE TASK: "  + task.getId());
//                da.deleteTask(task);
//                Intent i = new Intent(TaskDetailsActivity.this, TaskListActivity.class);
//                startActivity(i);

                showDeleteDialog();
            }
        });

        da = new PlayersDataAccess(this);

        Intent i = getIntent();
        long id = i.getLongExtra(EXTRA_PLAYERS_ID, 0);
        if(id > 0){
            player = da.getPlayerById(id);
            Log.d(TAG, player.toString());
            putDataIntoUI();
            btnDelete.setVisibility(View.VISIBLE);
        }else{
            Log.d(TAG, "Help");
        }
    }

    private void putDataIntoUI(){
        if(player != null){
            txtFirstName.setText(player.getFirstName());
            txtLastName.setText(player.getLastName());
            chkActive.setChecked(player.isActive());
        }
    }

    private boolean validate(){
        boolean isValid = true;

        if(txtFirstName.getText().toString().isEmpty()){
            isValid = false;
            txtFirstName.setError("You must enter a first name");

        }

        if(txtLastName.getText().toString().isEmpty()){
            isValid = false;
            txtLastName.setError("You must enter a last name");
        }


        return isValid;
    }

    private boolean save(){
        if(validate()){
            getDataFromUI();
            if(player.getId() > 0){
                Log.d(TAG, "UPDATE PLAYER");
                try {
                    da.updatePlayer(player);
                } catch (Exception e) {
                    //e.printStackTrace();
                    Log.d(TAG, e.getMessage());
                }
            }else{
                Log.d(TAG, "INSERT PLAYER");
                try {
                    da.insertPlayer(player);
                } catch (Exception e) {
                    // e.printStackTrace();
                    Log.d(TAG, "");
                }
            }
            return true;
        }
        return false;
    }

    private void getDataFromUI(){
        String firstName = txtFirstName.getText().toString();
        Log.d(TAG, firstName);
        String lastName = txtLastName.getText().toString();
        boolean active = chkActive.isChecked();




        if(player != null){
            player.setFirstName(firstName);
            player.setLastName(lastName);
            player.setActive(active);
        }else{
            player = new Players(firstName, lastName, active);
        }
    }

    private void showDeleteDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Delete Player");
        alert.setMessage("Are you sure you want to delete this player?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                da.deletePlayer(player);
                startActivity(new Intent(PlayersDetailsActivity.this, PlayersListActivity.class));
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alert.show();
    }

}