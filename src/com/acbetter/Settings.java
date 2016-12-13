package com.acbetter;

/**
 * Created by Reign on 16/6/28.
 * Settings
 */
class Settings {
    static int SIZE = 5;
    static int TIME = 10;

    public static String[] getSIZE() {
        String[] str = new String[90];
        for (int i = 0; i < 90; i++) {
            str[i] = (i + 5) + "x" + (i + 5);
        }
        return str;
    }

    public static void setSIZE(int SIZE) {
        Settings.SIZE = SIZE + 5;
    }

    public static String[] getTIME() {
        String[] str = new String[50];
        for (int i = 0; i < 50; i++) {
            str[i] = String.valueOf(i * 10);
        }
        return str;
    }

    public static void setTIME(int TIME) {
        Settings.TIME = (TIME + 1) * 10;
    }
}
