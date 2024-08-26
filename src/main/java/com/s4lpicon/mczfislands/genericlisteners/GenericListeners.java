package com.s4lpicon.mczfislands.genericlisteners;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class GenericListeners implements Listener {
    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        // Obtén el mundo del que el jugador ha salido
        String worldFrom = event.getFrom().getName();

        // Obtén el mundo al que el jugador ha ingresado
        String worldTo = event.getPlayer().getWorld().getName();

        // Aquí puedes realizar alguna acción, como enviar un mensaje al jugador o guardar información
        //event.getPlayer().sendMessage("Has salido del mundo " + worldFrom + " y entrado al mundo " + worldTo);

        // Si te interesa detectar un mundo específico
        if (worldFrom.startsWith("PlayerIslands")) {
            // Realiza alguna acción
            event.getPlayer().setGameMode(GameMode.SURVIVAL);
        }
    }
}
