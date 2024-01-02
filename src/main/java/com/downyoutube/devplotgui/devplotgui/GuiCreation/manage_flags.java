package com.downyoutube.devplotgui.devplotgui.GuiCreation;

import com.downyoutube.devplotgui.devplotgui.GuiPage.PlayerPlotPage;
import com.downyoutube.devplotgui.devplotgui.Other.ItemIcon.iconManageFlags;
import com.downyoutube.devplotgui.devplotgui.Other.ItemIcon.itemCreator;
import com.downyoutube.devplotgui.devplotgui.Other.Utils;
import com.plotsquared.core.plot.flag.GlobalFlagContainer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class manage_flags extends Utils {

    public static void guiManageFlags(Player player, int page) {
        if (!isInPlot(player)) {
            return;
        }
        if (!player.hasPermission("plots.flag")) {
            player.sendMessage(colorize(getMessage("message.no_permission")));
            return;
        }

        List<Integer> listSlot = guiListInt("gui.manage_flags.flag_icon.slot");
        int size = guiInt("gui.manage_flags.rows") * 9;
        Inventory invManageFlags = Bukkit.createInventory(player, size, colorize(guiString("gui.manage_flags.title")));
        Set<Class<?>> flagClasses = GlobalFlagContainer.getInstance().getFlagMap().keySet();
        Set<Class<?>> flagClass = new HashSet<>();
        for (Class<?> flag : flagClasses) {
            if (player.hasPermission("plots.set.flag." + getPlotFlagName(flag) + ".*") || player.hasPermission("plots.set.flag." + getPlotFlagName(flag))) {
                flagClass.add(flag);
            }
        }
        for (String key : guiKey("gui.manage_flags.item")) {
            if (guiKey("item").contains(key)) {
                for (Integer i : guiListInt("gui.manage_flags.item." + key)) {
                    if (key.equals("next") && !PlayerPlotPage.isPageValid(flagClass.size(), page + 1, listSlot.size())) {
                        break;
                    } else if (key.equals("previous") && !PlayerPlotPage.isPageValid(flagClass.size(), page - 1, listSlot.size())) {
                        break;
                    } else {
                        ItemStack item = switch (key) {
                            case ("previous"), ("next") ->
                                    itemCreator.addStrCustomNBT(itemCreator.item("item." + key, player), "page", String.valueOf(page));
                            default -> itemCreator.item("item." + key, player);
                        };
                        invManageFlags.setItem(i, item);
                    }
                }
            }
        }

        if (guiString("gui.manage_flags") != null) {
            Class<?>[] flags = flagClass.toArray(Class<?>[]::new);
            int l = ((page * listSlot.size()) - (listSlot.size() - 1)) - 1;
            for (int i = 0; i < flagClass.size() && i < listSlot.size() && l < flagClass.size(); i++) {
                invManageFlags.setItem(listSlot.get(i), iconManageFlags.iconManageFlags("gui.manage_flags.flag_icon", player, flags[l], page));
                l++;
            }
        }

        player.openInventory(invManageFlags);
    }
}
