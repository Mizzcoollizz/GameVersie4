package com.thuis.gameversie2.MapScreen;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.thuis.gameversie2.Character.MainCharacter;
import com.thuis.gameversie2.GamePanel;
import com.thuis.gameversie2.Inventory_System.Inventory;
import com.thuis.gameversie2.Inventory_System.Inventory_View.Inventory_Activity;
import com.thuis.gameversie2.Map.MapHandler;
import com.thuis.gameversie2.MapScreen.Quick_Item_Slot_Menu.Quick_Item_Slot_Menu_Fragment;
import com.thuis.gameversie2.R;


public class GameView_Activity extends Activity {

    private static MovePlayerButton leftBtn = null;
    private static MovePlayerButton rightBtn = null;
    private static MovePlayerButton upBtn = null;
    private static MovePlayerButton downBtn = null;
    static Context context = null;
    private Quick_Item_Slot_Menu_Fragment fragment = null;
    private boolean quick_item_slot_menu_expanded = false;

    public static Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameview_activity);
        context = this;
        GamePanel.setCurrentContext(this);

        MapHandler.setFarm();
    }

    public static Button getLeftBtn() {
        return leftBtn;
    }

    public static Button getRightBtn() {
        return rightBtn;
    }

    public static Button getUpBtn() {
        return upBtn;
    }

    public static Button getDownBtn() {
        return downBtn;
    }

    private void setButtons() {
        leftBtn = (MovePlayerButton) findViewById(R.id.leftBTN);
        leftBtn.setOnTouchListener(new MovePlayerButtonListener(leftBtn));

        rightBtn = (MovePlayerButton) findViewById(R.id.rightBTN);
        rightBtn.setOnTouchListener(new MovePlayerButtonListener(rightBtn));

        upBtn = (MovePlayerButton) findViewById(R.id.upBTN);
        upBtn.setOnTouchListener(new MovePlayerButtonListener(upBtn));

        downBtn = (MovePlayerButton) findViewById(R.id.downBTN);
        downBtn.setOnTouchListener(new MovePlayerButtonListener(downBtn));
    }



//  Combat buttons! DO NOT TOUCH!!
    public void DefenceButtonOnClick(View view) {
        System.out.print("niet zeuren hier werk ik nog aan!");
    }
// MiniMap
    public void GoTo_MiniMap(View view){
        setContentView(R.layout.minimap_eljakim);

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        setButtons();
        GamePanel.setCurrentContext(this);
    }

    public void goToInventory(View view){
        Intent intent = new Intent(this, Inventory_Activity.class);
        startActivity(intent);
    }

    public void onQuickItemMenuClick(View view){

        if(fragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragment = new Quick_Item_Slot_Menu_Fragment();
            fragmentTransaction.add(R.id.quick_item_slot_RelativeLayout, fragment, "Quick_Item_Slot_Menu_Fragment");
            fragmentTransaction.commit();
            quick_item_slot_menu_expanded = true;
        }else{
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
            fragment = null;
            quick_item_slot_menu_expanded = false;

        }


    }
}
