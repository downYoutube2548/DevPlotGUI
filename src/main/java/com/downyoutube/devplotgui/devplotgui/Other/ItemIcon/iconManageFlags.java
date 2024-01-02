package com.downyoutube.devplotgui.devplotgui.Other.ItemIcon;

import com.downyoutube.devplotgui.devplotgui.HeadDatabaseAPIPlugin;
import com.downyoutube.devplotgui.devplotgui.Other.Placeholders.placeholderManageFlags;
import com.downyoutube.devplotgui.devplotgui.Other.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class iconManageFlags extends Utils {
    public static ItemStack iconManageFlags(String path, Player player, Class<?> plotFlag, int page) {
        if (guiString(path + ".type") == null) {
            return null;
        }

        String ItemType;
        String flagName = getPlotFlagName(plotFlag);
        String[] strings = new String[0];
        if (guiString("flags_setting_icon." + flagName) != null) {
            strings = guiString("flags_setting_icon." + flagName).split(":");
            ItemType = strings[0];
        } else {
            ItemType = guiString(path + ".type");
        }

        if (Material.getMaterial(ItemType) == null) {
            player.sendMessage(ChatColor.RED + "null: " + flagName);
        }
        //player.sendMessage("1");
        ItemStack item;
        if (ItemType.equals("PLAYER_HEAD") && ((guiString("flags_setting_icon." + flagName) != null) ? (strings.length > 1 && strings[1] != null && strings[2] != null) : guiString(path + ".skin") != null) && ((guiString("flags_setting_icon." + flagName) != null) ? (strings.length > 1 && strings[1].equals("HDB")) : guiString(path + ".skin").startsWith("HDB:"))) {
            item = HeadDatabaseAPIPlugin.hdbHead((guiString("flags_setting_icon." + flagName) != null) ? strings[2] : guiString(path + ".skin").replace("HDB:", ""));
        } else {
            item = new ItemStack(Material.getMaterial(ItemType), guiInt(path + ".count"));
        }

        ItemMeta meta = item.getItemMeta();

        //Set display name
        String s = placeholderManageFlags.ManageFlags(plotFlag, player, colorize(guiString(path + ".name")), false);
        meta.setDisplayName(s);

        //Set lore
        List<String> l = placeholderManageFlags.ManageFlagsObj(plotFlag, player, colorizeObject(guiList(path + ".lores")));
        meta.setLore(l);

        //Set flags
        for (String flag : guiList(path + ".flags")) {
            meta.addItemFlags(ItemFlag.valueOf(flag));
        }

        //Set custom model data
        if (guiString("flags_setting_icon." + flagName) != null) {
            if (strings.length > 1 && strings[1] != null && strings[2] != null && strings[1].equals("DATA")) {
                meta.setCustomModelData(Integer.parseInt(strings[2]));
            }
        } else {
            meta.setCustomModelData(guiInt(path + ".model-data"));
        }

        if (ItemType.equals("PLAYER_HEAD") && ((guiString("flags_setting_icon." + flagName) != null) ? (strings.length > 1 && strings[1] != null && strings[2] != null) : guiString(path + ".skin") != null) && ((guiString("flags_setting_icon." + flagName) != null) ? (strings.length > 1 && strings[1].equals("PLAYER")) : !guiString(path + ".skin").startsWith("HDB:"))) {
            if ((guiString("flags_setting_icon." + flagName) != null) ? (strings.length > 1 && strings[2] == null) : guiString(path + ".skin") == null) {
                item.setItemMeta(meta);
            } else if ((guiString("flags_setting_icon." + flagName) != null) ? (strings.length > 1 && strings[2].equals("%player%")) : guiString(path + ".skin").equals("%player%")) {
                SkullMeta skullMeta = (SkullMeta) meta;
                skullMeta.setOwner(player.getName());
                item.setItemMeta(skullMeta);
            } else {
                SkullMeta skullMeta = (SkullMeta) meta;
                skullMeta.setOwner((guiString("flags_setting_icon." + flagName) != null) ? strings[2] : guiString(path + ".skin"));
                item.setItemMeta(skullMeta);
            }
            item = itemCreator.addStrCustomNBT(item, "flag", flagName);
            item = itemCreator.addStrCustomNBT(item, "page", String.valueOf(page));
            return item;
        }
        //player.sendMessage("6");
        item.setItemMeta(meta);
        item = itemCreator.addStrCustomNBT(item, "flag", flagName);
        item = itemCreator.addStrCustomNBT(item, "page", String.valueOf(page));
        return item;
    }
}
