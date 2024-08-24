package com.s4lpicon.mczfislands.islandsgeneration;

import com.s4lpicon.mczfislands.objets.Island;
import org.bukkit.*;
import org.apache.logging.log4j.Logger;

import java.io.File;

import static org.apache.logging.log4j.LogManager.getLogger;

public class GenerationManager {

    private static final Logger LOGGER = getLogger(GenerationManager.class);

    public void createNewIsland(Island island) {
        String folderName = "PlayerIslands/"; // Carpeta principal para islas
        String worldName = folderName + island.getOwnerUuid(); // Nombre del mundo

        // Obtener la carpeta del servidor donde se guardan los mundos
        File baseWorldFolder = Bukkit.getServer().getWorldContainer();

        // Crear un nuevo directorio para el mundo dentro de la carpeta 'PlayerIslands'
        File playerIslandsFolder = new File(baseWorldFolder, folderName);
        if (!playerIslandsFolder.exists()) {
            if (playerIslandsFolder.mkdirs()) {
                LOGGER.info("Carpeta 'PlayerIslands' creada con éxito.");
            } else {
                LOGGER.error("No se pudo crear la carpeta 'PlayerIslands'.");
                return;
            }
        }

        // Configurar el WorldCreator para usar el nombre del mundo
        WorldCreator worldCreator = islandCreator(island, worldName);

        // Crear el mundo
        World world = Bukkit.createWorld(worldCreator);
        assert world != null;
        islandFinalSettings(island,world);
    }

    public WorldCreator islandCreator(Island island, String worldName) {
        // Reemplazar caracteres no válidos en el nombre del mundo

        WorldCreator worldCreator = new WorldCreator(worldName);
        // Configurar las propiedades del mundo
        worldCreator.environment(World.Environment.NORMAL); // Tipo de ambiente (NORMAL, NETHER, THE_END)

        // Configuración del tipo de mundo basado en el tipo de isla
        switch (island.getType()) {
            case "DESERT":
                worldCreator.type(WorldType.FLAT);
                break;
            case "ICE":
                worldCreator.type(WorldType.AMPLIFIED);
                break;
            case "SABANA":
                worldCreator.type(WorldType.LARGE_BIOMES);
                break;
            default:
                worldCreator.type(WorldType.NORMAL);
                break;
        }

        worldCreator.generateStructures(true); // Generar estructuras como aldeas, templos, etc.
        return worldCreator;
    }

    public void islandFinalSettings(Island island, World world) {
        WorldBorder border = world.getWorldBorder();

        switch (island.getSize()) {
            case 2:
                border.setSize(25);
                break;
            case 3:
                border.setSize(50);
                break;
            default:
                border.setSize(3);
                break;
        }
        border.setCenter(island.getSpawnCoordX(),island.getSpawnCoordZ());
        border.setWarningDistance(-2);
    }
}
