package com.downyoutube.devplotgui.devplotgui.Other.Placeholders;

import com.plotsquared.core.PlotAPI;
import com.plotsquared.core.player.PlotPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MainGUIPlaceholder {
    public static String PlayerPlotPlaceholder(Player p, String s) {
        PlotPlayer<?> player = new PlotAPI().wrapPlayer(p.getName());
        assert player != null;
        int amount = player.getPlotCount();
        int max = player.getAllowedPlots();
        s = s.replace("%amount%", String.valueOf(amount));
        s = s.replace("%max%", String.valueOf(max));
        return s;
    }

    public static List<String> PlayerPlotPlaceholderObj(Player p, List<String> l) {
        List<String> a = new ArrayList<>();
        for (String s : l) {
            a.add(PlayerPlotPlaceholder(p, s));
        }
        return a;
    }
}
