package com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryListeners;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryAlternativeDropView;
import com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryDropView;
import com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryItemGridView;
import com.thuis.gameversie2.Inventory_System.Inventory_View.Inventory_List_Adapter;
import com.thuis.gameversie2.R;

import java.util.ArrayList;

/**
 * Created by Elize on 2-1-2016.
 */
public class OnInventoryItemDragListener2 implements View.OnDragListener {

    ViewGroup parent = null;
    GridView gridView = null;
    View draggingView = null;

    public OnInventoryItemDragListener2(ViewGroup parent, Activity activity){
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
                //TODO check this
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


    private void dropItem(int x, int y, InventoryDropView viewDragging){
        InventoryDropView hoveringView = (InventoryDropView) getHoveringView(x, y);
        if( hoveringView != null){
            if(hoveringView instanceof InventoryItemGridView && viewDragging instanceof InventoryItemGridView){
                InventoryItemGridView inventoryItemGridHoveringView = (InventoryItemGridView) hoveringView;
                InventoryItemGridView inventoryItemGridDraggingView = (InventoryItemGridView) viewDragging;
                if(inventoryItemGridHoveringView.dropItem(inventoryItemGridDraggingView, inventoryItemGridHoveringView)){
                    //TODo what if it succeeds?
                }
            }else if(hoveringView instanceof InventoryAlternativeDropView){
                if(hoveringView.dropItem(viewDragging, hoveringView)){

                 //TODO what if it succeeds?
                }
            }
        }
        }

    }



