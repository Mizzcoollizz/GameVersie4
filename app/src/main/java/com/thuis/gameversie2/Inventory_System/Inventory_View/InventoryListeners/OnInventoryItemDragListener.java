package com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryListeners;

import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryItemGridView;
import com.thuis.gameversie2.Inventory_System.Inventory_View.Inventory_List_Adapter;

/**
 * Created by Elize on 2-9-2015.
 */
public class OnInventoryItemDragListener implements View.OnDragListener{

    private ViewGroup parent = null;
    private static boolean dragging = false;
    private static InventoryItemGridView viewDragging = null;
    private GridView parentGridView = null;

    public OnInventoryItemDragListener(ViewGroup parent) {
        this.parent = parent;
        this.parentGridView = (GridView) parent;
    }

    public static boolean isDragging() {
        return dragging;
    }


    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()){
            case DragEvent.ACTION_DRAG_STARTED:
                dragging = true;
                return true;
            case DragEvent.ACTION_DRAG_LOCATION:
                colorItems((int) event.getX(), (int) event.getY());
                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                dragging = false;
                return true;
            case DragEvent.ACTION_DROP:
                dragging = false;
                dropItem((int)event.getX(), (int)event.getY());
                Inventory_List_Adapter adapter = (Inventory_List_Adapter) parentGridView.getAdapter();
                adapter.notifyDataSetChanged();
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);
                parent.getRootView().startDrag(event.getClipData(), shadow, null, 0);
            default:
                return false;

        }
    }

    private void dropItem(int x, int y) {
        InventoryItemGridView view = (InventoryItemGridView) checkHoverItems(x,y);
        if( view != null){
            if(view.dropItem(viewDragging, view)){
            }


        }else{
                //TODO Drop in alternative thingy
        }
    }

    private boolean getAlternativeDropView(int x, int y){
        //TODO check if dropped in alternative view
        return false;
    }

    private void colorItems(int x, int y){

        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            view.setBackgroundColor(Color.TRANSPARENT);
        }
        if (dragging){
            View view = checkHoverItems(x,y);
            if(view != null){
                view.setBackgroundColor(Color.YELLOW);
            }
        }

    }

    private View checkHoverItems(int x, int y) {

            for (int i = 0; i < parent.getChildCount(); i++) {
                View view = parent.getChildAt(i);
                Rect rect = new Rect(view.getLeft() + 1, view.getTop() + 1, view.getRight() - 1, view.getBottom() - 1);
                if (rect.contains(x, y)) {
                        return view;
                }
            }
            return null;
    }


    public static void setViewDragging(View _viewDragging) {
       viewDragging = (InventoryItemGridView) _viewDragging;
    }
}
