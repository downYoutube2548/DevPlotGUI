package com.downyoutube.devplotgui.devplotgui.Other.Placeholders;

import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.flag.GlobalFlagContainer;
import com.plotsquared.core.plot.flag.implementations.DescriptionFlag;

import java.util.ArrayList;
import java.util.List;

public class PlotIcon {
    public static String plotIcon(Plot plot, String s) {
        String id = plot.getId().toString();
        String world = plot.getWorldName();
        String description = plot.getFlag(GlobalFlagContainer.getInstance().getFlag(DescriptionFlag.class));
        s = s.replace("%id%", id);
        s = s.replace("%world%", world);
        s = s.replace("%description%", (description.equals("")) ? "No description set" : description);
        return s;
    }

    public static List<String> plotIconObj(Plot plot, List<String> l) {
        List<String> a = new ArrayList<>();
        for (String s : l) {
            a.add(plotIcon(plot, s));
        }
        return a;
    }
}
