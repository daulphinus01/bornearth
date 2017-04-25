package com.bornearth.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Marcellin RWEGO
 * @since 1.0.0 [26/10/2016]
 */
public class BornEarthSharePrefs extends Application {

    public static BornEarthSharePrefs bornEarthSharePrefs;
    private Context context;

    public static int day;
    public static int year;
    public static int monht;
    public static int hours;
    public static String nom;
    public static int minutes;
    public static int secondes;
    public static String prenom;

    private static final String BORNEARTH_NOM = "born_earth_nom";
    private static final String BORNEARTH_MON = "born_earth_mon";
    private static final String BORNEARTH_DAY = "born_earth_day";
    private static final String BORNEARTH_HOUR = "born_earth_hour";
    private static final String BORNEARTH_YEAR = "born_earth_year";
    private static final String BORNEARTH_PRENOM = "born_earth_prenom";
    private static final String BORNEARTH_MINUTE = "born_earth_minute";
    private static final String BORNEARTH_SECONDE = "born_earth_seconde";
    private static final String BORNEARTH_NAME = "born_earth_shared_prefs";

    public static void init(Context context) {
        bornEarthSharePrefs = new BornEarthSharePrefs();
        bornEarthSharePrefs.context = context;
        if (birthDateAvailable()) {
            loadBirthDate();
            loadBirthTime();
            loadPersonalInfo();
        }
    }

    public static void reloadPreferences() {
        if (birthDateAvailable()) {
            loadBirthDate();
            loadBirthTime();
            loadPersonalInfo();
        }
    }

    public static void storeBirthDate(int year, int month, int day) {
        SharedPreferences.Editor editor = bornEarthSharePrefs.context.getSharedPreferences(BORNEARTH_NAME, MODE_PRIVATE).edit();
        editor.putInt(BORNEARTH_YEAR, year);
        editor.putInt(BORNEARTH_MON, month);
        editor.putInt(BORNEARTH_DAY, day);
        editor.apply();
    }

    public static void storeBirthTime(int hour, int minute, int secondes) {
        SharedPreferences.Editor editor = bornEarthSharePrefs.context.getSharedPreferences(BORNEARTH_NAME, MODE_PRIVATE).edit();
        editor.putInt(BORNEARTH_HOUR, hour);
        editor.putInt(BORNEARTH_MINUTE, minute);
        editor.putInt(BORNEARTH_SECONDE, secondes);
        editor.apply();
    }

    public static void storePersonalInfo(String nom, String prenom) {
        SharedPreferences.Editor editor = bornEarthSharePrefs.context.getSharedPreferences(BORNEARTH_NAME, MODE_PRIVATE).edit();
        editor.putString(BORNEARTH_NOM, nom);
        editor.putString(BORNEARTH_PRENOM, prenom);
        editor.apply();
    }

    public static boolean birthDateAvailable() {
        SharedPreferences sp = bornEarthSharePrefs.context.getSharedPreferences(BORNEARTH_NAME, MODE_PRIVATE);
        return sp.getInt(BORNEARTH_YEAR, 0) != 0;
    }

    /*************************************
     *              TOOLS
     *************************************/

    private static void loadBirthDate() {
        SharedPreferences sp = bornEarthSharePrefs.context.getSharedPreferences(BORNEARTH_NAME, MODE_PRIVATE);
        year = sp.getInt(BORNEARTH_YEAR, 0);
        monht = sp.getInt(BORNEARTH_MON, 0);
        day = sp.getInt(BORNEARTH_DAY, 0);
    }

    private static void loadBirthTime() {
        SharedPreferences sp = bornEarthSharePrefs.context.getSharedPreferences(BORNEARTH_NAME, MODE_PRIVATE);
        hours = sp.getInt(BORNEARTH_HOUR, 0);
        minutes = sp.getInt(BORNEARTH_MINUTE, 0);
        secondes = sp.getInt(BORNEARTH_SECONDE, 0);
    }

    private static void loadPersonalInfo() {
        SharedPreferences sp = bornEarthSharePrefs.context.getSharedPreferences(BORNEARTH_NAME, MODE_PRIVATE);
        nom = sp.getString(BORNEARTH_NOM, "");
        prenom = sp.getString(BORNEARTH_PRENOM, "");
    }
}
