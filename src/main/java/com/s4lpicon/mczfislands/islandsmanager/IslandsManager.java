package com.s4lpicon.mczfislands.islandsmanager;

import com.s4lpicon.mczfislands.islandsgeneration.GenerationManager;
import com.s4lpicon.mczfislands.objets.Island;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class IslandsManager {

    public static HashMap<UUID, Island> activeIslands = new HashMap<>();

    public static void sendPlayerToIsland(Player player,  String islandOwnerName) {
        assert player != null;
        // Obtener el nombre del mundo desde el UUID del propietario
        Player ownerIsland = Bukkit.getPlayer(islandOwnerName);
        //Verificar si el dueño de la isla se encuentra conectado
        if (ownerIsland == null){
            player.sendMessage(Component.text("No puedes conectarte a esta isla, El dueño esta desconectado!"));
            return;
        }
        String worldName = "PlayerIslands/"+ownerIsland.getUniqueId();


        // Obtener el mundo por su nombre
        World world = Bukkit.getWorld(worldName);
        Island island = activeIslands.get(ownerIsland.getUniqueId());
        if (island == null){
            activateIsland(ownerIsland.getUniqueId());
        }

        if (world != null) {
            // Crear una nueva ubicación en el mundo
            assert island != null;
            double x = island.getSpawnCoordX();
            double z = island.getSpawnCoordZ();
            Location location = new Location(world, x, world.getHighestBlockYAt((int) x, (int) z)+1, z);

            // Teletransportar al jugador
            player.teleport(location);

            player.sendMessage(
                    Component.text("Enviandote a la isla")
                            .color(NamedTextColor.GREEN)
                            .decorate(TextDecoration.UNDERLINED));
        } else {
            player.sendMessage(
                    Component.text("ERROR: La isla no existe!")
                            .color(NamedTextColor.RED)
                            .decorate(TextDecoration.BOLD));
        }
    }

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
        sendPlayerToIsland(player,player.getName());
    }

    public static void activateIsland(UUID uuid){
        String worldName = "PlayerIslands/" + uuid.toString();
        loadWorld(worldName);

        // Ahora puedes trabajar con el mundo cargado
        World world = Bukkit.getWorld(worldName);
        if (world != null) {
            // Realiza más operaciones con el mundo, como teletransportar jugadores, etc.

        } else {
            Bukkit.getLogger().severe("No se pudo encontrar el mundo '" + worldName + "' después de intentar cargarlo.");
        }
    }

    public void desactivateIsland(UUID uuid){

    }

    public static void loadWorld(String worldName) {
        File worldFolder = new File(Bukkit.getWorldContainer(), worldName);

        if (!worldFolder.exists()) {
            Bukkit.getLogger().severe("¡El mundo '" + worldName + "' no existe en el disco!");
            return;
        }

        // Crear un WorldCreator con el nombre del mundo
        WorldCreator creator = new WorldCreator(worldName);

        // Cargar el mundo usando el WorldCreator
        World world = Bukkit.createWorld(creator);

        if (world != null) {
            Bukkit.getLogger().info("¡El mundo '" + worldName + "' se ha cargado correctamente!");
        } else {
            Bukkit.getLogger().severe("Hubo un problema al cargar el mundo '" + worldName + "'.");
        }
    }

    public static void unloadWorld(String worldName, boolean save) {
        World world = Bukkit.getWorld(worldName);
        if (world != null) {
            Bukkit.unloadWorld(world, save);
            Bukkit.getLogger().info("El mundo '" + worldName + "' ha sido descargado.");
        } else {
            Bukkit.getLogger().warning("El mundo '" + worldName + "' no estaba cargado.");
        }
    }
}
