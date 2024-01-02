package com.downyoutube.devplotgui.devplotgui.Other.ItemIcon;

import com.downyoutube.devplotgui.devplotgui.HeadDatabaseAPIPlugin;
import com.downyoutube.devplotgui.devplotgui.Other.Placeholders.PlotIcon;
import com.downyoutube.devplotgui.devplotgui.Other.Utils;
import com.plotsquared.core.plot.Plot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class plotIcon extends Utils {
    public static ItemStack plot_icon(String path, Player player, Plot plot) {
        if (guiString(path + ".type") == null) {
            return null;
        }
        //player.sendMessage("1");
        String world = plot.getWorldName();
        String id = plot.getId().toString();
        ItemStack item;
        if (guiString(path + ".type").equals("PLAYER_HEAD") && guiString(path + ".skin") != null && guiString(path + ".skin").startsWith("HDB:")) {
            item = HeadDatabaseAPIPlugin.hdbHead(guiString(path + ".skin").replace("HDB:", ""));
        } else {
            item = new ItemStack(Material.getMaterial(guiString(path + ".type")), guiInt(path + ".count"));
        }

        ItemMeta meta = item.getItemMeta();

        //Set display name
        String s = PlotIcon.plotIcon(plot, colorize(guiString(path + ".name")));
        meta.setDisplayName(s);

        //Set lore
        List<String> l = PlotIcon.plotIconObj(plot, colorizeObject(guiList(path + ".lores")));
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
            } else {
                SkullMeta skullMeta = (SkullMeta) meta;
                skullMeta.setOwner(guiString(path + ".skin"));
                item.setItemMeta(skullMeta);
            }
            item = itemCreator.addStrCustomNBT(item, "world", world);
            item = itemCreator.addStrCustomNBT(item, "PlotID", id);
            return item;
        }
        //player.sendMessage("6");
        item.setItemMeta(meta);
        item = itemCreator.addStrCustomNBT(item, "world", world);
        item = itemCreator.addStrCustomNBT(item, "PlotID", id);
        return item;
    }
}
