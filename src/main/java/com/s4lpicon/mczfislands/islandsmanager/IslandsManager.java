package com.s4lpicon.mczfislands.islandsmanager;

import com.s4lpicon.mczfislands.datamanager.json.Json;
import com.s4lpicon.mczfislands.islandsgeneration.GenerationManager;
import com.s4lpicon.mczfislands.objets.Island;
import com.s4lpicon.mczfislands.utils.TPSpawn;
import com.s4lpicon.mczfislands.utils.WorldsUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import static com.s4lpicon.mczfislands.utils.WorldsUtils.loadWorld;

@SuppressWarnings("UnstableApiUsage")
public class IslandsManager {

    public static HashMap<UUID, Island> activeIslands = new HashMap<>();

    public static void createNewIsland(Player player) {
        File carpet = new File("PlayerIslands/"+player.getUniqueId());
        if(carpet.exists() && carpet.isDirectory()){
            player.sendMessage(
                    Component.text("ERROR: La isla ya existe!")
                            .color(NamedTextColor.RED)
                            .decorate(TextDecoration.BOLD));
            return;
        }
        GenerationManager generator = new GenerationManager();
        Island island = new Island("DEFAULT NAME", player, "DESERT", 3);
        generator.createNewIsland(island);
        player.sendMessage(
                Component.text("Isla creada correctamente!")
                        .color(NamedTextColor.GREEN)
                        .decorate(TextDecoration.BOLD));
        activeIslands.put(player.getUniqueId(), island);
        IslandsPlayersManager.sendPlayerToIsland(player,player.getName());
    }

    public static void activateIsland(UUID uuid){
        String worldName = "PlayerIslands/" + uuid;
        loadWorld(worldName);
        Island island = Json.loadIslandFromJson(uuid);
        if (island == null) {
            Bukkit.getLogger().severe("La isla no fue encontrada para UUID: " + uuid);
            return;
        }
        activeIslands.put(uuid, island);
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            Bukkit.getLogger().severe("No se pudo encontrar el mundo '" + worldName + "' después de intentar cargarlo.");
        }
    }

    public void desactivateIsland(UUID uuid){
        WorldsUtils.unloadWorld(uuid, true);
        activeIslands.remove(uuid);
        Bukkit.getLogger().info("Se desactivo la isla con uuid"+uuid);
    }

    public static void islandSetSpawn(Player player){
        // Obtén el mundo del jugador
        World playerWorld = Objects.requireNonNull(player).getWorld();

        // Compara el nombre del mundo con el nombre que estás buscando
        if(!playerWorld.getName().equals("PlayerIslands/" +player.getUniqueId())){
            player.sendMessage("Debes estar en tu propia isla para ejecutar este comando!");
            return;
        }
        Island island = activeIslands.get(player.getUniqueId());
        if (island == null){
            player.sendMessage("Error con tu isla: ES NULA?"); //esta en su propia isla es imposible
            return;
        }
        island.setSpawn(player.getLocation());
        player.sendMessage("Se ha establecido correctamente el spawn de tu isla!");
    }


}
