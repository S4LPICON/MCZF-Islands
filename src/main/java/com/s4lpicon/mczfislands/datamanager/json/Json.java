package com.s4lpicon.mczfislands.datamanager.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.s4lpicon.mczfislands.objets.Island;


import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;

public class Json {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void saveIslandOnJson(Island island) {
        if (island == null) {
            // Maneja el caso donde jugadorIsla es null, tal vez con un mensaje de error o alguna otra lógica.
            System.out.println("Error: jugadorIsla es null. No se puede guardar en JSON.");
            return;
        }
        String rutaArchivo = "PlayerIslands/islandData/" + island.getOwnerUuid() + ".json";
        // Crear una instancia de Gson con configuración para incluir solo campos anotados con @Expose
        // y formatear el JSON de manera legible.
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation() // Solo incluir campos anotados con @Expose
                .setPrettyPrinting() // Para imprimir JSON de manera legible
                .create();

        // Convertir el objeto a JSON y guardarlo en el archivo.
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            gson.toJson(island, writer);
            System.out.println("Datos guardados en " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo JSON: " + e.getMessage());
        }
    }

    public static Island loadIslandFromJson(UUID islandOwnerUuid) {
        String rutaArchivo = "PlayerIslands/islandData/" + islandOwnerUuid;
        try (FileReader reader = new FileReader(rutaArchivo)) {
            return gson.fromJson(reader, Island.class);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo JSON: " + e.getMessage());
            return null;
        }
    }
}