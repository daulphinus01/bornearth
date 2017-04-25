/*
 * Copyright - All Rights Reserved
 */
package com.bornearth.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;

import com.bornearth.core.BornearthImage;

import org.joda.time.LocalDate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcellin RWEGO
 * @since 1.0.0 [05/02/2017]
 */
public class BornearthUtils {

    private static final String FILE_SEPARATOR = "/";
    private static final String ASSETS_IMAGES_FOLDER = "gallery";
    private static final String ASSETS_PATH_NAME_PATTERN = "____gallery_borneath";

    /**
     * Parcourt les fichiers dans /assets/ et renvoie la liste des fichiers dont leurs noms est
     * de la forme suivante : XXX____gallery_borneathXXX X pouvant etre une chaine de caracteres
     * vide
     * @param context utilisee
     * @return liste de chaine de caracteres (nom d'image)
     * @throws IOException
     */
    public static List<String> getImagesPathFromAssets(Context context) throws IOException {
        AssetManager assetManager = context.getAssets();
        String[] files = assetManager.list(ASSETS_IMAGES_FOLDER);
        List<String> sortedFileNames = new ArrayList<>();
        for (String fileName : files) {
            if (fileName.contains(ASSETS_PATH_NAME_PATTERN)) {
                sortedFileNames.add(ASSETS_IMAGES_FOLDER + FILE_SEPARATOR + fileName);
            }
        }
        return sortedFileNames;
    }

    /**
     * A partir d'un URI, cette methode creer un objet @{link BornearthImage}
     * @param selectedUri : Uri a partir duquel on contruira l'objet @{link BornearthImage}
     * @return @{link BornearthImage} cree
     */
    public static BornearthImage buildBornearthImageFromUri(Uri selectedUri, String descImg, String nomImgMin) {
        BornearthImage bornearthImage = null;
        String dateImg = LocalDate.now().toString(BornearthConst.DATE_PATTERN);
        bornearthImage = new BornearthImage(0, dateImg, descImg, nomImgMin, selectedUri.toString());
        return bornearthImage;
    }
}
