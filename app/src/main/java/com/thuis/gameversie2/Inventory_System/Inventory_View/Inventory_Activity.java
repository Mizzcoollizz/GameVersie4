package com.thuis.gameversie2.Inventory_System.Inventory_View;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.thuis.gameversie2.GamePanel;
import com.thuis.gameversie2.R;


public class Inventory_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        ImageView imageViewPlayer = (ImageView) findViewById(R.id.imageView_Player_Inventory);
        setPlayerImage(imageViewPlayer);
        setPlayerItems();
        View v = ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0);
        v.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                //TODO Working ;)
                //TODO Just add the next drop grids :/

                return false;
            }
        });
    }

    private static void setPlayerImage(ImageView imageViewPlayer) {
        Bitmap image = Bitmap.createBitmap(GamePanel.getPlayer().getInventoryImage());
        imageViewPlayer.setImageBitmap(image);
    }

    private void setPlayerItems(){

        ImageView itemHoldingView = (ImageView) findViewById(R.id.imageView_player_item_holding);

        itemHoldingView.setImageBitmap(GamePanel.getPlayer().getItemHoldingInventoryImage());

        ImageView toolHoldingView = (ImageView) findViewById(R.id.imageViewPlayerToolHolding);

        toolHoldingView.setImageBitmap(GamePanel.getPlayer().getToolHoldingInventoryImage());

    }


}
