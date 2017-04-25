/*
 * Copyright - All Rights Reserved
 */
package com.bornearth.utils;

/**
 * @author Marcellin RWEGO
 * @since 1.0.0 [19/02/2017]
 */
public final class BornearthConst {
    /***********************************************************************************************
     *                          INFOS BASE DE DONNEES
     ***********************************************************************************************/
    // Version de la base de donnees
    public static final int DB_VERSION = 1;
    // Nom de la base de donnees bornearth
    public static final String BORNEARTH_DB_NAME = "bornearth_database.db";


    /***********************************************************************************************
     *                          DEFINITION DES TABLES
     ***********************************************************************************************/
    /**
     * TABLE CONTENANT LES INFORMATIONS DES IMAGES
     */
    public static final String ID = "ID";
    public static final String DATE_IMG = "DATE_IMG";
    public static final String DESC_IMG = "DESC_IMG";
    public static final String NOM_IMG_MIN = "NOM_IMG_MIN";
    public static final String NOM_IMG_REEL = "NOM_IMG_REEL";
    public static final String CLE_ETRANGER_USER = "CLE_ETRANGER_USER";
    public static final String BORNEARTH_IMAGES_TABLE_NAME = "BORNEARTH_IMAGES";
    public static final String VIDER_TABLE_IMAGES_CONST = "delete from " + BORNEARTH_IMAGES_TABLE_NAME;
    public static final String SELECT_COUNT_ETOILE = "select count(*) from " + BORNEARTH_IMAGES_TABLE_NAME;
    public static final String BORNEARTH_IMAGES_TABLE_DROP = "DROP TABLE IF EXISTS " + BORNEARTH_IMAGES_TABLE_NAME + " ;";
    public static final String BORNEARTH_IMAGES_TABLE_CREATE =
            "CREATE TABLE "      +  BORNEARTH_IMAGES_TABLE_NAME + " (" +
                    ID                  + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CLE_ETRANGER_USER   + " INTEGER DEFAULT NULL, " +
                    NOM_IMG_MIN         + " TEXT DEFAULT \" \", " +
                    NOM_IMG_REEL        + " TEXT NOT NULL, " +
                    DATE_IMG            + " TEXT DEFAULT \" \", " +
                    DESC_IMG            + " TEXT DEFAULT \" \" );";


    /***********************************************************************************************
     *                          AUTRES INFOS UTILISES
     ***********************************************************************************************/
    public static final int ONE_DAY = 1;
    public static final String DATE_PATTERN = "dd/MM/yyyy";
    public static final String PAS_DE_DESC = "Description absente";
    public static final String IMAGE_URI_STR = "positionInt";
    public static final String IMAGE_DESC = "imageDateChargement";



}
