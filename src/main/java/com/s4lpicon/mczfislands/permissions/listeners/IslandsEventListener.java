package com.s4lpicon.mczfislands.permissions.listeners;

// Importa los paquetes necesarios
import com.s4lpicon.mczfislands.permissions.PermissionsManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.player.PlayerItemConsumeEvent; // Importa el evento de consumir ítem
import org.bukkit.inventory.ItemStack;

public class IslandsEventListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!PermissionsManager.canDoThis(event.getPlayer(), 1)) {
            event.setCancelled(true); // Cancela el evento, evitando que el jugador rompa bloques
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!PermissionsManager.canDoThis(event.getPlayer(),1)) {
            event.setCancelled(true); // Cancela el evento, evitando que el jugador coloque bloques
        }
    }

//    @EventHandler
    // public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
    // if (IslandsPermissions.blockActionPlayer(event.getPlayer(),2)) {
    //    event.setCancelled(true); // Cancela el evento, evitando que el jugador interactúe con entidades
    //  }
    //}

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (!PermissionsManager.canDoThis((Player) event.getPlayer(),2)) {
            event.setCancelled(true); // Cancela el evento, evitando que el jugador abra inventarios
        }
    }

    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        if (!PermissionsManager.canDoThis(event.getPlayer(),2)) {
            event.setCancelled(true); // Cancela el evento, evitando que el jugador vacíe un balde
        }
    }

    @EventHandler
    public void onPlayerBucketFill(PlayerBucketFillEvent event) {
        if (!PermissionsManager.canDoThis(event.getPlayer(),2)) {
            event.setCancelled(true); // Cancela el evento, evitando que el jugador llene un balde
        }
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack consumedItem = event.getItem();

        // Verifica si el ítem consumido es una Notch Apple (manzana dorada encantada)
        if (consumedItem.getType() == Material.ENCHANTED_GOLDEN_APPLE) {
            // Bloquea el consumo de la Notch Apple
            if (!PermissionsManager.canDoThis(player,0)) {
                event.setCancelled(true); // Cancela el evento, evitando que el jugador consuma la Notch Apple
                player.sendMessage("¡No puedes consumir Notch Apples en esta isla!"); // Mensaje opcional
            }
        }
    }
}
