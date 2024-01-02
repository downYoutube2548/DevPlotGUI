package com.downyoutube.devplotgui.devplotgui.Commands.maincommand;

import com.downyoutube.devplotgui.devplotgui.Other.Utils;
import com.plotsquared.core.PlotAPI;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.flag.GlobalFlagContainer;
import com.plotsquared.core.plot.flag.PlotFlag;
import com.plotsquared.core.util.query.PlotQuery;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class testCommand extends Utils implements CommandExecutor {


    private PlotQuery query(PlotPlayer<?> paramPlotPlayer) {
        return PlotQuery.newQuery().thatPasses(paramPlot -> paramPlot.isOwner(paramPlotPlayer.getUUID()));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        String value2 = new PlotAPI().wrapPlayer(((Player) sender).getUniqueId()).getCurrentPlot().getFlagContainer().getFlag(GlobalFlagContainer.getInstance().getFlagClassFromString("pvp").asSubclass(PlotFlag.class)).toString();
        Bukkit.broadcastMessage(value2);
        return false;
    }
}
