package com.s4lpicon.mczfislands.permissions;

import com.s4lpicon.mczfislands.islandsmanager.IslandsManager;
import com.s4lpicon.mczfislands.objets.Island;
import com.s4lpicon.mczfislands.utils.GenericUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class PermissionsManager {

    public static boolean canDoThis(Player player, int levelRequired){
        if(player.isOp() || player.hasPermission("mczf.admin")){
            return true;
        }
        if (!player.getWorld().getName().startsWith("PlayersIslands/")){
            return true;
        }
        // el mundo y el nombre si son diferentes comprobamos permisos
        // si no tiene permisos bloqueamos

        String worldName = GenericUtils.getStringAfter(player.getWorld().getName(), "/");
        String comparablePlayerName = String.valueOf(player.getUniqueId());

        UUID owner = Objects.requireNonNull(Bukkit.getPlayer(worldName)).getUniqueId();
        Island island = IslandsManager.activeIslands.get(owner);
        if (island == null){
            player.sendMessage("No puedes estar en esta isla!");
            // bastante raro que esté en una isla que no se considera activa
            return false;
        }

        if (worldName.equalsIgnoreCase(comparablePlayerName)){
            return true;
        }
        if (island.isPlayerMember(player.getUniqueId())){

        }
    }
}
