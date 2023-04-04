package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.models.Characters;
import com.example.finalproject.models.Players;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharactersDetailsActivity extends AppCompatActivity {
    public static final String TAG = "CharactersDetails";
    public static final String EXTRA_CHARACTERS_ID = "charactersId";

    CharactersDataAccess da;

    Characters character;

    EditText txtName;
    EditText txtBuild;
    EditText txtRace;
    EditText txtLvl;
    EditText txtCreated;
    Button btnSave;
    Button btnDelete;
    Button btnDate;
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters_details);

        txtName = findViewById(R.id.txtName);
        txtBuild = findViewById(R.id.txtBuild);
        txtRace = findViewById(R.id.txtRace);
        txtLvl = findViewById(R.id.txtLvl);
        txtCreated = findViewById(R.id.txtCreated);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        btnDate = findViewById(R.id.btnDate);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(save()){
                    Intent i = new Intent(CharactersDetailsActivity.this, CharactersListActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(CharactersDetailsActivity.this, "Unable to save character", Toast.LENGTH_LONG).show();
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

        da = new CharactersDataAccess(this);

        Intent i = getIntent();
        long id = i.getLongExtra(EXTRA_CHARACTERS_ID, 0);
        if(id > 0){
            character = da.getCharacterById(id);
            Log.d(TAG, character.toString());
            putDataIntoUI();
            btnDelete.setVisibility(View.VISIBLE);
        }else{
            Log.d(TAG, "Help");
        }
    }


    private void putDataIntoUI(){
        if(character != null){
            txtName.setText(character.getName());
            txtName.setText(character.getName());
            txtBuild.setText(character.getBuild());
            txtRace.setText(character.getRace());
            String textLvl = String.valueOf(character.getLvl());
            txtLvl.setText(textLvl);
            String createdStr =null;

            createdStr = sdf.format(character.getCreated());
            txtCreated.setText(createdStr);

        }
    }

    private boolean validate(){
        boolean isValid = true;

        if(txtName.getText().toString().isEmpty()){
            isValid = false;
            txtName.setError("You must enter a name");

        }

        if(txtBuild.getText().toString().isEmpty()){
            isValid = false;
            txtBuild.setError("You must enter a build");
        }

        if(txtRace.getText().toString().isEmpty()){
            isValid = false;
            txtRace.setError("You must enter a race");
        }

        if(txtLvl.getText().toString().isEmpty()){
            isValid = false;
            txtLvl.setError("You must enter a level");
        }

        Date createdDate = null;

        if(txtCreated.getText().toString().isEmpty()){
            isValid = false;
            txtCreated.setError("You must enter a date");
        }else{
            String regex = "^(1[0-2]|0[1-9])/(3[01]" +
                    "|[12][0-9]|0[1-9])/[0-9]{4}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher((CharSequence)txtCreated.getText().toString());
            if(!matcher.matches()){
                isValid = false;
                txtCreated.setError("The date entered is not valid");
            }
        }


        return isValid;
    }

    private boolean save(){
        if(validate()){
            getDataFromUI();
            if(character.getId() > 0){
                Log.d(TAG, "UPDATE CHARACTER");
                try {
                    da.updateCharacter(character);
                } catch (Exception e) {
                    //e.printStackTrace();
                    Log.d(TAG, e.getMessage());
                }
            }else{
                Log.d(TAG, "INSERT CHARACTER");
                try {
                    da.insertCharacter(character);
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
        String Name = txtName.getText().toString();
        String Build = txtBuild.getText().toString();
        String Race = txtRace.getText().toString();
        String Lvl = txtLvl.getText().toString();
        int finalLvl = Integer.parseInt(Lvl);
        String Created = txtCreated.getText().toString();

        Date date = null;

        try{
            date = sdf.parse(Created);
        } catch (ParseException e){
            Log.d(TAG, "Unable to parse date from string");
        }




        if(character != null){
            character.setName(Name);
            character.setBuild(Build);
            character.setRace(Race);
            character.setLvl(finalLvl);
            character.setCreated(date);
        }else{
            character = new Characters(Name, Build, Race, finalLvl, date);
        }
    }

    private void showDeleteDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Delete Character");
        alert.setMessage("Are you sure you want to delete this character?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                da.deleteCharacter(character);
                startActivity(new Intent(CharactersDetailsActivity.this, CharactersListActivity.class));
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


    private void showDatePicker(){

        Date today = new Date();
        int year = today.getYear() + 1900;
        int month = today.getMonth();
        int day = today.getDate();

        DatePickerDialog dp = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                String newM = String.valueOf(m + 1);
                if(newM.length() == 1){
                    newM = "0" + newM;
                }
                String newD = String.valueOf(d);
                if(newD.length() == 1){
                    newD = "0" + newD;
                }

                String selectedDate = (newM) + "/" + newD + "/" + y;
                txtCreated.setText(selectedDate);
                //datePicker.dismiss();
            }
        }, year, month, day);
        dp.show();
    }

    private String checkLength(int date){
        String newCheck = String.valueOf(date + 1);
        if(newCheck.length() == 1){
            newCheck = "0" + newCheck;
        }

        return newCheck;
    }


}