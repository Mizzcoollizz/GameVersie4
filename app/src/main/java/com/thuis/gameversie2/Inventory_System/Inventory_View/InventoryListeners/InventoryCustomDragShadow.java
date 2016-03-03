package com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryListeners;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import com.thuis.gameversie2.GamePanel;
import com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryDropView;

/**
 * Created by Elize on 29-2-2016.
 */
public class InventoryCustomDragShadow extends View.DragShadowBuilder {

    private Drawable shadow = null;

    public InventoryCustomDragShadow(View view) {
        super();
        if(view instanceof InventoryDropView){
            InventoryDropView inventoryDropView = (InventoryDropView) view;
            Bitmap bitmap = Bitmap.createScaledBitmap(inventoryDropView.getItemSlot().getItem().getImage(), view.getWidth(), view.getHeight(), false);
            shadow = new BitmapDrawable(GamePanel.getCurrentContext().getResources(), bitmap );
            shadow.setBounds(inventoryDropView.getItemImageViewBounds());
        }

    }

    @Override
    public void onDrawShadow(Canvas canvas) {
        shadow.draw(canvas);

    }

    @Override
    public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
        shadowSize.x = shadow.getMinimumWidth();
        shadowSize.y = shadow.getMinimumHeight();

        shadowTouchPoint.x = (int)(shadowSize.x / 2);
        shadowTouchPoint.y = (int)(shadowSize.y / 2);

    }
}
