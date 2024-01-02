package com.downyoutube.devplotgui.devplotgui.Other;

import com.downyoutube.devplotgui.devplotgui.DevPlotGUI;
import com.plotsquared.core.PlotAPI;
import com.plotsquared.core.player.PlotPlayer;
import de.tr7zw.nbtapi.NBTItem;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static String colorize(String s) {
        if (s == null || s.equals(""))
            return "";
        if (!Bukkit.getVersion().contains("1.16") && !Bukkit.getVersion().contains("1.17"))
            return ChatColor.translateAlternateColorCodes('&', s);
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher match = pattern.matcher(s);
        while (match.find()) {
            String hexColor = s.substring(match.start(), match.end());
            s = s.replace(hexColor, ChatColor.of(hexColor).toString());
            match = pattern.matcher(s);
        }
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static List<String> colorizeObject(List<String> l) {
        List<String> output = new ArrayList<>();
        for (String s : l) {
            output.add(colorize(s));
        }
        return output;
    }

    public static Integer guiInt(String path) {
        return DevPlotGUI.getGUIConfig().getInt(path);
    }

    public static String guiString(String path) {
        return DevPlotGUI.getGUIConfig().getString(path);
    }

    public static List<String> guiList(String path) {
        return DevPlotGUI.getGUIConfig().getStringList(path);
    }

    public static Set<String> guiKey(String path) {
        return DevPlotGUI.getGUIConfig().getConfigurationSection(path).getKeys(false);
    }

    public static List<Integer> guiListInt(String path) {
        return DevPlotGUI.getGUIConfig().getIntegerList(path);
    }

    public static String getLastKey(String path, int i) {
        String[] output = path.split("\\.");
        return output[output.length - i];
    }

    public static String getMessage(String path) {
        if (DevPlotGUI.getMessage().getString(path) != null) {
            return colorize(DevPlotGUI.getMessage().getString(path));
        }
        return "";
    }

    public static ItemStack addStrCustomNBT(ItemStack item, String nbt, String value) {
        NBTItem nbti = new NBTItem(item);
        nbti.setString(nbt, value);
        return nbti.getItem();
    }

    public static Boolean isInPlot(Player player) {
        PlotPlayer<?> plotPlayer = new PlotAPI().wrapPlayer(player.getName());
        if (plotPlayer.getCurrentPlot() == null) {
            player.sendMessage(getMessage("message.not_in_plot"));
            return false;
        }

        if (!plotPlayer.getCurrentPlot().getOwners().contains(player.getUniqueId())) {
            player.sendMessage(getMessage("message.must_be_owner"));
            return false;
        }
        return true;
    }

    public static String getPlotFlagName(Class<?> flagClass) {
        final StringBuilder flagName = new StringBuilder();
        final char[] chars = flagClass.getSimpleName().replace("Flag", "").toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i == 0) {
                flagName.append(Character.toLowerCase(chars[i]));
            } else if (Character.isUpperCase(chars[i])) {
                flagName.append('-').append(Character.toLowerCase(chars[i]));
            } else {
                flagName.append(chars[i]);
            }
        }
        return flagName.toString();
    }
}
