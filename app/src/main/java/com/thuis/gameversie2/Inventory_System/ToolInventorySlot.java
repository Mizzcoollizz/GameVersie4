package com.thuis.gameversie2.Inventory_System;

import com.thuis.gameversie2.Items.Item;
import com.thuis.gameversie2.Items.Tools.Tool;

/**
 * Created by Elize on 29-2-2016.
 */
public class ToolInventorySlot extends Inventory_Slot {

    Tool tool = null;

    public ToolInventorySlot(Item firstItem) {
        this.tool = (Tool) firstItem;
    }

    @Override
    public boolean isEqualItem(Item _item) {
        return _item.equals(this.tool);
    }

    @Override
    public boolean hasRoom() {
        return tool == null;
    }

    @Override
    public void add(Item item) {
        this.tool = (Tool) item;
    }

    @Override
    public Item getItem() {
        return tool;
    }

    @Override
    public int getAmount() {
        return tool != null ? 1 : 0;
    }
}
