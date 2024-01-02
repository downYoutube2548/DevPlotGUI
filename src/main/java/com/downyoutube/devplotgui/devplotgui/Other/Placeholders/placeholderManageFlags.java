package com.downyoutube.devplotgui.devplotgui.Other.Placeholders;

import com.downyoutube.devplotgui.devplotgui.Other.Utils;
import com.plotsquared.core.PlotAPI;
import com.plotsquared.core.configuration.caption.LocaleHolder;
import com.plotsquared.core.plot.flag.GlobalFlagContainer;
import com.plotsquared.core.plot.flag.PlotFlag;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class placeholderManageFlags extends Utils {
    public static String ManageFlags(Class<?> flag, Player player, String s, Boolean t) {
        String value = new PlotAPI().wrapPlayer(player.getUniqueId()).getCurrentPlot().getFlagContainer().getFlag(GlobalFlagContainer.getInstance().getFlagClassFromString(getPlotFlagName(flag)).asSubclass(PlotFlag.class)).toString();
        String category = GlobalFlagContainer.getInstance().getFlagFromString(getPlotFlagName(flag)).getFlagCategory().getComponent(LocaleHolder.console());
        String[] l = getPlotFlagName(flag).split("-");
        StringBuilder flagDisplay = new StringBuilder();
        for (String a : l) {
            flagDisplay.append(a.substring(0, 1).toUpperCase()).append(a.substring(1) + " ");
        }
        String description = GlobalFlagContainer.getInstance().getFlagFromString(getPlotFlagName(flag)).getFlagDescription().getComponent(LocaleHolder.console());
        String description_1;
        String description_2 = (t) ? "%none%" : "";
        int length = 50;
        if (description.length() > 50) {
            for (int i = 50; i < description.length(); i++) {
                if (!description.substring(0, i).endsWith(" ")) {
                    length++;
                } else {
                    break;
                }
            }
            description_1 = description.substring(0, length);
            description_2 = ((description.substring(length).equals("")) ? ((t) ? "%none%" : "") : description.substring(length));
        } else {
            description_1 = description;
        }

        /*if (description_2.equals("") || description_2.equals(" ")) {
            description_2 = "%none%";
        }*/

        s = s.replace("%flag%", flagDisplay)
                .replace("%value%", value)
                .replace("%category%", category)
                .replace("%description_1%", description_1)
                .replace("%description_2%", description_2);
        return s;
    }

    public static List<String> ManageFlagsObj(Class<?> flag, Player player, List<String> l) {
        List<String> a = new ArrayList<>();
        for (String s : l) {
            if (!(ManageFlags(flag, player, s, true).contains("%none%"))) {
                a.add(ManageFlags(flag, player, s, true));
            }
        }
        return a;
    }
}
