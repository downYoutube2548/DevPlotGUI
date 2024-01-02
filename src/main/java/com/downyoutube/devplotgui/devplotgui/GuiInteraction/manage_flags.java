package com.downyoutube.devplotgui.devplotgui.GuiInteraction;

import com.downyoutube.devplotgui.devplotgui.DevPlotGUI;
import com.downyoutube.devplotgui.devplotgui.GuiCreation.plot_gui_util;
import com.downyoutube.devplotgui.devplotgui.Other.Utils;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class manage_flags extends Utils implements Listener {

    public HashMap<UUID, String> ManageFlag = new HashMap<>();

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equals(colorize(guiString("gui.manage_flags.title"))) && e.getCurrentItem() != null) {
            e.setCancelled(true);
            //e.getWhoClicked().sendMessage(this.getClass().getName());

            NBTItem nbtI = new NBTItem(e.getCurrentItem());
            String page = nbtI.getString("page");
            String flagName = nbtI.getString("flag");

            if (!flagName.equals("")) {
                String[] l = flagName.split("-");
                if (e.getClick().equals(ClickType.LEFT)) {
                    StringBuilder flagDisplay = new StringBuilder();
                    for (String a : l) {
                        flagDisplay.append(a.substring(0, 1).toUpperCase()).append(a.substring(1)).append(" ");
                    }
                    e.getWhoClicked().closeInventory();
                    ((Player) e.getWhoClicked()).sendTitle(colorize(getMessage("message.manage_flag_title").replace("%flag%", flagDisplay)), colorize(getMessage("message.manage_flag_subtitle")), 10, 10000, 20);
                    ((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5f, 1f);
                    ManageFlag.put(e.getWhoClicked().getUniqueId(), flagName + ":" + page);

                } else if (e.getClick().equals(ClickType.RIGHT)) {
                    Bukkit.getScheduler().runTask(DevPlotGUI.main, () -> {
                        ((Player) e.getWhoClicked()).performCommand("plot flag remove " + flagName);
                        com.downyoutube.devplotgui.devplotgui.GuiCreation.manage_flags.guiManageFlags((Player) e.getWhoClicked(), Integer.parseInt(page));
                    });
                }
            }
            //e.getWhoClicked().sendMessage(e.getCurrentItem().getItemMeta().getLore().toString());
            switch (nbtI.getString("GuiID")) {
                case ("next") ->
                        com.downyoutube.devplotgui.devplotgui.GuiCreation.manage_flags.guiManageFlags((Player) e.getWhoClicked(), Integer.parseInt(page) + 1);
                case ("previous") ->
                        com.downyoutube.devplotgui.devplotgui.GuiCreation.manage_flags.guiManageFlags((Player) e.getWhoClicked(), Integer.parseInt(page) - 1);
                case ("back") -> plot_gui_util.plotGUIMain((Player) e.getWhoClicked());
                case ("close") -> e.getWhoClicked().closeInventory();
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent e) {
        //e.getPlayer().sendMessage("1");
        if (ManageFlag.get(e.getPlayer().getUniqueId()) != null) {
            e.setCancelled(true);
            Player player = e.getPlayer();

            String[] temp = ManageFlag.get(player.getUniqueId()).split(":");
            String flagName = temp[0];
            int page = Integer.parseInt(temp[1]);

            //e.getPlayer().sendMessage("2");

            player.resetTitle();
            Bukkit.getScheduler().runTask(DevPlotGUI.main, () -> {
                player.performCommand("plot flag set " + flagName + " " + ChatColor.stripColor(e.getMessage()));
                com.downyoutube.devplotgui.devplotgui.GuiCreation.manage_flags.guiManageFlags(player, page);
            });

            ManageFlag.remove(e.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        clearData(e.getPlayer());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        clearData(e.getPlayer());
    }

    public void clearData(Player player) {
        if (ManageFlag.get(player.getUniqueId()) != null) {
            ManageFlag.remove(player.getUniqueId());
            player.resetTitle();
        }
    }
}
