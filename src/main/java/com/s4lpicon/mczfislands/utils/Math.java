package com.s4lpicon.mczfislands.utils;

import java.util.Random;

public class Math {

    public static double randomNumber(double min, double max){
        Random random = new Random();
        return random.nextInt((int) ((max - min) + 1)) + min;
    }
}
