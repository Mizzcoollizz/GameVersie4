package com.thuis.gameversie2.MapScreen.Quick_Item_Slot_Menu;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.thuis.gameversie2.R;

/**
 * Created by Elize on 19-9-2015.
 */
public class Quick_Item_Slot_Gridview extends GridView{

    public Quick_Item_Slot_Gridview(Context context) {
        super(context);
        init();
    }

    public Quick_Item_Slot_Gridview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Quick_Item_Slot_Gridview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Quick_Item_Slot_Gridview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init(){


    }


}
