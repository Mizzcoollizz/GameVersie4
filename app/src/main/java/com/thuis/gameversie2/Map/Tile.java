package com.thuis.gameversie2.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Elize on 1-8-2015.
 */
public class Tile {

    private String path = null;
    private Bitmap image = null;
    private int nameNumber = 0;

    public int getNameNumber() {
        return nameNumber;
    }

    public void setNameNumber(int nameNumber) {
        this.nameNumber = nameNumber;
    }

    public Tile(String path, Bitmap image, int nameNumber) {
        this.path = path;
        this.image = image;
        this.nameNumber = nameNumber;
    }

    public Bitmap getImage() {
        return image;
    }
}
