package com.s4lpicon.mczfislands.utils;

import org.bukkit.entity.Player;

import static com.s4lpicon.mczfislands.islandsmanager.IslandsManager.activeIslands;

public class DevInfo {
    public static void devinfo(Player player){
        player.sendMessage("Info"+ activeIslands.toString());
    }
}
