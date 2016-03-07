package com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryListeners;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.thuis.gameversie2.GamePanel;
import com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryDropView;
import com.thuis.gameversie2.Items.Item;
import com.thuis.gameversie2.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Elize on 2-1-2016.
 */
public class OnInventoryItemDragListener implements View.OnDragListener {

    ViewGroup parent = null;
    GridView gridView = null;

    public OnInventoryItemDragListener(ViewGroup parent, Activity activity){
        this.parent = parent;
        gridView = (GridView) activity.findViewById(R.id.inventoryGridView);
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()){
            case DragEvent.ACTION_DRAG_STARTED:
                gridView.invalidateViews();
                ((View) event.getLocalState()).setBackgroundColor(Color.RED);
                return true;
            case DragEvent.ACTION_DRAG_LOCATION:
                colorItems((int)event.getX(), (int)event.getY());
                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                return true;
            case DragEvent.ACTION_DROP:
                dropItem((int) event.getX(), (int) event.getY(), (InventoryDropView) event.getLocalState());
                gridView.invalidateViews();
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                return true;
            default:
                return false;
        }
    }

    private View getHoveringView(int x, int y){
        for(View view: getAllViews(parent, null)) {
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            Rect rect = new Rect(location[0], location[1], location[0] + view.getWidth(), location[1] + view.getHeight());
            if(rect.contains(x, y)){
                return view;
            }
        }
        return null;
    }

    private void colorItems(int x, int y){
        removeAllColoredItems();
        View view = getHoveringView(x, y);
        if(view != null){
            view.setBackgroundColor(Color.GREEN);
        }

    }

    private ArrayList<View> getAllViews(View view, ArrayList<View> viewArrayList){
        if(viewArrayList == null) {
            viewArrayList = new ArrayList<>();
        }

        if(view instanceof InventoryDropView) {
            viewArrayList.add(view);
        }else if(view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (viewGroup.getChildCount() > 0) {
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    getAllViews(viewGroup.getChildAt(i), viewArrayList);
                }
            }
        }
        return viewArrayList;
    }

    private void removeAllColoredItems(){
            for(View view: getAllViews(parent, null)) {
                view.setBackgroundColor(Color.TRANSPARENT);
            }
        }


    /**
     * If the item in the slot is equal to the one hovering, the itemslots should be merged.
     * @param x
     * @param y
     * @param viewDragging
     */
    private void dropItem(int x, int y, InventoryDropView viewDragging){
        InventoryDropView hoveringView = (InventoryDropView) getHoveringView(x, y);
        if(hoveringView != null){
            Item draggingItem = viewDragging.getItemSlot().getItem();

            if(hoveringView.getItemSlot() != null){
                Item itemInDropSlot = hoveringView.getItemSlot().getItem();
                if(draggingItem.isEqualItem(itemInDropSlot)){
                    GamePanel.getInventory().mergeInventorySlots(viewDragging.getItemSlot(), hoveringView.getItemSlot());
                }else {
                    if(hoveringView.dropItem(draggingItem, viewDragging)){

                    }
                }
            }

        }
        }

    }



