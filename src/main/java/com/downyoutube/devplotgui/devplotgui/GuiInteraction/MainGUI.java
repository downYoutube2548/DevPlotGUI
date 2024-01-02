package com.downyoutube.devplotgui.devplotgui.GuiInteraction;

import com.downyoutube.devplotgui.devplotgui.GuiCreation.manage_flags;
import com.downyoutube.devplotgui.devplotgui.GuiCreation.manage_members;
import com.downyoutube.devplotgui.devplotgui.GuiCreation.player_plot;
import com.downyoutube.devplotgui.devplotgui.Other.Utils;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MainGUI extends Utils implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equals(colorize(guiString("gui.main.title"))) && e.getCurrentItem() != null) {
            e.setCancelled(true);
            //e.getWhoClicked().sendMessage(this.getClass().getName());

            NBTItem nbtI = new NBTItem(e.getCurrentItem());
            switch (nbtI.getString("GuiID")) {
                case ("player_plot") -> player_plot.plotGUIPlayerPlot((Player) e.getWhoClicked(), 1);
                case ("manage_members") -> manage_members.guiManageMembers((Player) e.getWhoClicked());
                case ("manage_flags") -> manage_flags.guiManageFlags((Player) e.getWhoClicked(), 1);
                case ("close") -> e.getWhoClicked().closeInventory();
            }
        }
    }
}
