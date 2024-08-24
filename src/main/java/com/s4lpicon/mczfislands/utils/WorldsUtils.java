package com.s4lpicon.mczfislands.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.util.UUID;

public class WorldsUtils {
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

    public static void unloadWorld(UUID uuid, boolean save) {
        String worldName = "PlayerIslands/"+ uuid;
        World world = Bukkit.getWorld(worldName);
        if (world != null) {
            Bukkit.unloadWorld(world, save);
            Bukkit.getLogger().info("El mundo '" + worldName + "' ha sido descargado.");
        } else {
            Bukkit.getLogger().warning("El mundo '" + worldName + "' no estaba cargado.");
        }
    }
}
