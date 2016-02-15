package com.thuis.gameversie2.Inventory_System.Inventory_View;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thuis.gameversie2.GamePanel;
import com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryListeners.OnItemTouchListener;
import com.thuis.gameversie2.Items.Item;
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
        Item item = getItem();
        if(item != null) {
            Log.i("Image: ", item.getInventoryImage().toString());
            this.setImageBitmap(item.getInventoryImage());
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
    public void onItemSelect() {
        View view = (View) this;

        View activity = getRootView();
        TextView nameTextView = (TextView) activity.findViewById(R.id.nameTextView_Inventory_input);
        TextView typeTextView = (TextView) activity.findViewById(R.id.typeTextView_Inventory_input);
        TextView gradeTextView = (TextView) activity.findViewById(R.id.gradeTextView_Inventory_input);
        ImageView imageView = (ImageView) activity.findViewById(R.id.ImageView_Selected_Item);
        try {

            ViewGroup gridView = (ViewGroup) activity.findViewById(R.id.inventoryGridView);
            //When the user clicks on an item, the item background will be green.
            //The other elements have a transparent background.
            for(int i = 0; i < gridView.getChildCount(); i++){
                gridView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
            }

            view.setBackgroundColor(Color.RED);

            Item item = getItem();

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

    @Override
    public boolean checkHasItem() {
        return getItem() != null;
    }

    private Item getItem(){
        switch(getId()){
            case R.id.imageView_player_item_holding:
                return GamePanel.getPlayer().getItemHolding();
            case R.id.imageViewPlayerToolHolding:
                return GamePanel.getPlayer().getToolHolding();
            default:
                return null;
        }

    }
}
