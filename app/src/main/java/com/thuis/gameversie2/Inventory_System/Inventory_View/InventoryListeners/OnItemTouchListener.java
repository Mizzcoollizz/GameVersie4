package com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryListeners;

import android.app.Activity;
import android.content.ClipData;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.thuis.gameversie2.GamePanel;
import com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryItemGridView;
import com.thuis.gameversie2.Items.Item;
import com.thuis.gameversie2.R;

/**
 * Created by Elize on 2-9-2015.
 */
public class OnItemTouchListener implements View.OnTouchListener {

    private boolean selected = false;
    ViewGroup parent = null;

    public OnItemTouchListener(ViewGroup parent){
        this.parent = parent;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                onItemSelect((InventoryItemGridView) v);
                selected = true;
                return true;
            case MotionEvent.ACTION_MOVE:
                if(selected && checkItem(v)){
                    ClipData data = ClipData.newPlainText((String) v.getTag(),
                            String.valueOf(0));
                    View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);
                    parent.startDrag(data, shadow, null, 0);
                    OnInventoryItemDragListener.setViewDragging(v);
                }else{
                    GridView gridView = (GridView) parent;
                    gridView.scrollBy(0, InventoryItemGridView.getWIDTH());

                }
                return true;
            case MotionEvent.ACTION_UP:
                selected = false;
                return true;
            default:
                return false;
        }
    }

    private boolean checkItem(View v) {
        InventoryItemGridView view = (InventoryItemGridView) v;
        try{
            if(GamePanel.getInventory().getAllSlots().get(view.getPosition()).getAmount() > 0){
                return true;
            }else{
                return false;
            }

        }catch (NullPointerException ex){
            return false;
        }
    }

    private void onItemSelect(InventoryItemGridView view){

        view.setBackgroundColor(Color.GREEN);
        View activity = parent.getRootView();
        int position = view.getPosition();

        TextView nameTextView = (TextView) activity.findViewById(R.id.nameTextView_Inventory_input);
        TextView typeTextView = (TextView) activity.findViewById(R.id.typeTextView_Inventory_input);
        TextView gradeTextView = (TextView) activity.findViewById(R.id.gradeTextView_Inventory_input);
        ImageView imageView = (ImageView) activity.findViewById(R.id.ImageView_Selected_Item);
        try {
            //When the user clicks on an item, the item background will be green.
            //The other elements have a transparant background.
            for(int i = 0; i < parent.getChildCount(); i++){
                parent.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
            }
            view.setBackgroundColor(Color.GREEN);

            Item item = GamePanel.getInventory().getAllSlots().get(position).getItem();

            if (item != null && !item.equals(null) && view != null) {
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
            ex.printStackTrace();
            nameTextView.setText(null);
            typeTextView.setText(null);
            gradeTextView.setText(null);
            Bitmap imageBitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.inventory_item_border);
            imageBitmap = Bitmap.createScaledBitmap(imageBitmap, 92, 92, false);
            imageView.setImageBitmap(imageBitmap);
        }
    }







}
