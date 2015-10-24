package com.thuis.gameversie2.Inventory_System.Inventory_View.InventoryListeners;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.thuis.gameversie2.GamePanel;
import com.thuis.gameversie2.Items.Item;
import com.thuis.gameversie2.R;

/**
 * Created by Elize on 25-8-2015.
 */
public class ItemSelectListener implements AdapterView.OnItemClickListener {

    Activity activity = null;

    public ItemSelectListener(Activity activity) {
        this.activity = activity;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
                typeTextView.setText(item.getType());
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
