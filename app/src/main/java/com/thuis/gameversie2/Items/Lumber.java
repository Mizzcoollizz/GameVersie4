package com.thuis.gameversie2.Items;

public class Lumber extends Material {

    private final int SELL_PRICE = 5;
    private final int BUY_PRICE = 25;


    public Lumber(){
        setImage();
        sellPrice = SELL_PRICE;
        buyPrice = BUY_PRICE;
        setGrade();
        name = "Lumber";
    }

    public Lumber(int grade) {
        this.grade = grade;
        setImage();
        sellPrice = SELL_PRICE;
        buyPrice = BUY_PRICE;
        name = "Lumber";
    }

    @Override
    public void setImage() {
        //image =  BitmapFactory.decodeResource(GameView_Activity.getContext().getResources(), R.drawable.?);

    }

}
