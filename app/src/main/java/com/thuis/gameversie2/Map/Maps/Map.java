package com.thuis.gameversie2.Map.Maps;

import android.content.Context;
import android.graphics.Bitmap;

import com.thuis.gameversie2.Character.MainCharacter;
import com.thuis.gameversie2.GamePanel;
import com.thuis.gameversie2.Interactive.Field;
import com.thuis.gameversie2.Interactive.Interactive;
import com.thuis.gameversie2.Interactive.Tree;
import com.thuis.gameversie2.Items.Spawnable_Item;
import com.thuis.gameversie2.Map.CollisionObject;
import com.thuis.gameversie2.Map.ItemSpawnArea;
import com.thuis.gameversie2.Map.Layer;
import com.thuis.gameversie2.Map.Tile;
import com.thuis.gameversie2.MapScreen.MapSurfaceView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

/**
 * Created by Elize on 31-7-2015.
 */
public abstract class Map {

    private int map_width = 0;
    private int map_height = 0;
    private int tile_width = 0;
    private int tile_height = 0;
    private ArrayList<Layer> layersToDraw = null;
    private java.util.Map<Integer, Tile> tilesCollection = new HashMap<Integer,Tile>();
    public Context context = null;
    public final String BACKGROUND_LAYER_NAME = "background";
    public final String PLANTS_LAYER_NAME = "Plants";
    public final String INTERACTIVE_LAYER_NAME = "Interactive";
    private ArrayList<CollisionObject> collisionObjects = new ArrayList();
    private ArrayList<Interactive> interactiveObjects = new ArrayList<>();
    private boolean spawnableItemsSpawned = false;
    private ArrayList<ItemSpawnArea> itemSpawnAreas = null;

    public ArrayList<ItemSpawnArea> getItemSpawnAreas() {
        return itemSpawnAreas;
    }

    public ArrayList<Interactive> getInteractiveObjects() {
        return interactiveObjects;
    }

    public abstract String getPath();

    public ArrayList<CollisionObject> getCollisionObjects() {
        return collisionObjects;
    }

    public java.util.Map<Integer, Tile> getTilesCollection() {
        return tilesCollection;
    }

    public int getMap_height() {
        return map_height;
    }

    public int getMap_width() {
        return map_width;
    }

    public int getTile_width() {
        return tile_width;
    }

    public int getTile_height() {
        return tile_height;
    }

    public ArrayList<Layer> getLayers() {
        return layersToDraw;
    }


    public void setMap(int new_map_width,
                              int new_map_height,
                              int new_tile_width,
                              ArrayList<Layer> new_layers,
                              java.util.Map<Integer, Tile> newTilesCollection,
                              Context new_context, ArrayList<CollisionObject> newCollisionObjects,
                              ArrayList<Interactive> newInteractiveObjects,
                              ArrayList<ItemSpawnArea> _itemSpawnAreas
                              ){
        context = new_context;
        map_width = new_map_width;
        map_height = new_map_height;
        tile_width = new_tile_width;
        tile_height = tile_width;
        layersToDraw = new_layers;
        tilesCollection = newTilesCollection;
        collisionObjects = newCollisionObjects;
        interactiveObjects = newInteractiveObjects;
        itemSpawnAreas = _itemSpawnAreas;
    }

    public Bitmap getImage(int i) {
        return tilesCollection.get(i).getImage();
    }

    public long[] getNextTile(){
        MainCharacter player = GamePanel.getPlayer();
        int playerTileX = player.getMapTileX();
        int playerTileY = player.getMapTileY();
        String direction = player.getDirection();
        long objectX = playerTileX;
        long objectY = playerTileY;

        if(direction.equals("left")){
            objectX -= 1;
        }else if(direction.equals("right")){
            objectX += 1;
        }else if(direction.equals("up")){
            objectY -= 1;
        }else if(direction.equals("down")){
            objectY += 1;
        }

        long[] tile = {objectX, objectY};
        return tile;
    }

    public boolean checkObjectCollision() {
        long[] tile = getNextTile();
        long objectX = tile[0];
        long objectY = tile[1];
        boolean collisionFound = false;
        for(CollisionObject c: collisionObjects){
            if(c.getXLocation() == objectX * this.getTile_width() && c.getYLocation() == objectY * this.getTile_height()) {
                if (c.checkCollision()) {
                    collisionFound = true;
                    break;
                }
            }

        }
        return collisionFound;
    }

    public long[][] getNextThreeTiles(){
        MainCharacter player = GamePanel.getPlayer();
        int playerTileX = player.getMapTileX();
        int playerTileY = player.getMapTileY();
        String direction = player.getDirection();
        long objectX = playerTileX;
        long objectY = playerTileY;

        long[][] tiles = new long[3][2];

            if (direction.equals("left")) {
                tiles[0][0] = objectX - 1;
                tiles[1][0] = objectX - 1;
                tiles[2][0] = objectX - 1;
                tiles[0][1] = objectY - 1;
                tiles[1][1] = objectY;
                tiles[2][1] = objectY + 1;
            } else if (direction.equals("right")) {
                tiles[0][0] = objectX + 1;
                tiles[1][0] = objectX + 1;
                tiles[2][0] = objectX + 1;
                tiles[0][1] = objectY - 1;
                tiles[1][1] = objectY;
                tiles[2][1] = objectY + 1;
            } else if (direction.equals("up")) {
                tiles[0][0] = objectX - 1;
                tiles[1][0] = objectX;
                tiles[2][0] = objectX + 1;
                tiles[0][1] = objectY - 1;
                tiles[1][1] = objectY - 1;
                tiles[2][1] = objectY - 1;
            } else if (direction.equals("down")) {
                tiles[0][0] = objectX - 1;
                tiles[1][0] = objectX;
                tiles[2][0] = objectX + 1;
                tiles[0][1] = objectY + 1;
                tiles[1][1] = objectY + 1;
                tiles[2][1] = objectY + 1;
            }
        return tiles;

    }

    public boolean checkBorderCollision(long mapX, long mapY, String direction ){

        if(mapX <= 0 && direction.equals("left")){
            GamePanel.getPlayer().setMapX(0);
            return true;

        }else if(mapX >= (this.getMap_width() * this.getTile_width()) && direction.equals("right")){
            GamePanel.getPlayer().setMapX((this.getMap_width() * this.getTile_width()));
            return true;
        }else if(mapY <= 0 && direction.equals("up")){
            GamePanel.getPlayer().setMapY(0);
            return true;
        }else if(mapY >= (this.getMap_height() * this.getTile_height()) && direction.equals("down")){
            GamePanel.getPlayer().setMapY((this.getMap_height() * this.getTile_height()));
            return true;
        }else{ return false;}
    }


    /**
     * Check the interaction of the user with the interactive objects.
     * The tab must be in the area where the player is standing. The area is calculated using getNextThreeTiles();
     * The touchX and touchY are calculated using the x and y location of the screen on the map(getScreenX() and getScreenY()).
     *
     * @param touchX: The x location of the tab on the screen.
     * @param touchY The y location of the tab on the screen.
     * @return The Interactive objects that the user is interacting with.
     * */

    public Interactive checkInteraction(int touchX, int touchY){
        Interactive tappedInteractive = null;
        touchX = touchX + (int)MapSurfaceView.getScreenX();
        touchY = touchY + (int)MapSurfaceView.getScreenY();
        long[][] tiles = getNextThreeTiles();
        ArrayList<Interactive> interactivesOnScreen = getAllInteractiveOnScreen();
        for(int i = 0; i < tiles.length; i++){
            for(Interactive interactive: interactivesOnScreen){
                if(interactive.getXLocation() == (tiles[i][0] * getMap_width()) && interactive.getYLocation() == (tiles[i][1]* getMap_height())) {
                    if (interactive.getRect().contains(touchX, touchY)) {
                        tappedInteractive = interactive;
                        break;
                    }
                }
            }
        }
        return tappedInteractive;
    }

    public ArrayList<Interactive> getAllInteractiveOnScreen(){
        ArrayList<Interactive> interactives = new ArrayList<>();

        for(Interactive interactive: interactiveObjects){
            if(MapSurfaceView.getScreenRect().contains(interactive.getRect())){
                interactives.add(interactive);
            }
        }

        for(Interactive interactive: interactives){
            interactive.setCollision();
        }

        return interactives;
    }

    public void update(){
        if(!spawnableItemsSpawned && itemSpawnAreas.size() != 0){
            spawnItems();
            spawnableItemsSpawned = true;
        }

    }

    private void spawnItems(){
        try {
            spawnItemsOnSpawnableAreas();
            spawnItemsInFields();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private void spawnItemsOnSpawnableAreas() throws IllegalAccessException, InstantiationException {
        int numberToSpawn = (getItemSpawnAreas().size() / 10) * 2 ;

        //get random spawn areas
        HashSet<ItemSpawnArea> tiles = new HashSet<>();
        HashSet<ItemSpawnArea> checkedAreas = new HashSet<>();
        while(tiles.size() != numberToSpawn && checkedAreas.size() != getItemSpawnAreas().size()){
            int index = new Random().nextInt(getItemSpawnAreas().size());
            ItemSpawnArea itemSpawnArea = getItemSpawnAreas().get(index);
            if(!itemSpawnArea.hasItem() && !itemSpawnArea.hasInteractive()) {
                tiles.add(itemSpawnArea);
            }
            checkedAreas.add(itemSpawnArea);
        }
        //add an item or Object to the tile
        for(ItemSpawnArea itemSpawnArea: tiles){
            int index = new Random().nextInt(10);
            getSpawnItemOrOtherObject(index, itemSpawnArea);

        }
    }

    private void getSpawnItemOrOtherObject(int index, ItemSpawnArea itemSpawnArea) throws IllegalAccessException, InstantiationException {

        switch(index){
            case 0:
                Tree tree = new Tree(itemSpawnArea.getXLocation(), itemSpawnArea.getYLocation(),
                                    itemSpawnArea.getWidth(), itemSpawnArea.getHeight());
                itemSpawnArea.setInteractive(tree);
                break;
            case 1:
                //Todo add new cactus
                break;
            default:
                int itemIndex = new Random().nextInt(GamePanel.getSpawnablesSize());
                Class classItem = GamePanel.getSpawnables()[itemIndex];
                Spawnable_Item item = (Spawnable_Item)classItem.newInstance();
                itemSpawnArea.setItem(item);
                break;

        }


    }

    private void spawnItemsInFields() throws IllegalAccessException, InstantiationException {
        ArrayList<Field> fields = new ArrayList<>();
        //get all fields
        for(Interactive interactive: getInteractiveObjects()){
            if(interactive.getClass().isInstance(Field.class)){
                fields.add((Field) interactive);
            }
        }
        int numberToSpawn = fields.size() / 10;

        HashSet<Field> tiles = new HashSet<>();
        HashSet<Field> checkedFields = new HashSet<>();
        while(tiles.size() != numberToSpawn && checkedFields.size() != fields.size()){
            int index = new Random().nextInt(fields.size());
            Field field = fields.get(index);
            if(!field.isPlowed() && ! field.isSown() && !field.hasCrop()) {
                tiles.add(fields.get(index));
            }
            checkedFields.add(field);
        }
        //add an item to the tile
        for(Field field: tiles){
            int index = new Random().nextInt(GamePanel.getSpawnablesSize());
            Class classItem = GamePanel.getSpawnables()[index];

            Spawnable_Item item = (Spawnable_Item) classItem.newInstance();
            field.setItem(item);
        }

    }

    public ArrayList<Interactive> getItemSpawnAreasInScreen(){
        ArrayList<Interactive> areas = new ArrayList<>();
        for(ItemSpawnArea itemSpawnArea:  getItemSpawnAreas()){
            if(MapSurfaceView.getScreenRect().contains(itemSpawnArea.getRect())){
                areas.add(itemSpawnArea);
            }
        }
        for(Interactive field:  getInteractiveObjects()){
            if(field.getClass().isInstance(Field.class)) {
                if (MapSurfaceView.getScreenRect().contains(field.getRect())) {
                    areas.add(field);
                }
            }
        }
        return areas;
    }



    public void addcollisionObject(CollisionObject collisionObject){
        this.collisionObjects.add(collisionObject);
    }

    public void removeCollisionObject(CollisionObject collisionObject){
        this.collisionObjects.remove(collisionObject);
    }


}
