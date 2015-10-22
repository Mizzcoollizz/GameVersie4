package com.thuis.gameversie2.Map;

import com.thuis.gameversie2.GamePanel;
import com.thuis.gameversie2.MapScreen.GameView_Activity;
import com.thuis.gameversie2.JsonParsers.JsonMapParser;
import com.thuis.gameversie2.Map.Maps.Map;
import com.thuis.gameversie2.Map.Maps.PlayerHomeMap;

/**
 * Created by Elize on 13-8-2015.
 */
public class MapHandler {

    private static JsonMapParser jsonMapParser = null;
    public static Map currentMap = null;

    public static Map getCurrentMap() {
        return currentMap;
    }


    private static void updateMap(){
        currentMap.update();
    }

    public static void setFarm(){
        if(GamePanel.getPlayerHomeMap() == null || GamePanel.getPlayerHomeMap().equals(null)){
            PlayerHomeMap map = new PlayerHomeMap();
            jsonMapParser = new JsonMapParser(GameView_Activity.getContext(), map);
            currentMap = map;
            GamePanel.setPlayerHomeMap(map);
        }else{
            currentMap = GamePanel.getPlayerHomeMap();
        }
        updateMap();

    }
}
