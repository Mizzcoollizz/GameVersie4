package com.thuis.gameversie2.Map;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.thuis.gameversie2.GamePanel;
import com.thuis.gameversie2.Interactive.Interactive;
import com.thuis.gameversie2.Items.Spawnable_Item;
import com.thuis.gameversie2.Items.Tools.Tool;

/**
 * Created by Elize on 18-8-2015.
 */
public class ItemSpawnArea extends Interactive {

    Spawnable_Item item = null;
    Interactive interactive = null;
    CollisionObject collisionObject = null;

    public ItemSpawnArea(long xLocation, long yLocation, int width, int height) {
        super(xLocation, yLocation, width, height);
        collisionObject = new CollisionObject(getXLocation(), getYLocation(), "spawnable_Item_Area", true, width, height);
    }

    /**
     * Constructor for trees and other large objects.
     * @param xLocation
     * @param yLocation
     * @param width
     * @param height
     * @param interactive: the interactive object this area now holds.
     */
    public ItemSpawnArea(long xLocation, long yLocation, int width, int height, Interactive interactive) {
        super(xLocation, yLocation, width, height);
        this.interactive = interactive;
        collisionObject = new CollisionObject(getXLocation(), getYLocation(), "spawnable_Item_Area", false, width, height);
    }

    public Spawnable_Item getItem() {
        MapHandler.getCurrentMap().getCollisionObjects().remove(collisionObject);
        return this.item;
    }

    public void setItem(Spawnable_Item item) {
        this.item = item;
        MapHandler.getCurrentMap().addcollisionObject(collisionObject);
    }

    public boolean hasItem() {
         return this.item != null || !this.item.equals(null);
    }

    public Rect getRect() {
        return new Rect((int) getXLocation(), (int) getYLocation(), getWidth(), getHeight());
    }

    @Override
    public void onInteraction() {
        if(item != null ){
            GamePanel.getPlayer().setItemHolding(item);
            item = null;
        }
    }

    @Override
    public void onInteraction(Tool tool) {
        //TODO
    }

    @Override
    public Bitmap getImage() {
        return null;
    }

    @Override
    public void update() {
        //TODO update()
        if(this.hasItem()){
            this.item = null;
        }
    }

    @Override
    public void setCollision() {
        if(this.hasItem()){
            super.setCollisionObject(new CollisionObject(getXLocation(), getYLocation(), "itemSpawnAreaWithItem", true, getHeight(), getWidth()));
        }
    }

    public boolean hasInteractive() {
        return (interactive != null);
    }

    public Interactive getInteractive() {
        return interactive;
    }

    public void setInteractive(Interactive interactive) {
        this.interactive = interactive;
    }
}
