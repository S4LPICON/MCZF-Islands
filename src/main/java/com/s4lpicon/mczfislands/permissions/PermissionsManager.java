package com.s4lpicon.mczfislands.permissions;

import com.s4lpicon.mczfislands.islandsmanager.IslandsManager;
import com.s4lpicon.mczfislands.objets.Island;
import com.s4lpicon.mczfislands.utils.GenericUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PermissionsManager {

    public static boolean canDoThis(Player player, int levelRequired) {
//        // Chequeo si el jugador es un admin
//        player.sendMessage("Entra a canDoThis");
//        String worldName1 = player.getWorld().getName();
//        player.sendMessage("Nombre del mundo: " + worldName1); // Mensaje de depuración
//
//        if (worldName1.equals("main")) {
//            player.sendMessage("Estás en el mundo principal");
//            // Agrega lógica de permisos especial si es necesario
//        }
        if (player.isOp() || player.hasPermission("mczf.admin")) {
//            player.sendMessage("Puedes hacer esto, eres admin");
            return true;
        }

        // Verifica si el jugador está en un mundo de jugador
        String worldName = player.getWorld().getName();
        if (!worldName.startsWith("PlayerIslands/")) {
//            player.sendMessage("No estás en una isla de jugador, mundo actual: " + worldName);
            return true;
        }

        // Obtén el UUID del propietario del mundo
        UUID ownerUuid = UUID.fromString(GenericUtils.getStringAfter(worldName, "/"));
//        try {
//            ownerUuid = UUID.fromString(GenericUtils.getStringAfter(worldName, "/"));
//        } catch (IllegalArgumentException e) {
//            player.sendMessage("UUID del propietario inválido, mundo actual: " + worldName);
//            return false;
//        }

        Player ownerPlayer = Bukkit.getPlayer(ownerUuid);
        if (ownerPlayer == null) {
            player.sendMessage("No puedes estar en esta isla, propietario no encontrado, UUID: " + ownerUuid);
            return false;
        }

        Island island = IslandsManager.activeIslands.get(ownerPlayer.getUniqueId());
        if (island == null) {
            player.sendMessage("No puedes estar en esta isla, isla no encontrada.");
            return false;
        }

        // Verifica si el jugador es el propietario de la isla
        if (player.getUniqueId().equals(ownerPlayer.getUniqueId())) {
//            player.sendMessage("Puedes hacer esto, eres el dueño de la isla.");
            return true;
        }

        // Verifica permisos en la isla
        if (!island.isPlayerMember(player.getUniqueId())) {
//            player.sendMessage("No eres miembro de la isla.");
            return false;
        }

        return island.getLevelPermission(player.getUniqueId()) >= levelRequired;
    }
}
