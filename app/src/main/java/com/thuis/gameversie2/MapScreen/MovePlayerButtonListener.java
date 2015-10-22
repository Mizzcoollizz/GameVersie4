package com.thuis.gameversie2.MapScreen;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.thuis.gameversie2.Character.MainCharacter;
import com.thuis.gameversie2.GamePanel;
import com.thuis.gameversie2.R;

/**
 * Created by Elize on 5-8-2015.
 */
public class MovePlayerButtonListener implements View.OnTouchListener {
    private static boolean pressed = false;
    private static Button clickedButton = null;

    public MovePlayerButtonListener() {
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            pressed = true;
            clickedButton = (Button) v;
            GamePanel.getPlayer().setWalking(true);
        }
        else if(event.getAction() == MotionEvent.ACTION_UP){
            pressed = false;
            clickedButton = null;
            GamePanel.getPlayer().setWalking(false);
        }


        return true;
    }


    public static void checkButtonMovement() {

        if(pressed && clickedButton != null && !clickedButton.equals(null) ){
            int speed = GamePanel.getPlayer().getSpeed();
            switch(clickedButton.getId()) {
                case R.id.leftBTN:
                    GamePanel.getPlayer().setDirection("left");
                    GamePanel.getPlayer().walk(-speed, 0);
                    break;
                case R.id.rightBTN:
                    GamePanel.getPlayer().setDirection("right");
                    GamePanel.getPlayer().walk(speed, 0);
                    break;
                case R.id.upBTN:
                    GamePanel.getPlayer().setDirection("up");
                    GamePanel.getPlayer().walk(0, -speed);
                    break;
                case R.id.downBTN:
                    GamePanel.getPlayer().setDirection("down");
                    GamePanel.getPlayer().walk(0, speed);
                    break;
            }
        }
    }
}
