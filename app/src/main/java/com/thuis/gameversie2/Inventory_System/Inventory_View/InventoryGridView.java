package com.thuis.gameversie2.Inventory_System.Inventory_View;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Elize on 1-9-2015.
 */
public class InventoryGridView extends GridView{

    public InventoryGridView(Context context) {
        super(context);
        if(!isInEditMode()){
            init(context);
        }
    }

    public InventoryGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode()) {
            init(context);
        }
    }

    public InventoryGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(!isInEditMode()) {
            init(context);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public InventoryGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        if(!isInEditMode()) {
            init(context);
        }
    }

    private void init(Context context){
        setAdapter(new Inventory_List_Adapter(context));
    }

}
