package main.utils;

public abstract class Validator {
    public static boolean isValidString(String s){
        return s != null && s.length() > 0;
    }
}
