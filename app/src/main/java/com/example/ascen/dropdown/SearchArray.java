package com.example.ascen.dropdown;

public class SearchArray {

    String name;
    String id;
    String alternareID;

    public SearchArray(String name, String id, String alternareID){
        this.name = name;
        this.alternareID = alternareID;
        this.id = id;
    }

    public String getAlternareID() {
        return alternareID;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
