package com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryListeners;

import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryAlternativeDropView;
import com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryDropView;
import com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryItemGridView;
import com.thuis.gameversie2.Inventory_System.Inventory_View.Inventory_List_Adapter;

/**
 * Created by Elize on 2-9-2015.
 */
public class OnInventoryItemDragListener implements View.OnDragListener{

    private ViewGroup parent = null;
    private static boolean dragging = false;
    private static InventoryDropView viewDragging = null;
    private GridView parentGridView = null;
    private Rect parentRect = null;

    public OnInventoryItemDragListener(ViewGroup gridview, ViewGroup parent) {
        this.parent = parent;
        this.parentGridView = (GridView) gridview;
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
                removeAllColoredItems();
                return true;
            case DragEvent.ACTION_DROP:
                dragging = false;
                dropItem((int)event.getX(), (int)event.getY());
                Inventory_List_Adapter adapter = (Inventory_List_Adapter) parentGridView.getAdapter();
                adapter.notifyDataSetChanged();
                removeAllColoredItems();
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);
                parent.getRootView().startDrag(event.getClipData(), shadow, null, 0);
                removeAllColoredItems();
                System.out.println("EXITED");
                return true;
            default:
                return false;
        }
    }

    private void dropItem(int x, int y) {
        InventoryItemGridView view = (InventoryItemGridView) checkHoverItems(x,y, parent);
        if( view != null){
            if(view.dropItem(viewDragging, view)){
            }


        }else{


                //TODO Drop in alternative thingy
           // getAlternativeDropView(x, y);
        }
    }

    private View getAlternativeDropView(int x, int y){
        //TODO check if dropped in alternative view
        //InventoryAlternativeDropView
        return null;
    }

    private void removeAllColoredItems(){
        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            if(view instanceof GridView){
                GridView gridView = (GridView) view;
                for(int j = 0; j < gridView.getChildCount(); j++){
                    gridView.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);
                }
            }
            view.setBackgroundColor(Color.TRANSPARENT);
        }
    }


    private void colorItems(int x, int y){
        removeAllColoredItems();


        if (dragging){
            View view = checkHoverItems(x,y, parent);
            if(view != null){
                view.setBackgroundColor(Color.YELLOW);
            }
        }

    }

    private View getHoveringInventoryDropView(View view, int x, int y){

        if(view instanceof InventoryDropView) {

            //Rect rect = new Rect((int)view.getX(), (int) view.getY(), (int) view.getX() + view.getWidth(), (int) view.getY() + view.getHeight());
            Rect rect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            if (rect.contains(x, y)) {
                if (view instanceof InventoryAlternativeDropView) {
                    System.out.println("ALTERNATIVE VIEW!!!!!!!!!!!!!!!!!!!!!!!!!");
                }
                System.out.println("FoundView");
                return view;
            }
        }
        return null;
    }

    private View checkHoverItems(int x, int y, ViewGroup parent) {

//        //TODO Make object drop in alternative view
            for (int i = 0; i < parent.getChildCount(); i++) {
                View view = parent.getChildAt(i);

//                if(view instanceof ViewGroup){
//                    ViewGroup gridView = (ViewGroup) view;
//                    if(gridView.getChildCount() > 0 && !(view instanceof InventoryDropView)){
//                        checkHoverItems(x, y, gridView);
//                    }else{
//                        View returnview = getHoveringInventoryDropView(gridView, x, y);
//                        if(returnview != null){
//                            return returnview;
//                        }
//                    }
//                }else{
//                    View returnview = getHoveringInventoryDropView(view, x, y);
//                    if(returnview != null){
//                        return returnview;
//
//                    }
//                }

                if(view instanceof GridView){
                    GridView gridView = (GridView) view;
                    for(int j = 0; j < gridView.getChildCount(); j++){
                        View returnView = getHoveringInventoryDropView(gridView.getChildAt(j), x, y);
                        if(returnView != null){
                            return returnView;
                        }
                    }
                }else{
                    View returnView = getHoveringInventoryDropView(view, x, y);
                    if(returnView != null){
                        return returnView;
                    }
                }

//                int[] location = new int[2];
//                view.getLocationOnScreen(location);
//                int xs = location[0];
//                int ys = location[1];
//               // Rect rect = new Rect(xs, ys, xs + view.getWidth(), ys + view.getHeight());
//                Rect rect = new Rect((int)view.getX(), (int) view.getY(), (int) view.getX() + view.getWidth(), (int) view.getY() + view.getHeight());

//
//                if (rect.contains(x, y)) {
//                    if (view instanceof InventoryAlternativeDropView) {
//                        System.out.println("ALTERNATIVE VIEW!!!!!!!!!!!!!!!!!!!!!!!!!");
//                    }
//
//                    System.out.println("FoundView");
//                    return view;
//                }
//
//
            }

            return null;
    }


    public static void setViewDragging(View _viewDragging) {
       viewDragging = (InventoryDropView) _viewDragging;
    }
}
