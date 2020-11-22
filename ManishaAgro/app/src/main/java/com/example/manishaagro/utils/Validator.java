package com.example.manishaagro.utils;

public class Validator {

    public static boolean isValidName(String name) {
        if(name.isEmpty())
            return false;
        for (char c : name.toCharArray()) {
            if (Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidMobileNumber(String number) {
        boolean hasCharacter = false;
        for (char c : number.toCharArray()) {
            if (!Character.isDigit(c)) {
                hasCharacter = true;
                break;
            }
        }
        return number.length() == 10 && !hasCharacter;
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }


}
