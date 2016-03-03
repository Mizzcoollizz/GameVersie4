package com.thuis.gameversie2.Inventory_System.Inventory_View;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.thuis.gameversie2.GamePanel;
import com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryListeners.OnInventoryItemDragListener;
import com.thuis.gameversie2.Items.Item;
import com.thuis.gameversie2.R;


public class Inventory_Activity extends Activity {

    private static ImageView toolHoldingView = null;
    private static ImageView itemHoldingView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GamePanel.setCurrentContext(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        ImageView imageViewPlayer = (ImageView) findViewById(R.id.imageView_Player_Inventory);
        setPlayerImage(imageViewPlayer);
        setPlayerItems();
        View v = ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0);
        GridView gridView = (GridView) findViewById(R.id.inventoryGridView);
        View baseView = findViewById(R.id.parentViewInventoryActivity);
        baseView.setOnDragListener(new OnInventoryItemDragListener((ViewGroup) baseView, this));

        View parentView = findViewById(R.id.parentViewInventoryActivity);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        GamePanel.setCurrentContext(this);
    }

    public static ImageView getToolHoldingView() {
        return toolHoldingView;
    }

    public static ImageView getItemHoldingView() {
        return itemHoldingView;
    }

    private static void setPlayerImage(ImageView imageViewPlayer) {
        Bitmap image = Bitmap.createBitmap(GamePanel.getPlayer().getInventoryImage());
        imageViewPlayer.setImageBitmap(image);
    }

    private void setPlayerItems(){

        itemHoldingView = (ImageView) findViewById(R.id.imageView_player_item_holding);

        itemHoldingView.setImageBitmap(GamePanel.getPlayer().getItemHoldingInventoryImage());

        toolHoldingView = (ImageView) findViewById(R.id.imageViewPlayerToolHolding);

        toolHoldingView.setImageBitmap(GamePanel.getPlayer().getToolHoldingInventoryImage());

    }

    public static void setAllBackgroundsTransparent(ViewGroup gridView){
        //When the user clicks on an item, the item background will be green.
        //The other elements have a transparent background.
      for(int i = 0; i < gridView.getChildCount(); i++){
        gridView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
        }

        itemHoldingView.setBackgroundColor(Color.TRANSPARENT);
        toolHoldingView.setBackgroundColor(Color.TRANSPARENT);
        }

    public static void onInventoryItemSelect(InventoryDropView inventoryDropView){

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
                item = ((InventoryAlternativeDropView)inventoryDropView).getItemSlot().getItem();
            }else if(inventoryDropView instanceof InventoryItemGridView){
                InventoryItemGridView inventoryItemGridView = (InventoryItemGridView) inventoryDropView;
                item = GamePanel.getInventory().getAllSlots().get(inventoryItemGridView.getPosition()).getItem();
            }

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
