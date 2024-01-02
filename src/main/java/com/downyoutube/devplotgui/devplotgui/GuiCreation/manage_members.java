package com.downyoutube.devplotgui.devplotgui.GuiCreation;

import com.downyoutube.devplotgui.devplotgui.Other.ItemIcon.iconManageMembers;
import com.downyoutube.devplotgui.devplotgui.Other.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class manage_members extends Utils {

    public static void guiManageMembers(Player player) {
        if (!isInPlot(player)) {
            return;
        }

        int size = guiInt("gui.manage_members.rows") * 9;
        Inventory invManageMembers = Bukkit.createInventory(player, size, colorize(guiString("gui.manage_members.title")));
        for (String key : guiKey("gui.manage_members.item")) {
            if (guiKey("item").contains(key)) {
                for (Integer i : guiListInt("gui.manage_members.item." + key)) {
                    invManageMembers.setItem(i, iconManageMembers.iconManageMembers(player, "item." + key));
                }
            }
        }

        player.openInventory(invManageMembers);
    }
}
