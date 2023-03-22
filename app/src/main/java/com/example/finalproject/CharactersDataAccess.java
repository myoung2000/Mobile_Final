package com.example.finalproject;

import android.content.Context;

import com.example.finalproject.models.Characters;

import java.util.ArrayList;
import java.util.Date;

public class CharactersDataAccess {

    private Context context;

    public CharactersDataAccess(Context c){
        this.context = c;
    }

    private static ArrayList<Characters> allCharacters = new ArrayList<Characters>(){{
        add(new Characters(1, "Skamos", "Artificer", "Tiefling", 12, new Date()));
        add(new Characters(2, "Dramos", "Homebrew Stand User", "Aasimar", 20, new Date()));
        add(new Characters(3, "Kratos", "Barbarian", "Human", 11, new Date()));
    }};

    public Characters insertCharacter(Characters c) throws Exception {
        if(c.isValid()) {
            allCharacters.add(c);
            c.setId(getMaxId() + 1);
            return c;
        }else{
            throw new Exception("INSERT FAILED INVALID CHARACTER");
        }
    }


    private long getMaxId(){
        long maxId = 0;
        for(Characters c : allCharacters){
            long cId = c.getId();
            maxId = cId > maxId ? cId : maxId;
        }
        return maxId;
    }

    public ArrayList<Characters> getAllTasks(){
        ArrayList<Characters>copyList = new ArrayList();
        for(Characters c : allCharacters){
            Characters copy = new Characters(c.getId(), c.getName(), c.getBuild(), c.getRace(), c.getLvl(), c.getCreated());
            copyList.add(copy);
        }
        return copyList;
    }

    public Characters getCharacterById(long id){
        for(Characters c : allCharacters){
            if(c.getId() == id){
                return new Characters(c.getId(), c.getName(), c.getBuild(), c.getRace(), c.getLvl(), c.getCreated());
            }
        }
        return null;
    }

    public Characters updateCharacter(Characters c) throws Exception {
        if(c.isValid()) {
            for (Characters currentCharacter : allCharacters) {
                if (currentCharacter.getId() == c.getId()) {
                    currentCharacter.setBuild((c.getBuild()));
                    currentCharacter.setName(c.getName());
                    currentCharacter.setRace(c.getRace());
                    currentCharacter.setLvl(c.getLvl());
                    currentCharacter.setCreated(c.getCreated());
                    break;
                }
            }
            return c;
        }else{
            throw new Exception("UPDATE FAILED, INVALID CHARACTER");
        }
    }

    public int deleteTask(Characters c){
        for(Characters currentCharacters : allCharacters){
            if(currentCharacters.getId() == c.getId()){
                allCharacters.remove(currentCharacters);
                return 1;
            }
        }
        return 0;
    }
}
