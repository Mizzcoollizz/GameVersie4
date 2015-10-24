package com.thuis.gameversie2.MapScreen;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Elize on 24-10-2015.
 */
public class MovePlayerButton extends Button {

    private boolean pressed = false;

    public MovePlayerButton(Context context) {
        super(context);
    }

    public MovePlayerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MovePlayerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MovePlayerButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isPressed() {
        return pressed;
    }

    @Override
    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }
}
