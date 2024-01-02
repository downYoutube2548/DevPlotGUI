package com.downyoutube.devplotgui.devplotgui.GuiCreation;

import com.downyoutube.devplotgui.devplotgui.GuiPage.PlayerPlotPage;
import com.downyoutube.devplotgui.devplotgui.Other.ItemIcon.iconManageAdded;
import com.downyoutube.devplotgui.devplotgui.Other.ItemIcon.itemCreator;
import com.downyoutube.devplotgui.devplotgui.Other.Utils;
import com.plotsquared.core.PlotAPI;
import com.plotsquared.core.player.PlotPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ManageAdded extends Utils {

    public static void guiManageAdded(Player player, int page, String ManageType) {
        if (!isInPlot(player)) {
            return;
        }
        guiSetItemManageAdded(player, page, "default", ManageType);

    }

    public static void guiSetItemManageAdded(Player player, int page, String type, String ManageType) {
        int size = guiInt("gui." + ManageType + ".rows") * 9;
        Inventory inv = Bukkit.createInventory(player, size, colorize(guiString("gui." + ManageType + "." + ((type.equals("remove") ? "title_remove" : "title")))));
        PlotPlayer<?> plotPlayer = new PlotAPI().wrapPlayer(player.getName());
        Set<UUID> members;
        switch (ManageType) {
            case ("manage_added"):
                members = plotPlayer.getCurrentPlot().getMembers();
                break;
            case ("manage_trusted"):
                members = plotPlayer.getCurrentPlot().getTrusted();
                break;
            case ("manage_denied"):
                members = plotPlayer.getCurrentPlot().getDenied();
                break;
            default:
                return;
        }
        List<Integer> listSlot = guiListInt("gui." + ManageType + "." + (type.equals("remove") ? "members_icon_remove" : "members_icon") + ".slot");
        for (String key : guiKey("gui." + ManageType + ".item." + type)) {
            if (guiKey("item").contains(key)) {
                for (Integer i : guiListInt("gui." + ManageType + ".item." + type + "." + key)) {

                    if (key.equals("next") && !PlayerPlotPage.isPageValid(members.size(), page + 1, listSlot.size())) {
                        break;
                    } else if (key.equals("previous") && !PlayerPlotPage.isPageValid(members.size(), page - 1, listSlot.size())) {
                        break;
                    } else {
                        ItemStack item;
                        switch (key) {
                            case ("next"):
                            case ("previous"):
                            case ("back"):
                            case ("remove_member"):
                            case ("remove_trusted"):
                            case ("remove_denied"):
                                item = itemCreator.addStrCustomNBT(itemCreator.item("item." + key, player), "page", String.valueOf(page));
                                break;
                            default:
                                item = itemCreator.item("item." + key, player);
                        }
                        inv.setItem(i, item);
                    }
                }
            }
        }
        if (guiString("gui.plot") != null) {
            UUID[] uuids = members.toArray(UUID[]::new);
            int l = ((page * listSlot.size()) - (listSlot.size() - 1)) - 1;
            for (int i = 0; i < members.size() && i < listSlot.size() && l < members.size(); i++) {
                if (!Bukkit.getOfflinePlayer(uuids[l]).hasPlayedBefore()) {
                    inv.setItem(listSlot.get(i), itemCreator.addStrCustomNBT(itemCreator.addStrCustomNBT(itemCreator.item("item.everyone", player), "username", "*"), "page", String.valueOf(page)));
                } else {
                    inv.setItem(listSlot.get(i), iconManageAdded.iconManageAdded("gui." + ManageType + "." + (type.equals("remove") ? "members_icon_remove" : "members_icon"), player, uuids[l], page));
                }
                l++;
            }
        }
        player.openInventory(inv);
    }
}
