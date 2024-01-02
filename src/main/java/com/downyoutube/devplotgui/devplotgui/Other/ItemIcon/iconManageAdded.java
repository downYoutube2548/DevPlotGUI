package com.downyoutube.devplotgui.devplotgui.Other.ItemIcon;

import com.downyoutube.devplotgui.devplotgui.HeadDatabaseAPIPlugin;
import com.downyoutube.devplotgui.devplotgui.Other.Placeholders.placeholderManageAdded;
import com.downyoutube.devplotgui.devplotgui.Other.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.UUID;

public class iconManageAdded extends Utils {
    public static ItemStack iconManageAdded(String path, Player player, UUID uuid, int page) {
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

        String s = placeholderManageAdded.ManageAdded(uuid, colorize(guiString(path + ".name")));
        meta.setDisplayName(s);

        //Set lore
        List<String> l = placeholderManageAdded.ManageAddedObj(uuid, colorizeObject(guiList(path + ".lores")));
        meta.setLore(l);

        //Set flags
        for (String flag : guiList(path + ".flags")) {
            meta.addItemFlags(ItemFlag.valueOf(flag));
        }

        //Set custom model data
        meta.setCustomModelData(guiInt(path + ".model-data"));

        if (guiString(path + ".type").equals("PLAYER_HEAD") && guiString(path + ".skin") != null && !guiString(path + ".skin").startsWith("HDB:")) {
            if (guiString(path + ".skin") == null) {
                item.setItemMeta(meta);
            } else if (guiString(path + ".skin").equals("%player%")) {
                SkullMeta skullMeta = (SkullMeta) meta;
                skullMeta.setOwner(player.getName());
                item.setItemMeta(skullMeta);
            } else if (guiString(path + ".skin").equals("%username%")) {
                SkullMeta skullMeta = (SkullMeta) meta;
                skullMeta.setOwner(Bukkit.getOfflinePlayer(uuid).getName());
                item.setItemMeta(skullMeta);
            } else {
                SkullMeta skullMeta = (SkullMeta) meta;
                skullMeta.setOwner(guiString(path + ".skin"));
                item.setItemMeta(skullMeta);
            }
            item = itemCreator.addStrCustomNBT(item, "username", Bukkit.getOfflinePlayer(uuid).getName());
            item = itemCreator.addStrCustomNBT(item, "page", String.valueOf(page));
            return item;
        }
        //player.sendMessage("6");
        item.setItemMeta(meta);
        item = itemCreator.addStrCustomNBT(item, "username", Bukkit.getOfflinePlayer(uuid).getName());
        item = itemCreator.addStrCustomNBT(item, "page", String.valueOf(page));
        return item;
    }
}
