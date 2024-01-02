package com.downyoutube.devplotgui.devplotgui.GuiCreation;

import com.downyoutube.devplotgui.devplotgui.Other.ItemIcon.itemCreator;
import com.downyoutube.devplotgui.devplotgui.Other.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class plot_gui_util extends Utils {

    public static void plotGUIMain(Player player) {
        int size = guiInt("gui.main.rows") * 9;
        Inventory MainInv = Bukkit.createInventory(player, size, colorize(guiString("gui.main.title")));
        for (String key : guiKey("gui.main.item")) {
            if (guiKey("item").contains(key)) {
                for (Integer i : guiListInt("gui.main.item." + key)) {
                    MainInv.setItem(i, itemCreator.item("item." + key, player));
                }
            }
        }

        player.openInventory(MainInv);
    }

}
