package com.downyoutube.devplotgui.devplotgui.Commands.maincommand;

import com.downyoutube.devplotgui.devplotgui.GuiCreation.plot_gui_util;
import com.plotsquared.core.command.*;
import com.plotsquared.core.player.PlotPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandDeclaration(
        command = "gui",
        permission = "plots.gui",
        description = "Plot setting gui",
        category = CommandCategory.SETTINGS,
        requiredType = RequiredType.PLAYER
)
public class plot_gui extends SubCommand {

    public plot_gui() {
        MainCommand.getInstance().register(this);
    }

    @Override
    public boolean onCommand(PlotPlayer<?> plotPlayer, String[] args) {

        Player player = Bukkit.getPlayer(plotPlayer.getName());
        assert player != null;
        plot_gui_util.plotGUIMain(player);


        return false;
    }


}