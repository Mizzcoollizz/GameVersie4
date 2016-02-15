package com.thuis.gameversie2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.thuis.gameversie2.Character.MainCharacter;
import com.thuis.gameversie2.Inventory_System.Inventory;
import com.thuis.gameversie2.Inventory_System.Inventory_View.Inventory_Activity;
import com.thuis.gameversie2.Items.Raw_Materials.Rock;
import com.thuis.gameversie2.MapScreen.GameView_Activity;

import java.io.IOException;

public class Main_Menu_Activity extends AppCompatActivity {

    private static Context context = null;

    public static Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main__menu);
        GamePanel.setCurrentContext(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main__menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startNewGame(View v) {
        GamePanel.setPlayer(new MainCharacter("male", "human", "me", getContext()));
        GamePanel.setInventory(new Inventory());

        //Intent intent = new Intent(this, GameView_Activity.class);
        GamePanel.getInventory().add(new Rock());
        GamePanel.getPlayer().setItemHolding(new Rock());
        Intent intent = new Intent(this, Inventory_Activity.class);
        startActivity(intent);
    }


}
