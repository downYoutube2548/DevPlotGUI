package com.downyoutube.devplotgui.devplotgui.Other.ItemIcon;

import com.downyoutube.devplotgui.devplotgui.HeadDatabaseAPIPlugin;
import com.downyoutube.devplotgui.devplotgui.Other.Placeholders.MainGUIPlaceholder;
import com.downyoutube.devplotgui.devplotgui.Other.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class itemCreator extends Utils {

    public static ItemStack item(String path, Player player, String... placeholderType) {
        if (guiString(path + ".type") == null) {
            return null;
        }
        //player.sendMessage("1");

        ItemStack item;
        if (guiString(path + ".type").equals("PLAYER_HEAD") && guiString(path + ".skin") != null && guiString(path + ".skin").startsWith("HDB:")) {
            item = HeadDatabaseAPIPlugin.hdbHead(guiString(path + ".skin").replace("HDB:", ""));
        } else {
            item = new ItemStack(Material.getMaterial(guiString(path + ".type")), guiInt(path + ".count"));
        }

        ItemMeta meta = item.getItemMeta();
        //player.sendMessage("2");

        //Set display name
        if (guiString(path + ".name") != null) {
            String s;
            if ("player_plot".equals(((placeholderType.length == 0) ? getLastKey(path, 1) : placeholderType[0]))) {
                s = MainGUIPlaceholder.PlayerPlotPlaceholder(player, colorize(guiString(path + ".name")));
            } else {
                s = colorize(guiString(path + ".name"));
            }
            meta.setDisplayName(s);
        }
        //player.sendMessage("3");

        //Set lore
        if (guiList(path + ".lores") != null) {
            List<String> l;
            if ("player_plot".equals(((placeholderType.length == 0) ? getLastKey(path, 1) : placeholderType[0]))) {
                l = MainGUIPlaceholder.PlayerPlotPlaceholderObj(player, colorizeObject(guiList(path + ".lores")));
            } else {
                l = colorizeObject(guiList(path + ".lores"));
            }
            meta.setLore(l);
        }
        //player.sendMessage("4");

        //Set flags
        if (guiList(path + ".flags") != null) {
            for (String flag : guiList(path + ".flags")) {
                meta.addItemFlags(ItemFlag.valueOf(flag));
            }
        }

        //Set custom model data
        meta.setCustomModelData(guiInt(path + ".model-data"));

        //player.sendMessage("5");

        if (guiString(path + ".type").equals("PLAYER_HEAD") && guiString(path + ".skin") != null && !guiString(path + ".skin").startsWith("HDB:")) {
            if (guiString(path + ".skin") == null) {
                item.setItemMeta(meta);
            } else if (guiString(path + ".skin").equals("%player%")) {
                SkullMeta skullMeta = (SkullMeta) meta;
                skullMeta.setOwner(player.getName());
                item.setItemMeta(skullMeta);
            } else {
                SkullMeta skullMeta = (SkullMeta) meta;
                skullMeta.setOwner(guiString(path + ".skin"));
                item.setItemMeta(skullMeta);
            }
            return addStrCustomNBT(item, "GuiID", Utils.getLastKey(path, 1));
        }
        //player.sendMessage("6");
        item.setItemMeta(meta);
        return addStrCustomNBT(item, "GuiID", Utils.getLastKey(path, 1));
    }


    /*public static String translatePlaceholder(Player player, String ItemGUI, String s) {
        switch (ItemGUI) {
            case ("player_plot"):
                return MainGUIPlaceholder.PlayerPlotPlaceholder(player, s);
            default:
                return s;
        }
    }

    public static List<String> translatePlaceholderObj(Player player, String ItemGUI, List<String> l) {
        switch (ItemGUI) {
            case ("player_plot"):
                return MainGUIPlaceholder.PlayerPlotPlaceholderObj(player, l);
            default:
                return l;
        }
    }*/
}