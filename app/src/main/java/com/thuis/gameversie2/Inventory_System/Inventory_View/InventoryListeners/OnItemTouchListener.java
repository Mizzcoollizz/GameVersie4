package com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryListeners;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.thuis.gameversie2.GamePanel;
import com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryAlternativeDropView;
import com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryDropView;
import com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryItemGridView;
import com.thuis.gameversie2.Inventory_System.Inventory_View.Inventory_Activity;
import com.thuis.gameversie2.Items.Item;
import com.thuis.gameversie2.R;

/**
 * Created by Elize on 2-9-2015.
 */
public class OnItemTouchListener implements View.OnTouchListener {

    private boolean selected = false;
    ViewGroup parent = null;
    ViewGroup baseView = null;


    public OnItemTouchListener(Context context, ViewGroup parent){
        this.parent = parent;
        this.baseView = (ViewGroup)((Activity) context).findViewById(R.id.parentViewInventoryActivity);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                onItemSelect((InventoryDropView) v);
                selected = true;
                return true;
            case MotionEvent.ACTION_MOVE:
                if(selected && checkHasItem(v)){
                    View viewToDrag = v;
                    ClipData data = ClipData.newPlainText((String) viewToDrag.getTag(),
                            String.valueOf(0));
                    InventoryCustomDragShadow shadow = new InventoryCustomDragShadow(viewToDrag);
                    viewToDrag.startDrag(data, shadow, viewToDrag, 0);
                }else{
                    if(v instanceof InventoryItemGridView) {
                        GridView gridView = (GridView) parent;
                        gridView.scrollBy(0, InventoryItemGridView.getWIDTH());
                    }
                }
                return true;
            case MotionEvent.ACTION_UP:
                selected = false;
                return true;
            default:
                return false;
        }
    }

    //Check if the view is not empty.
    private boolean checkHasItem(View v) {
        if(v instanceof InventoryItemGridView) {
            InventoryItemGridView view = (InventoryItemGridView) v;
            return view.checkHasItem();
        }else if(v instanceof InventoryAlternativeDropView){
            InventoryAlternativeDropView view = (InventoryAlternativeDropView) v;
            return view.checkHasItem();
        }else{
            return false;
        }
    }

    private void onItemSelect(InventoryDropView inventoryDropView){

        View view = (View) inventoryDropView;
        View activity = view.getRootView();
        TextView nameTextView = (TextView) activity.findViewById(R.id.nameTextView_Inventory_input);
        TextView typeTextView = (TextView) activity.findViewById(R.id.typeTextView_Inventory_input);
        TextView gradeTextView = (TextView) activity.findViewById(R.id.gradeTextView_Inventory_input);
        ImageView imageView = (ImageView) activity.findViewById(R.id.ImageView_Selected_Item);
        try {
            ViewGroup gridView = (ViewGroup) activity.findViewById(R.id.inventoryGridView);
            Inventory_Activity.setAllBackgroundsTransparent(gridView);
            view.setBackgroundColor(Color.RED);
            Item item = null;
            if(inventoryDropView instanceof InventoryAlternativeDropView) {
                item = inventoryDropView.getItemSlot().getItem();
            }else if(inventoryDropView instanceof InventoryItemGridView){
                InventoryItemGridView inventoryItemGridView = (InventoryItemGridView) inventoryDropView;
                item = GamePanel.getInventory().getAllSlots().get(inventoryItemGridView.getPosition()).getItem();
            }
            if (item != null && view != null) {
                nameTextView.setText(item.getName());
                typeTextView.setText(item.getClass().getSimpleName());
                gradeTextView.setText(Integer.toString(item.getGrade()));

                Bitmap imageBitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.inventory_item_border);
                imageBitmap = Bitmap.createScaledBitmap(imageBitmap, 92, 92, false);

                imageBitmap = imageBitmap.copy(Bitmap.Config.ARGB_4444, true);
                Canvas canvas = new Canvas(imageBitmap);
                Bitmap itemImage = Bitmap.createScaledBitmap(item.getImage(), 92, 92, false);
                canvas.drawBitmap(itemImage, 0, 0, null);
                imageView.setImageBitmap(imageBitmap);
            }else{
                throw new NullPointerException();
            }
        }catch (NullPointerException ex){
            nameTextView.setText(null);
            typeTextView.setText(null);
            gradeTextView.setText(null);
            Bitmap imageBitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.inventory_item_border);
            imageBitmap = Bitmap.createScaledBitmap(imageBitmap, 92, 92, false);
            imageView.setImageBitmap(imageBitmap);
        }
    }
}
