package com.downyoutube.devplotgui.devplotgui.GuiCreation;

import com.downyoutube.devplotgui.devplotgui.GuiPage.PlayerPlotPage;
import com.downyoutube.devplotgui.devplotgui.Other.ItemIcon.itemCreator;
import com.downyoutube.devplotgui.devplotgui.Other.ItemIcon.plotIcon;
import com.downyoutube.devplotgui.devplotgui.Other.Utils;
import com.plotsquared.core.PlotAPI;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class player_plot extends Utils {

    public static void plotGUIPlayerPlot(Player player, int page) {
        PlotPlayer<?> plotPlayer = new PlotAPI().wrapPlayer(player.getName());
        assert plotPlayer != null;
        Set<Plot> p = plotPlayer.getPlots();
        List<Plot> PlotList = new ArrayList<>();
        for (Plot plot : p) {
            if (!PlotList.contains(Plot.getPlotFromString(plotPlayer, plot.getWorldName() + ";" + plot.getBasePlot(false).getId(), false))) {
                PlotList.add(Plot.getPlotFromString(plotPlayer, plot.getWorldName() + ";" + plot.getBasePlot(false).getId(), false));
            }
        }
        List<Integer> listSlot = guiListInt("gui.plot.plot_icon.slot");
        int size = guiInt("gui.plot.rows") * 9;
        Inventory PlayerPlotInv = Bukkit.createInventory(player, size, colorize(guiString("gui.plot.title")));
        for (String key : guiKey("gui.plot.item")) {
            if (guiKey("item").contains(key)) {
                for (Integer i : guiListInt("gui.plot.item." + key)) {
                    if (key.equals("next") && !PlayerPlotPage.isPageValid(PlotList.size(), page + 1, listSlot.size())) {
                        break;
                    } else if (key.equals("previous") && !PlayerPlotPage.isPageValid(PlotList.size(), page - 1, listSlot.size())) {
                        break;
                    } else {
                        ItemStack item = switch (key) {
                            case ("previous"), ("next") ->
                                    itemCreator.addStrCustomNBT(itemCreator.item("item." + key, player), "page", String.valueOf(page));
                            default -> itemCreator.item("item." + key, player);
                        };
                        PlayerPlotInv.setItem(i, item);
                    }
                }
            }
        }
        if (guiString("gui.plot") != null) {
            Plot[] plots = PlotList.toArray(Plot[]::new);
            int l = ((page * listSlot.size()) - (listSlot.size() - 1)) - 1;
            for (int i = 0; i < PlotList.size() && i < listSlot.size() && l < PlotList.size(); i++) {
                PlayerPlotInv.setItem(listSlot.get(i), plotIcon.plot_icon("gui.plot.plot_icon", player, plots[l]));
                l++;
            }
        }

        player.openInventory(PlayerPlotInv);
    }
}
