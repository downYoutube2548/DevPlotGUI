package com.downyoutube.devplotgui.devplotgui.GuiInteraction;

import com.downyoutube.devplotgui.devplotgui.GuiCreation.player_plot;
import com.downyoutube.devplotgui.devplotgui.GuiCreation.plot_gui_util;
import com.downyoutube.devplotgui.devplotgui.Other.Utils;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlayerPlotGUI extends Utils implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equals(colorize(guiString("gui.plot.title"))) && e.getCurrentItem() != null) {
            e.setCancelled(true);
            //e.getWhoClicked().sendMessage(this.getClass().getName());

            NBTItem nbtI = new NBTItem(e.getCurrentItem());
            String id = nbtI.getString("PlotID");
            String world = nbtI.getString("world");
            String gui = nbtI.getString("GuiID");
            String page = nbtI.getString("page");
            if (!(id.equals("") && world.equals(""))) {
                ((Player) e.getWhoClicked()).performCommand("plot home " + world + " " + id);
                return;
            }
            switch (gui) {
                case ("next") ->
                        player_plot.plotGUIPlayerPlot((Player) e.getWhoClicked(), (Integer.parseInt(page)) + 1);
                case ("previous") ->
                        player_plot.plotGUIPlayerPlot((Player) e.getWhoClicked(), (Integer.parseInt(page)) - 1);
                case ("claimplot") -> ((Player) e.getWhoClicked()).performCommand("plot auto");
                case ("close") -> e.getWhoClicked().closeInventory();
                case ("back") -> plot_gui_util.plotGUIMain((Player) e.getWhoClicked());
            }
        }
    }
}
