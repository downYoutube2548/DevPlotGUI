package com.downyoutube.devplotgui.devplotgui;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class HeadDatabaseAPIPlugin extends JavaPlugin {

    public static ItemStack hdbHead(String Id) {
        if (Bukkit.getPluginManager().isPluginEnabled("HeadDatabase")) {
            HeadDatabaseAPI api = new HeadDatabaseAPI();
            try {
                ItemStack item = api.getItemHead(Id);
                return item;
            } catch (NullPointerException nullPointerException) {
                return null;
            }

        }
        return null;
    }

}
