package com.thuis.gameversie2.Inventory_System.Inventory_View;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.thuis.gameversie2.GamePanel;
import com.thuis.gameversie2.Inventory_System.Inventory_Slot;
import com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryListeners.OnItemTouchListener;
import com.thuis.gameversie2.Inventory_System.ToolInventorySlot;
import com.thuis.gameversie2.Items.Item;
import com.thuis.gameversie2.Items.Tools.Tool;
import com.thuis.gameversie2.R;

/**
 * Created by Elize on 31-12-2015.
 */
public class InventoryAlternativeDropView extends ImageView implements InventoryDropView{

    Bitmap emptyInventoryImage = null;

    public InventoryAlternativeDropView(Context context) {
        super(context);
        init(context);
    }

    public InventoryAlternativeDropView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public InventoryAlternativeDropView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public InventoryAlternativeDropView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void setEmptyInventoryImage(){
        if(emptyInventoryImage == null){
            emptyInventoryImage = BitmapFactory.decodeResource(getResources(), R.drawable.inventory_item_border);
        }

    }

    private void init(Context context){
        ViewGroup parent = (ViewGroup) ((Activity) context).findViewById(R.id.inventoryGridView);
        setOnTouchListener(new OnItemTouchListener(context, parent));
        setEmptyInventoryImage();
        setCurrentImage();
    }

    private void setCurrentImage(){
        if(getItemSlot() != null){
            Item item = getItemSlot().getItem();
            if(item != null) {
                this.setImageBitmap(item.getInventoryImage());
            }else{
                this.setImageBitmap(emptyInventoryImage);
            }
        }else{
            this.setImageBitmap(emptyInventoryImage);
        }
    }

    @Override
    public boolean dropItem(InventoryDropView viewDragging, InventoryDropView hoveringView) {
        //TODO drop item here

        return false;
    }


    @Override
    public boolean checkHasItem() {
        return getItemSlot() != null && !getItemSlot().isEmpty();
    }

    @Override
    public Inventory_Slot getItemSlot(){
        Inventory_Slot returnSlot = new Inventory_Slot();
        switch(getId()){
            case R.id.imageView_player_item_holding:
                returnSlot =  GamePanel.getPlayer().getItemHolding();
                break;
            case R.id.imageViewPlayerToolHolding:
                returnSlot = new ToolInventorySlot(GamePanel.getPlayer().getToolHolding());
                break;
            default:
                break;
        }
        return returnSlot;
    }

    @Override
    public boolean dropItem(Item item, InventoryDropView draggingView) {
        //TODO still not working
        if(draggingView instanceof InventoryItemGridView){
            return dropInventoryItem(item, (InventoryItemGridView) draggingView);
        }else if(draggingView instanceof InventoryAlternativeDropView){
            return dropAlternativeViewItem(item, (InventoryAlternativeDropView) draggingView);
        }
        return false;
    }

    @Override
    public Rect getItemImageViewBounds() {
        return new Rect(0, 0, this.getWidth(), this.getHeight());
    }

    private boolean dropAlternativeViewItem(Item item, InventoryAlternativeDropView inventoryAlternativeDropView){
        Inventory_Slot tempInventorySlot = this.getItemSlot();
        switch(getId()){
            case R.id.imageView_player_item_holding:
                GamePanel.getPlayer().setItemHolding(inventoryAlternativeDropView.getItemSlot());
                inventoryAlternativeDropView.setItemSlot(tempInventorySlot);
                return true;
            case R.id.imageViewPlayerToolHolding:
                if(item instanceof Tool) {
                    if (item instanceof Tool) {
                        GamePanel.getPlayer().setToolHolding((Tool) item);
                        inventoryAlternativeDropView.setItemSlot(tempInventorySlot);
                        return true;
                    } else {
                        return false;
                    }
                }else{
                    return false;
                }
            default:
                return false;
        }
    }

    public void setItemSlot(Inventory_Slot itemSlot) {
        switch(getId()){
            case R.id.imageView_player_item_holding:
              GamePanel.getPlayer().setItemHolding(itemSlot);
                break;
            case R.id.imageViewPlayerToolHolding:
                GamePanel.getPlayer().setToolHolding((Tool) itemSlot.getItem());
            break;

        }
        setCurrentImage();

    }

    public boolean dropInventoryItem(Item item, InventoryItemGridView inventoryItemGridView){

        Inventory_Slot inventory_slot = inventoryItemGridView.getItemSlot();

        switch(getId()){
            case R.id.imageView_player_item_holding:

                GamePanel.getInventory().putItem(this.getItemSlot(), inventoryItemGridView.getPosition());
                GamePanel.getPlayer().setItemHolding(inventory_slot);
                setCurrentImage();
                return true;
            case R.id.imageViewPlayerToolHolding:
                if(item instanceof Tool){
                    if(this.checkHasItem()) {
                        GamePanel.getInventory().putItem(this.getItemSlot(), inventoryItemGridView.getPosition());
                    }
                    GamePanel.getPlayer().setToolHolding((Tool) item);
                    setCurrentImage();
                    return true;
                }else{
                    return false;
                }
            default:
                return false;
        }

    }



}
