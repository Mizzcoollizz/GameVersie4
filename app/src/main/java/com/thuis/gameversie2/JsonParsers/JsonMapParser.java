package com.thuis.gameversie2.JsonParsers;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.JsonReader;
import android.util.Log;

import com.thuis.gameversie2.Interactive.Bush;
import com.thuis.gameversie2.Interactive.Field;
import com.thuis.gameversie2.Interactive.Interactive;
import com.thuis.gameversie2.Interactive.Tree;
import com.thuis.gameversie2.Interactive.Water;
import com.thuis.gameversie2.Items.Berries.Raspberry;
import com.thuis.gameversie2.Map.CollisionObject;
import com.thuis.gameversie2.Map.ItemSpawnArea;
import com.thuis.gameversie2.Map.Layer;
import com.thuis.gameversie2.Map.Maps.Map;
import com.thuis.gameversie2.Map.Tile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Elize on 31-7-2015.
 */
public class JsonMapParser {

    private InputStream is = null;
    private Context context = null;
    private Map mapToParse = null;
    private BufferedReader rd = null;
    byte[] bytes = null;

    private ArrayList<Layer> layersToDraw = new ArrayList<>();
    private ArrayList<CollisionObject> collisionObjects = new ArrayList<>();
    private ArrayList<Interactive> interactiveObjects = new ArrayList<>();
    private ArrayList<ItemSpawnArea> itemSpawnAreas = new ArrayList<>();
    private java.util.Map<Integer, Tile> tilesCollection = new HashMap<Integer,Tile>();

    private int map_width = 0;
    private int map_height = 0;
    private int tile_width = 0;
    private int tile_height = 0;



    /**
     * Constructor for JsonMapParser.
     * The path of the json file can be gathered by the map.getPath() method.
     * If the file is too big to be stored in a string/json object, we are going to use the parseMapAlternatively() method.
     * Otherwise, the file is read, and a jsonobject is created.
     * @param context
     * @param map: map to parse.
     */
    public JsonMapParser(Context context, Map map){
        try{
            this.mapToParse = map;
            this.context = context;
            getMainMapProperties(map.getPath());
            is = context.getAssets().open(map.getPath());
            rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            JSONObject completeMapObject = null;


                int size = is.available();
                if(size < Runtime.getRuntime().freeMemory() && size > 0) {
                    bytes = new byte[size];
                    is.read(bytes);
                    completeMapObject = new JSONObject(new String(bytes, "UTF-8"));
                    readJsonMapObject(completeMapObject);
                }else {
                    parseMapAlternatively(rd);
                }

        } catch (IOException e) {
            e.printStackTrace();
        }catch (JSONException e) {
            e.printStackTrace();
        }catch(OutOfMemoryError ex) {
            Log.i("tag", "OutOfMemoryError, parsing alternatively...");
            ex.printStackTrace();
            parseMapAlternatively(rd);
        }finally{
            try{
                is.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * We first need to get the map width and height, or otherwise the app will crash.
     * @param path: the path to the jsonfile;
     * @throws IOException: when the inputStream is not found.
     */
    private void getMainMapProperties(String path) throws IOException {
        is = context.getAssets().open(path);
        rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String string = null;

        String heightString = "\"height\":";
        String widthString = "\"width\":";
        int openingChars = 0;

        while ((string = rd.readLine()) != null) {
            if(string.contains("{")){
                openingChars++;
            }
            if(string.contains("[")){
                openingChars++;
            }
            if(string.contains("}")){
                openingChars--;
            }
            if(string.contains("]")){
                openingChars--;
            }
            if(openingChars == 1) {
                if (string.contains(heightString)) {
                    string = string.trim().replace(",", "");
                    int index = string.indexOf(heightString) + heightString.length();
                    map_height = Integer.parseInt(string.substring(index));
                }else if(string.contains(widthString)){
                    string = string.trim().replace(",", "");
                    int index = string.indexOf(widthString) + widthString.length();
                    map_width = Integer.parseInt(string.substring(index));
                }
            }
        }
    }

    private void parseMapAlternatively(Reader rd){

        JsonReader jsonReader = new JsonReader(rd);
        jsonReader.setLenient(true);
        readJsonMapByJsonReader(jsonReader);
    }

    private void readJsonMapByJsonReader(JsonReader jsonReader) {
        try {

            jsonReader.beginObject();
            while(jsonReader.hasNext()){
                String name = jsonReader.nextName();
                switch(name){
                    case "tilewidth":
                        tile_width = jsonReader.nextInt();
                        break;
                    case "tileheight":
                        tile_height = jsonReader.nextInt();
                        break;
                    case "layers":
                        setLayersbyReader(jsonReader);
                        break;
                    case "tilesets":
                        setTilesArrayUsingReader(jsonReader);
                        break;
                    default:
                        jsonReader.skipValue();
                        break;
                }
            }
            jsonReader.endObject();
            jsonReader.close();

            mapToParse.setMap(map_width, map_height, tile_width, tile_height, layersToDraw, tilesCollection,
                    context, collisionObjects, interactiveObjects, itemSpawnAreas);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("tag", "JsonException...");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("tag", "IOException..." + e.getMessage());
        }

    }

    private void setTilesArrayUsingReader(JsonReader jsonReader) throws IOException, JSONException {

        jsonReader.beginArray();
        while(jsonReader.hasNext()){

            int firstGid = 0;
            ArrayList<Tile> tilesInTilesSet = new ArrayList<>();
            jsonReader.beginObject();
            while(jsonReader.hasNext()){

                String name = jsonReader.nextName();
                switch (name){
                    case "firstgid":
                        firstGid = jsonReader.nextInt();
                        break;
                    case "tiles":
                        jsonReader.beginObject();
                        while(jsonReader.hasNext()){

                            String number = jsonReader.nextName();
                            String path = null;
                            jsonReader.beginObject();
                            while(jsonReader.hasNext()) {

                                String nextName = jsonReader.nextName();
                                if (nextName.equals("image")) {
                                    path =  jsonReader.nextString().toLowerCase();
                                } else {
                                    jsonReader.skipValue();
                                }
                            }
                            jsonReader.endObject();

                            Tile tile = new Tile(path, loadImage(path) , Integer.parseInt(number));
                            tilesInTilesSet.add(tile);

                        }
                        jsonReader.endObject();
                        break;
                    default:
                        jsonReader.skipValue();
                        break;
                }
            }
            jsonReader.endObject();
            for(Tile tile: tilesInTilesSet){
                tile.setNameNumber(tile.getNameNumber() + firstGid);
                tilesCollection.put(tile.getNameNumber(), tile);
            }

        }

        jsonReader.endArray();
    }

    private void setLayersbyReader(JsonReader jsonReader) throws IOException, JSONException {

        jsonReader.beginArray();
        while(jsonReader.hasNext()){
            String name_layer = null;
            String type_layer = null;
            int[][] layerCoordinates = new int[map_height][map_width];
            int[] layerWithId = new int[map_width * map_height];
            JSONArray objectsArray = new JSONArray();

            jsonReader.beginObject();
            while(jsonReader.hasNext()) {
                String name = jsonReader.nextName();
                switch (name) {
                    case "name":
                        name_layer =  jsonReader.nextString();
                        break;
                    case "type":
                        type_layer = jsonReader.nextString();
                        break;
                    case "data":
                        jsonReader.beginArray();
                        int i = 0;
                        while(jsonReader.hasNext()){
                            int id = jsonReader.nextInt();
                            layerWithId[i] = id;
                            i++;
                        }
                        for (int tileY = 0; tileY < map_height; tileY++) {
                            for (int tileX = 0; tileX < map_width; tileX++) {
                                layerCoordinates[tileY][tileX] = layerWithId[tileX + (tileY * map_width)];
                            }
                        }
                        jsonReader.endArray();
                        break;
                    case "objects":
                        objectsArray = getObjectFromLayerUsingReader(jsonReader);
                        break;
                    default:
                        jsonReader.skipValue();
                        break;
                }


            }

            jsonReader.endObject();

            if(type_layer.equals("tilelayer")){
                layersToDraw.add(new Layer(layerCoordinates, name_layer, layerWithId));
            }else if(type_layer.equals("objectgroup")){
                JSONObject jsonObject = new JSONObject().put("objects", objectsArray);
                setObjectLayerByType(name_layer, type_layer, jsonObject);
            }

        }
        jsonReader.endArray();

    }

    private void setObjectLayerByType(String name_layer, String type_layer, JSONObject jsonObject) throws JSONException {
        if(type_layer.equals("objectgroup") && name_layer.equals("Collision")){
            collisionObjects = getCollisionObjectLayer(jsonObject);
        }else if(type_layer.equals("objectgroup") && name_layer.equals("Interactive")){
            interactiveObjects = getInteractiveObjectLayer(jsonObject);
        }else if((type_layer.equals("objectgroup") && name_layer.equals("ObjectSpawnArea"))){
            itemSpawnAreas = getItemSpawnAreas(jsonObject);
        }
    }

    private JSONArray getObjectFromLayerUsingReader(JsonReader jsonReader) throws IOException, JSONException {
        JSONArray objectsArray = new JSONArray();
        jsonReader.beginArray();
        while(jsonReader.hasNext()){
            JSONObject jsonObject1 = new JSONObject();
            jsonReader.beginObject();
            while(jsonReader.hasNext()){
                String nameInObjectsArray = jsonReader.nextName();
                switch (nameInObjectsArray){
                    case "width":
                        jsonObject1.put(nameInObjectsArray, jsonReader.nextInt());
                        break;
                    case "height":
                        jsonObject1.put(nameInObjectsArray, jsonReader.nextInt());
                        break;
                    case "x":
                        try {
                            jsonObject1.put(nameInObjectsArray, jsonReader.nextInt());
                        }catch(NumberFormatException ex){
                            ex.printStackTrace();
                        }
                        break;
                    case "y":
                        try {
                            jsonObject1.put(nameInObjectsArray, jsonReader.nextInt());
                        }catch(NumberFormatException ex){
                            ex.printStackTrace();
                        }
                        break;
                    case "type":
                        jsonObject1.put(nameInObjectsArray, jsonReader.nextString());
                        break;
                    case "properties":
                        jsonReader.beginObject();
                        JSONObject propertiesObject = new JSONObject();
                        while(jsonReader.hasNext()){
                            propertiesObject.put(jsonReader.nextName(), jsonReader.nextString());
                        }
                        jsonReader.endObject();
                        jsonObject1.put(nameInObjectsArray, propertiesObject);
                        break;
                    default:
                        jsonReader.skipValue();
                        break;
                }
            }
            jsonReader.endObject();
            objectsArray.put(jsonObject1);
        }
        jsonReader.endArray();
        return objectsArray;
    }

    private void readJsonMapObject(JSONObject completeMapObject) {
        try {
            map_width = completeMapObject.getInt("width");
            map_height = completeMapObject.getInt("height");
            tile_width = completeMapObject.getInt("tilewidth");
            tile_height = completeMapObject.getInt("tileheight");

            //get the layers with id

            JSONArray layersArray = completeMapObject.getJSONArray("layers");

            for (int layerInt = 0; layerInt < layersArray.length(); layerInt++) {

                String type = layersArray.getJSONObject(layerInt).getString("type");
                //Get collision objects
                if(type.equals("objectgroup") && layersArray.getJSONObject(layerInt).getString("name").equals("Collision")){
                    collisionObjects = getCollisionObjectLayer(layersArray.getJSONObject(layerInt));
                //Get other tilelayers
                }else if(type.equals("tilelayer")){
                    Layer layer = getTileLayer(map_width, map_height, layersArray.getJSONObject(layerInt));
                    layersToDraw.add(layer);
                }else if(type.equals("objectgroup") && layersArray.getJSONObject(layerInt).getString("name").equals("Interactive")){
                    interactiveObjects = getInteractiveObjectLayer(layersArray.getJSONObject(layerInt));
                }else if((type.equals("objectgroup") && layersArray.getJSONObject(layerInt).getString("name").equals("ObjectSpawnArea"))){
                    itemSpawnAreas = getItemSpawnAreas(layersArray.getJSONObject(layerInt));
                }

            }



            //get the tilesets
            JSONArray tilesetArray = completeMapObject.getJSONArray("tilesets");

            java.util.Map<Integer, Tile> tilesCollection = new HashMap<Integer,Tile>();

            for (int tilesetInArray = 0; tilesetInArray < tilesetArray.length(); tilesetInArray++) {

                JSONObject tilesSet = tilesetArray.getJSONObject(tilesetInArray);
                JSONObject tiles = tilesSet.getJSONObject("tiles");

                int firstgid = tilesSet.getInt("firstgid");
                int number = 0;

                while(tiles.has(Integer.toString(number))){
                    JSONObject tile = tiles.getJSONObject(Integer.toString(number));
                    String imgPath = tile.getString("image").toLowerCase();

                    int newNumber = number + firstgid;
                    Bitmap image = loadImage(imgPath);

                    Tile tileObject = new Tile(imgPath, image , newNumber);
                    tilesCollection.put(newNumber, tileObject);
                    number++;
                }

            }


            mapToParse.setMap(map_width, map_height, tile_width, tile_height, layersToDraw, tilesCollection,
                    context, collisionObjects, interactiveObjects, itemSpawnAreas);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("Error: ", " =" + e.getMessage());
        }
    }

    private ArrayList<ItemSpawnArea> getItemSpawnAreas(JSONObject layer) throws JSONException {
        ArrayList<ItemSpawnArea> itemSpawnAreas = new ArrayList<>();

        JSONArray objectsArray = layer.getJSONArray("objects");
        for(int i = 0; i < objectsArray.length(); i++){
            int x = (int)objectsArray.getJSONObject(i).get("x");
            int y = (int)objectsArray.getJSONObject(i).get("y");
            int height = (int)objectsArray.getJSONObject(i).get("height");
            int width = (int)objectsArray.getJSONObject(i).get("width");

            ItemSpawnArea itemSpawnArea = new ItemSpawnArea(x, y, width, height);
            itemSpawnAreas.add(itemSpawnArea);
        }
        return itemSpawnAreas;
    }

    private ArrayList<CollisionObject> getCollisionObjectLayer(JSONObject layer) throws JSONException{

        ArrayList<CollisionObject> collisionObjects = new ArrayList<>();

        JSONArray objectsArray = layer.getJSONArray("objects");
        for(int i = 0; i < objectsArray.length(); i++){
            JSONObject collisionJSONObject = objectsArray.getJSONObject(i);
            int x = (int)collisionJSONObject.get("x");
            int y = (int)collisionJSONObject.get("y");
            int height = (int)collisionJSONObject.get("height");
            int width = (int)collisionJSONObject.get("width");
            String type = collisionJSONObject.getString("type");

            boolean lowObject = false;
            if(collisionJSONObject.has("properties")) {
                JSONObject properties = collisionJSONObject.getJSONObject("properties");
                if (properties.has("lowObject")) {
                    lowObject = properties.getBoolean("lowObject");
                }
            }
            CollisionObject collisionObject = new CollisionObject(x, y, type,  lowObject, width, height);
            collisionObjects.add(collisionObject);
        }
        return collisionObjects;
    }

    private ArrayList<Interactive> getInteractiveObjectLayer(JSONObject layer) throws JSONException{

        ArrayList<Interactive> interactiveObjects = new ArrayList<>();


        JSONArray objectsArray = layer.getJSONArray("objects");
        for(int i = 0; i < objectsArray.length(); i++){
            int x = (int)objectsArray.getJSONObject(i).get("x");
            int y = (int)objectsArray.getJSONObject(i).get("y");
            int height = (int)objectsArray.getJSONObject(i).get("height");
            int width = (int)objectsArray.getJSONObject(i).get("width");
            String type = objectsArray.getJSONObject(i).getString("type");
            int growState = 0;
            boolean collision = false;
            boolean lowObject = false;

            if(objectsArray.getJSONObject(i).has("properties")){
                JSONObject properties = objectsArray.getJSONObject(i).getJSONObject("properties");
                if(properties.has("growstate")){
                    growState = properties.getInt("growstate");
                }
                if(properties.has("lowObject")){
                    lowObject = properties.getBoolean("lowObject");
                }
                if(properties.has("collision")){
                    collision = properties.getBoolean("collision");
                }
            }

            Interactive interactive = getInteractiveObjectByType(x, y, height, width, type, growState);


                interactiveObjects.add(interactive);



        }

        return new ArrayList(Arrays.asList(interactiveObjects.toArray()));
    }

    /**
     * @param x: the x location of the object given by the json reader.
     * @param y: the y location of the object given by the json reader.
     * @param height: the height of the object given by the json reader(usually 32px);
     * @param width: the width of the object given by the json reader(usually 32px)
     * @param type: the type of the intearctive, here transformed into a sort of class.
     * @param growState: if the interactive is an already grown object, the grow state is given.
     * @return the interactive to put in the interactives ArrayList.
     */
    private Interactive getInteractiveObjectByType(int x, int y, int height, int width, String type, int growState) {
        //TODO first add a new ItemSpawnArea to the map!
        switch (type) {
            case "full_raspberry_bush":
                return new Bush(x, y, width, height, true, new Raspberry());
            case "empty_raspberry_bush":
                return new Bush(x, y, width, height, false, new Raspberry());
            case "field":
                return new Field(x, y, width, height);
            case "tree":
                return new Tree(x, y, width, height, growState);
            case "water":
                return new Water(x, y, width, height);
            default:
                return null;
        }
    }

    private Layer getTileLayer(int map_width, int map_height, JSONObject layerObject) throws JSONException {

        int[][] layerCoordinates = new int[map_height][map_width];
        int[] layerWithId = new int[map_width * map_height];
        
        String name = layerObject.getString("name").toLowerCase();
        JSONArray layerData = layerObject.getJSONArray("data");
        for (int i2 = 0; i2 < map_height * map_width; i2++) {

            int id = Integer.parseInt(layerData.get(i2).toString());
            layerWithId[i2] = id;

        }

        for (int tileY = 0; tileY < map_height; tileY++) {
            for (int tileX = 0; tileX < map_width; tileX++) {
                layerCoordinates[tileY][tileX] = layerWithId[tileX + (tileY * map_width)];
            }
        }
        return new Layer(layerCoordinates, name, layerWithId);
    }

    public Bitmap loadImage(String path) {

            AssetManager mngr = context.getAssets();
            InputStream is2 = null;
            try {
                is2 = mngr.open(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
                Bitmap newBitmamp = BitmapFactory.decodeStream(is2);
                return newBitmamp;


        }

}
