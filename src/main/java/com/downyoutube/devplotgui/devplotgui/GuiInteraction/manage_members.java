package com.downyoutube.devplotgui.devplotgui.GuiInteraction;

import com.downyoutube.devplotgui.devplotgui.GuiCreation.ManageAdded;
import com.downyoutube.devplotgui.devplotgui.GuiCreation.plot_gui_util;
import com.downyoutube.devplotgui.devplotgui.Other.Utils;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class manage_members extends Utils implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equals(colorize(guiString("gui.manage_members.title"))) && e.getCurrentItem() != null) {
            e.setCancelled(true);
            //e.getWhoClicked().sendMessage(this.getClass().getName());

            NBTItem nbtI = new NBTItem(e.getCurrentItem());
            switch (nbtI.getString("GuiID")) {
                case ("close") -> e.getWhoClicked().closeInventory();
                case ("back") -> plot_gui_util.plotGUIMain((Player) e.getWhoClicked());

                case ("members") -> ManageAdded.guiManageAdded((Player) e.getWhoClicked(), 1, "manage_added");
                case ("trusted") -> ManageAdded.guiManageAdded((Player) e.getWhoClicked(), 1, "manage_trusted");
                case ("denied") -> ManageAdded.guiManageAdded((Player) e.getWhoClicked(), 1, "manage_denied");
            }
        }
    }

}
