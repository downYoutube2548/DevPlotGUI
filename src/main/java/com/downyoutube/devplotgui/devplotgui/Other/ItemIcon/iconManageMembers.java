package com.downyoutube.devplotgui.devplotgui.Other.ItemIcon;

import com.downyoutube.devplotgui.devplotgui.HeadDatabaseAPIPlugin;
import com.downyoutube.devplotgui.devplotgui.Other.Placeholders.placeholderManageMembers;
import com.downyoutube.devplotgui.devplotgui.Other.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class iconManageMembers extends Utils {
    public static ItemStack iconManageMembers(Player player, String path) {
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
            switch (getLastKey(path, 1)) {
                case ("members"):
                    s = placeholderManageMembers.members(player, colorize(guiString(path + ".name")));
                    break;
                case ("trusted"):
                    s = placeholderManageMembers.trusted(player, colorize(guiString(path + ".name")));
                    break;
                case ("denied"):
                    s = placeholderManageMembers.denied(player, colorize(guiString(path + ".name")));
                    break;
                default:
                    s = colorize(guiString(path + ".name"));
            }
            meta.setDisplayName(s);
        }
        //player.sendMessage("3");

        //Set lore
        if (guiList(path + ".lores") != null) {
            List<String> l;
            switch (getLastKey(path, 1)) {
                case ("members"):
                    l = placeholderManageMembers.membersObj(player, colorizeObject(guiList(path + ".lores")));
                    break;
                case ("trusted"):
                    l = placeholderManageMembers.trustedObj(player, colorizeObject(guiList(path + ".lores")));
                    break;
                case ("denied"):
                    l = placeholderManageMembers.deniedObj(player, colorizeObject(guiList(path + ".lores")));
                    break;
                default:
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
}
