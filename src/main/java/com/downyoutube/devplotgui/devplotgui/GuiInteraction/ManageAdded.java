package com.downyoutube.devplotgui.devplotgui.GuiInteraction;

import com.downyoutube.devplotgui.devplotgui.DevPlotGUI;
import com.downyoutube.devplotgui.devplotgui.GuiCreation.manage_members;
import com.downyoutube.devplotgui.devplotgui.Other.Utils;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class ManageAdded extends Utils implements Listener {

    public static HashMap<UUID, Boolean> AddMember = new HashMap<>();
    public static HashMap<UUID, Boolean> AddTrusted = new HashMap<>();
    public static HashMap<UUID, Boolean> AddDenied = new HashMap<>();

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        String path;

        if (title.equals(colorize(guiString("gui.manage_added.title")))) {
            path = "gui.manage_added.title";
        } else if (title.equals(colorize(guiString("gui.manage_added.title_remove")))) {
            path = "gui.manage_added.title_remove";
        } else if (title.equals(colorize(guiString("gui.manage_trusted.title")))) {
            path = "gui.manage_trusted.title";
        } else if (title.equals(colorize(guiString("gui.manage_trusted.title_remove")))) {
            path = "gui.manage_trusted.title_remove";
        } else if (title.equals(colorize(guiString("gui.manage_denied.title")))) {
            path = "gui.manage_denied.title";
        } else if (title.equals(colorize(guiString("gui.manage_denied.title_remove")))) {
            path = "gui.manage_denied.title_remove";
        } else {
            return;
        }
        //e.getWhoClicked().sendMessage(this.getClass().getName());

        if (e.getCurrentItem() != null) {
            e.setCancelled(true);
            String ManageType = getLastKey(path, 2);
            String ManageName = switch (ManageType) {
                case ("manage_added") -> "member";
                case ("manage_trusted") -> "trusted";
                case ("manage_denied") -> "denied";
                default -> "";
            };

            NBTItem nbtI = new NBTItem(e.getCurrentItem());
            String page = nbtI.getString("page");
            String username = nbtI.getString("username");
            if (!username.equals("")) {
                if ((title.equals(colorize(guiString("gui." + ManageType + ".title_remove"))))) {
                    if (username.equals("*")) {
                        ((Player) e.getWhoClicked()).performCommand("plot remove *");
                        com.downyoutube.devplotgui.devplotgui.GuiCreation.ManageAdded.guiSetItemManageAdded((Player) e.getWhoClicked(), Integer.parseInt(page), "remove", ManageType);
                    } else {
                        ((Player) e.getWhoClicked()).performCommand("plot remove " + username);
                        com.downyoutube.devplotgui.devplotgui.GuiCreation.ManageAdded.guiSetItemManageAdded((Player) e.getWhoClicked(), Integer.parseInt(page), "remove", ManageType);
                    }
                }
            }
            switch (nbtI.getString("GuiID")) {
                case ("close"):
                    e.getWhoClicked().closeInventory();
                    break;
                case ("back"):
                    if ((title.equals(colorize(guiString("gui." + ManageType + ".title_remove"))))) {
                        com.downyoutube.devplotgui.devplotgui.GuiCreation.ManageAdded.guiSetItemManageAdded((Player) e.getWhoClicked(), Integer.parseInt(page), "default", ManageType);
                    } else {
                        manage_members.guiManageMembers((Player) e.getWhoClicked());
                    }
                    break;
                case ("add_trusted"):
                case ("add_denied"):
                case ("add_member"):
                    e.getWhoClicked().closeInventory();
                    ((Player) e.getWhoClicked()).sendTitle(colorize(getMessage("message.add_" + ManageName + "_title")), colorize(getMessage("message.add_" + ManageName + "_subtitle")), 10, 10000, 20);
                    ((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5f, 1f);
                    switch (ManageType) {
                        case ("manage_added") -> AddMember.put(e.getWhoClicked().getUniqueId(), true);
                        case ("manage_trusted") -> AddTrusted.put(e.getWhoClicked().getUniqueId(), true);
                        case ("manage_denied") -> AddDenied.put(e.getWhoClicked().getUniqueId(), true);
                    }
                    break;
                case ("remove_trusted"):
                case ("remove_denied"):
                case ("remove_member"):
                    com.downyoutube.devplotgui.devplotgui.GuiCreation.ManageAdded.guiSetItemManageAdded((Player) e.getWhoClicked(), Integer.parseInt(page), "remove", ManageType);
                    break;
            }

        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent e) {
        String ManageType;
        if (AddMember.get(e.getPlayer().getUniqueId()) != null) {
            AddMember.remove(e.getPlayer().getUniqueId());
            ManageType = "manage_added";
        } else if (AddTrusted.get(e.getPlayer().getUniqueId()) != null) {
            AddTrusted.remove(e.getPlayer().getUniqueId());
            ManageType = "manage_trusted";
        } else if (AddDenied.get(e.getPlayer().getUniqueId()) != null) {
            AddDenied.remove(e.getPlayer().getUniqueId());
            ManageType = "manage_denied";
        } else {
            return;
        }

        Player player = e.getPlayer();
        OfflinePlayer target = Bukkit.getOfflinePlayer(ChatColor.stripColor(e.getMessage()));

        String ManageCmd = switch (ManageType) {
            case ("manage_added") -> "add";
            case ("manage_trusted") -> "trust";
            case ("manage_denied") -> "deny";
            default -> "";
        };

        player.resetTitle();

        e.setCancelled(true);

        if (e.getMessage().equals("cancel")) {
            Bukkit.getScheduler().runTask(DevPlotGUI.main, () -> com.downyoutube.devplotgui.devplotgui.GuiCreation.ManageAdded.guiManageAdded(e.getPlayer(), 1, ManageType));
            return;
        }

        if (!isInPlot(player)) {
            Bukkit.getScheduler().runTask(DevPlotGUI.main, () -> com.downyoutube.devplotgui.devplotgui.GuiCreation.ManageAdded.guiManageAdded(e.getPlayer(), 1, ManageType));
            return;
        }
        if (!target.hasPlayedBefore()) {
            if (e.getMessage().equals("*") || e.getMessage().equalsIgnoreCase("Everyone")) {
                Bukkit.getScheduler().runTask(DevPlotGUI.main, () -> {
                    player.performCommand("plot " + ManageCmd + " *");
                    com.downyoutube.devplotgui.devplotgui.GuiCreation.ManageAdded.guiManageAdded(e.getPlayer(), 1, ManageType);
                });
            } else {
                player.sendMessage(colorize(getMessage("message.null_player")));
                Bukkit.getScheduler().runTask(DevPlotGUI.main, () -> com.downyoutube.devplotgui.devplotgui.GuiCreation.ManageAdded.guiManageAdded(e.getPlayer(), 1, ManageType));
            }
            return;
        }
        Bukkit.getScheduler().runTask(DevPlotGUI.main, () -> {
            player.performCommand("plot " + ManageCmd + " " + ChatColor.stripColor(e.getMessage()));
            com.downyoutube.devplotgui.devplotgui.GuiCreation.ManageAdded.guiManageAdded(e.getPlayer(), 1, ManageType);
        });


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
        if (AddMember.get(player.getUniqueId()) != null) {
            AddMember.remove(player.getUniqueId());
            player.resetTitle();
        }
        if (AddTrusted.get(player.getUniqueId()) != null) {
            AddTrusted.remove(player.getUniqueId());
            player.resetTitle();
        }
        if (AddDenied.get(player.getUniqueId()) != null) {
            AddDenied.remove(player.getUniqueId());
            player.resetTitle();
        }
    }
}
