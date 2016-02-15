package com.thuis.gameversie2;

import android.content.Context;

import com.thuis.gameversie2.Character.MainCharacter;
import com.thuis.gameversie2.Inventory_System.Inventory;
import com.thuis.gameversie2.Items.Raw_Materials.Rock;
import com.thuis.gameversie2.Items.Raw_Materials.Wood;
import com.thuis.gameversie2.Map.Maps.PlayerHomeMap;

/**
 * Created by Elize on 6-8-2015.
 */
public class GamePanel {

    private static Context currentContext = null;

    private static Class[] spawnables = {Rock.class, Wood.class};

    public static Class[] getSpawnables() {
        return spawnables;
    }

    private static MainCharacter player = null;

    //Maps:
    private static PlayerHomeMap playerHomeMap = null;

    public static void setInventory(Inventory inventory) {
        GamePanel.inventory = inventory;
    }

    private static Inventory inventory = null;

    public static void setPlayerHomeMap(PlayerHomeMap playerHomeMap) {
        GamePanel.playerHomeMap = playerHomeMap;
    }

    public static PlayerHomeMap getPlayerHomeMap() {
        return playerHomeMap;
    }

    public static MainCharacter getPlayer() {
        return player;
    }

    public static void setPlayer(MainCharacter player) {
        GamePanel.player = player;
    }

    public static int getSpawnablesSize() {
        return spawnables.length;
    }

    public static Inventory getInventory() {
        return inventory;
    }

    public static Context getCurrentContext() {
        return currentContext;
    }

    public static void setCurrentContext(Context currentContext) {
        GamePanel.currentContext = currentContext;
    }
}
