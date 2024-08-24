package com.s4lpicon.mczfislands.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class TPSpawn {

    public static void sendPlayerToWorld(Player player, String world_name){
        World world = Bukkit.getWorld(world_name);
        assert world != null;
        Location location = new Location(world, 8.5, world.getHighestBlockYAt((int) 8.5, (int) 8.5)+1, 8.5);

        // Teletransportar al jugador
        player.teleport(location);

        player.sendMessage(
                Component.text("Enviandote al spawn")
                        .color(NamedTextColor.BLUE)
                        .decorate(TextDecoration.ITALIC));
    }
}
