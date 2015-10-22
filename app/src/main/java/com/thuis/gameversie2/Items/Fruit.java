package com.thuis.gameversie2.Items;

/**
 * Created by Elize on 5-9-2015.
 */
public class Fruit extends Item{

    protected int sellPrice = 0;

    protected int buyPrice = 0;

    protected int grade = 0;


    protected String name = null;
    public Fruit(int grade, String name) {
        this.grade = grade;
        this.name = name;
        setPrices(grade);
    }

    private void setPrices(int grade) {
        //TODO set pricing
    }


    @Override
    public void setImage() {

    }

}
