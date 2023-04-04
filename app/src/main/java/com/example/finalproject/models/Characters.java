package com.example.finalproject.models;

import java.util.Date;

public class Characters {
    private long id;
    private String name;
    private String build;
    private String race;
    private long lvl;
    private Date created;

    public Characters(long id, String name, String build, String race, long lvl, Date created) {
        this.id = id;
        this.name = name;
        this.build = build;
        this.race = race;
        this.lvl = lvl;
        this.created = created;
    }

    public Characters(String name, String build, String race, long lvl, Date date) {
        this.name = name;
        this.build = build;
        this.race = race;
        this.lvl = lvl;
        this.created = date;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public long getLvl() {
        return lvl;
    }

    public void setLvl(long lvl) {
        this.lvl = lvl;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString(){
        String createdStr = created != null ? created.toString(): "NULL";
        String msg = "ID: " + id + " NAME: " + name + " CLASS: " + build + " RACE: " + race + " LEVEL: " + lvl + "CREATED: " + createdStr;
        return msg;
    }

    public boolean isValid(){
        if(this.id < 0){
            return false;
        }

        if(this.name.isEmpty()){
            return false;
        }

        if(this.build.isEmpty()){
            return false;
        }

        if(this.race.isEmpty()){
            return false;
        }

        if(this.lvl < 0){
            return false;
        }

        if(this.created == null){
            return false;
        }
        return true;
    }
}
