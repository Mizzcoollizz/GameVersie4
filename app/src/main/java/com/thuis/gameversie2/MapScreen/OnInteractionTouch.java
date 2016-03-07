package com.thuis.gameversie2.MapScreen;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.thuis.gameversie2.GamePanel;
import com.thuis.gameversie2.Interactive.Interactive;
import com.thuis.gameversie2.Map.MapHandler;
import com.thuis.gameversie2.Map.Maps.Map;

/**
 * Created by Elize on 15-8-2015.
 */
public class OnInteractionTouch implements View.OnTouchListener {
    boolean pressed = false;
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN){
           pressed = true;
            checkInteraction(event);

        }
        else if(event.getAction() == MotionEvent.ACTION_UP){
           pressed = false;
        }
        return false;
    }

    private void checkInteraction(MotionEvent event) {
        if(GamePanel.getPlayer().getOnTouch(event)){
            GamePanel.getPlayer().manageOnTouch();
        }else{
            checkAndInteract(event);
        }
    }

    private void checkAndInteract(MotionEvent event){
        Interactive interactive = MapHandler.getCurrentMap().getInteractiveObjectByLocation((int) event.getX(), (int) event.getY());
        if(interactive != null) {
            if(GamePanel.getPlayer().isHoldingTool()){
                interactive.onInteraction(GamePanel.getPlayer().getToolHolding());
            }else{
                interactive.onInteraction();
            }
        }
    }
}
