package com.thuis.gameversie2.Map.Maps;

import com.thuis.gameversie2.JsonParsers.JsonMapParser;
import com.thuis.gameversie2.Main_Menu_Activity;

/**
 * Created by Elize on 13-8-2015.
 */
public class PlayerHomeMap extends Map {

    String path = "river_intersection_home4.json";

    public PlayerHomeMap() {
        JsonMapParser jsonMapParser = new JsonMapParser(Main_Menu_Activity.getContext(), this);
    }

    @Override
    public String getPath() {
        return path;
    }

}
