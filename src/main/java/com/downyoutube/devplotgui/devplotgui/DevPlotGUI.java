package com.downyoutube.devplotgui.devplotgui;

import com.downyoutube.devplotgui.devplotgui.Commands.maincommand.DevPlotCommand;
import com.downyoutube.devplotgui.devplotgui.Commands.maincommand.plot_gui;
import com.downyoutube.devplotgui.devplotgui.Commands.maincommand.testCommand;
import com.downyoutube.devplotgui.devplotgui.GuiInteraction.*;
import com.google.common.io.ByteStreams;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

public final class DevPlotGUI extends JavaPlugin {

    public static YamlConfiguration guiConfig;
    public static YamlConfiguration messageConfig;
    public static DevPlotGUI main;

    public static YamlConfiguration getGUIConfig() {
        return guiConfig;
    }

    public static YamlConfiguration getMessage() {
        return messageConfig;
    }

    private static File loadResource(Plugin plugin, String resource) {
        File folder = plugin.getDataFolder();
        if (!folder.exists())
            folder.mkdir();
        File resourceFile = new File(folder, resource);
        try {
            if (!resourceFile.exists()) {
                resourceFile.createNewFile();
                try (InputStream in = plugin.getResource(resource);
                     OutputStream out = new FileOutputStream(resourceFile)) {
                    ByteStreams.copy(in, out);
                }
            }
        } catch (Exception e) {
            System.out.println("PUNK");
            e.printStackTrace();
        }
        return resourceFile;
    }

    public static void loadGUI(Plugin plugin) {
        guiConfig = YamlConfiguration.loadConfiguration(loadResource(plugin, "gui.yml"));
    }

    public static void loadMessage(Plugin plugin) {
        messageConfig = YamlConfiguration.loadConfiguration(loadResource(plugin, "message.yml"));
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        main = this;

        loadGUI(this);
        loadMessage(this);
        getServer().getPluginManager().registerEvents(new MainGUI(), this);
        getServer().getPluginManager().registerEvents(new PlayerPlotGUI(), this);
        getServer().getPluginManager().registerEvents(new manage_members(), this);
        getServer().getPluginManager().registerEvents(new ManageAdded(), this);
        getServer().getPluginManager().registerEvents(new manage_flags(), this);

        Objects.requireNonNull(getCommand("testplot")).setExecutor(new testCommand());
        Objects.requireNonNull(getCommand("devplot")).setExecutor(new DevPlotCommand(this));

        new plot_gui();
    }


}
