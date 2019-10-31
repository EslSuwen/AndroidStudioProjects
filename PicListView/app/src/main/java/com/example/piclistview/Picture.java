package com.example.piclistview;

import android.graphics.Bitmap;

public class Picture {

    private String id;
    private String name;
    Bitmap bitmap;

    public Picture(String id, String name) {
        this.id = id;
        this.name = name;
        this.bitmap=null;
    }

    public Picture (){
        this.id=null;
        this.name=null;
        this.bitmap=null;
    }

    public Picture(String id, String name, Bitmap bitmap) {
        this.id = id;
        this.name = name;
        this.bitmap = bitmap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
