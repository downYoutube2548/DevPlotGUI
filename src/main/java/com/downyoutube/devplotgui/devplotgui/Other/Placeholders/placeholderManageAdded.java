package com.downyoutube.devplotgui.devplotgui.Other.Placeholders;

import com.downyoutube.devplotgui.devplotgui.Other.Utils;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class placeholderManageAdded extends Utils {
    public static String ManageAdded(UUID uuid, String s) {
        String userName = Bukkit.getOfflinePlayer(uuid).getName();
        String displayName = (Bukkit.getOfflinePlayer(uuid).isOnline()) ? Bukkit.getPlayer(uuid).getDisplayName() : Bukkit.getOfflinePlayer(uuid).getName();
        String status = (Bukkit.getOfflinePlayer(uuid).isOnline()) ? getMessage("message.online") : getMessage("message.offline");
        s = s.replace("%username%", userName);
        s = s.replace("%displayname%", displayName);
        s = s.replace("%status%", status);
        return s;
    }

    public static List<String> ManageAddedObj(UUID uuid, List<String> l) {
        List<String> a = new ArrayList<>();
        for (String s : l) {
            a.add(ManageAdded(uuid, s));
        }
        return a;
    }
}
