package com.thuis.gameversie2.Items;

import android.graphics.Canvas;

/**
 * Created by Elize on 5-9-2015.
 */
public abstract class Fruit extends Item{


    protected String name = null;
    public Fruit(int grade, String name) {
        this.grade = grade;
        this.name = name;
        setPrices(grade);
    }

    private void setPrices(int grade) {
        //TODO set pricing
    }




}
