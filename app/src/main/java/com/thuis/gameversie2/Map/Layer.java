package com.thuis.gameversie2.Map;

/**
 * Created by Elize on 1-8-2015.
 */
public class Layer {

    public int[][] getCoordinates() {
        return coordinates;
    }

    public String getName() {
        return name;
    }

    int[][] coordinates = null;
    String name = null;


    public int[] getAllGid() {
        return allGid;
    }

    int[] allGid = null;

    public Layer(int[][] coordinates, String name, int[] newAllGid) {
        this.coordinates = coordinates;
        this.name = name;
        this.allGid = newAllGid;
    }
}
