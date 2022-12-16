package com.example.bilabonnement.models;
//Udarbejdet af Malik Kütük
public class StatusSet {
    int value;
    String name;

    public StatusSet(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
