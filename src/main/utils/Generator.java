package main.utils;

import java.util.Random;

public abstract class Generator {
    public static int generateRandomNumber(int min, int max){
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }
}