package com.s4lpicon.mczfislands.permissions.listeners;

import com.s4lpicon.mczfislands.permissions.PermissionsManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageEntityListener implements Listener {
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player attacker) {
            Entity target = event.getEntity();

            if (!PermissionsManager.canDoThis(attacker, 2)) {
                if (target instanceof Player targetPlayer) {
                    if (arePlayersInSameIsland(attacker, targetPlayer)) {
                        event.setCancelled(true); // Cancela el evento
                    }
                } else {
                    if (isPetOrFarmAnimal(target)) {
                        event.setCancelled(true); // Cancela el evento
                    }
                }
            }
        }
    }

    private boolean arePlayersInSameIsland(Player attacker, Player targetPlayer) {
        return PermissionsManager.canDoThis(attacker, 2)
                && PermissionsManager.canDoThis(targetPlayer, 2);
    }

    // Método para verificar si la entidad es un mob hostil
    private boolean isHostileMob(Entity entity) {
        return switch (entity.getType()) {
            case ZOMBIE, SKELETON, CREEPER, SPIDER, ENDERMAN, WITCH, BLAZE, SLIME, ENDER_DRAGON, WITHER, GHAST, PILLAGER, VINDICATOR, EVOKER ->
                // Agrega más mobs hostiles según sea necesario
                    true;
            default -> false;
        };
    }

    // Método para verificar si la entidad es una mascota o un animal de granja
    private boolean isPetOrFarmAnimal(Entity entity) {
        return switch (entity.getType()) {
            case WOLF, CAT, HORSE, DONKEY, MULE, LLAMA, PARROT, SHEEP, COW, PIG, CHICKEN, RABBIT, TURTLE, BEE, FOX, PANDA, POLAR_BEAR, STRIDER, GOAT, FROG, CAMEL ->
                // Agrega más mascotas y animales de granja según sea necesario
                    true;
            default -> false;
        };
    }
}
