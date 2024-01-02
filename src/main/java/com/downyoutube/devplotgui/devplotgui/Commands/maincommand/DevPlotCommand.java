package com.downyoutube.devplotgui.devplotgui.Commands.maincommand;

import com.downyoutube.devplotgui.devplotgui.DevPlotGUI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class DevPlotCommand implements CommandExecutor {
    private final DevPlotGUI plugin;

    public DevPlotCommand(DevPlotGUI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length >= 1 && args[0].equals("reload")) {
            sender.sendMessage("§aCorrectly reload DevPlotAPI!");
            DevPlotGUI.loadGUI(this.plugin);
            DevPlotGUI.loadMessage(this.plugin);
        } else {
            sender.sendMessage(ChatColor.RED + "Usage: /" + command.getName() + " reload");
        }

        return false;
    }
}
