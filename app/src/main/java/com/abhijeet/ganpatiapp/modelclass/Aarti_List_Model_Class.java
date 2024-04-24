package com.abhijeet.ganpatiapp.modelclass;

import android.graphics.Bitmap;

public class Aarti_List_Model_Class {

    private Bitmap image;

    private String name;

    public Aarti_List_Model_Class(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Bitmap getImage() {
        return image;
    }
}
