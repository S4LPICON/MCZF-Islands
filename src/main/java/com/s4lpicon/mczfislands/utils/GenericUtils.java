package com.s4lpicon.mczfislands.utils;

public class GenericUtils {

    public static String getStringAfter(String input, String simbol) {
        // Dividir el string en partes utilizando el carácter '/'
        String[] parts = input.split(simbol);

        // Verificar si hay al menos dos partes
        if (parts.length > 1) {
            // Retornar la parte después del '/'
            return parts[1];
        } else {
            // Si no hay '/' en el string, retornar el string original o manejar el caso como desees
            return input;
        }
    }
}
