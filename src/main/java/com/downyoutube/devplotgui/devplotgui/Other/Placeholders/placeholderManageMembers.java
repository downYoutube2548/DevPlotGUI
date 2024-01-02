package com.downyoutube.devplotgui.devplotgui.Other.Placeholders;

import com.plotsquared.core.PlotAPI;
import com.plotsquared.core.player.PlotPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class placeholderManageMembers {

    // amount of member
    public static String members(Player player, String s) {
        PlotPlayer<?> p = new PlotAPI().wrapPlayer(player.getUniqueId());
        int amount = p.getCurrentPlot().getMembers().size();
        s = s.replace("%amount%", String.valueOf(amount));
        return s;
    }

    // amount of trust
    public static String trusted(Player player, String s) {
        PlotPlayer<?> p = new PlotAPI().wrapPlayer(player.getUniqueId());
        int amount = p.getCurrentPlot().getTrusted().size();
        s = s.replace("%amount%", String.valueOf(amount));
        return s;
    }

    // amount of denied
    public static String denied(Player player, String s) {
        PlotPlayer<?> p = new PlotAPI().wrapPlayer(player.getUniqueId());
        int amount = p.getCurrentPlot().getDenied().size();
        s = s.replace("%amount%", String.valueOf(amount));
        return s;
    }


    // input from list
    public static List<String> membersObj(Player p, List<String> l) {
        List<String> a = new ArrayList<>();
        for (String s : l) {
            a.add(members(p, s));
        }
        return a;
    }

    public static List<String> trustedObj(Player p, List<String> l) {
        List<String> a = new ArrayList<>();
        for (String s : l) {
            a.add(trusted(p, s));
        }
        return a;
    }

    public static List<String> deniedObj(Player p, List<String> l) {
        List<String> a = new ArrayList<>();
        for (String s : l) {
            a.add(denied(p, s));
        }
        return a;
    }
}
