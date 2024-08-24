package com.s4lpicon.mczfislands.utils;

import com.s4lpicon.mczfislands.datamanager.json.Json;
import com.s4lpicon.mczfislands.objets.Island;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


import static com.s4lpicon.mczfislands.islandsmanager.IslandsManager.activeIslands;

public class IslandsUtils {

    public static void saveAllIslands(){
        for (Island island: activeIslands.values()) {
            Json.saveIslandOnJson(island);
        }
        kickAllPlayersFromIslands();
    }

    public static void kickAllPlayersFromIslands(){
        for (Player player : Bukkit.getOnlinePlayers()){
            TPSpawn.sendPlayerToWorld(player, "main");
            player.sendMessage("Fuiste enviado al spawn porque el servidor se reinicio o apagó!");
        }
    }
}
