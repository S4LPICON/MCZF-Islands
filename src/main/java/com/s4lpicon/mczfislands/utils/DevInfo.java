package com.s4lpicon.mczfislands.utils;

import com.s4lpicon.mczfislands.objets.Island;
import org.bukkit.entity.Player;

import static com.s4lpicon.mczfislands.islandsmanager.IslandsManager.activeIslands;

public class DevInfo {
    public static void devinfo(Player player){
        for (Island island : activeIslands.values()){
            player.sendMessage(island.toString());
            player.sendMessage("------------------------------");
        }
    }
}
