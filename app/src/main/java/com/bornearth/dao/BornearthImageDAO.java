/*
 * Copyright - All Rights Reserved
 */
package com.bornearth.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.bornearth.core.BornearthImage;
import com.bornearth.utils.BornearthConst;

import java.util.ArrayList;
import java.util.List;

import static com.bornearth.utils.BornearthConst.BORNEARTH_IMAGES_TABLE_NAME;
import static com.bornearth.utils.BornearthConst.CLE_ETRANGER_USER;
import static com.bornearth.utils.BornearthConst.DATE_IMG;
import static com.bornearth.utils.BornearthConst.DESC_IMG;
import static com.bornearth.utils.BornearthConst.NOM_IMG_MIN;
import static com.bornearth.utils.BornearthConst.NOM_IMG_REEL;

/**
 * @author Marcellin RWEGO
 * @since 1.0.0 [19/02/2017]
 */
public class BornearthImageDAO extends DAOBase {

    public BornearthImageDAO(Context context) {
        super(context);
    }

    /**
     * Ajoute une image dans la base de donnees si celle-ci n'est pas
     * @param bi l'image à ajouter à la base
     * @return  true si le nom de l'image a ete ajoute dans la base de donnees pour la premiere fois
     *          false si le nom de l'image y est deja
     */
    public boolean ajouter(BornearthImage bi) {
        // On verifie si l'image n'existe pas
        if (selectionner(bi.getNomImgReel()) != null) {
            return false;
        }

        ContentValues cv = new ContentValues();
        cv.put(CLE_ETRANGER_USER, bi.getCleEtrangereUser());
        cv.put(NOM_IMG_MIN, bi.getNomImgMin());
        cv.put(NOM_IMG_REEL, bi.getNomImgReel());
        cv.put(DATE_IMG, bi.getDateImage());
        cv.put(DESC_IMG, bi.getDescImage());
        bornearthDb.insert(BORNEARTH_IMAGES_TABLE_NAME, null, cv);

        return true;
    }

    /**
     * @param nomReelImg le nom reel de l' image à supprimer
     */
    public void supprimer(String nomReelImg) {
        bornearthDb.delete(BORNEARTH_IMAGES_TABLE_NAME, NOM_IMG_REEL + " = ?", new String[] {nomReelImg});
    }

    /**
     * @param bi @{link BornearthImage} à supprimer
     */
    public void supprimer(BornearthImage bi) {
        bornearthDb.delete(BORNEARTH_IMAGES_TABLE_NAME, NOM_IMG_REEL + " = ?", new String[] {bi.getNomImgReel()});
    }

    /**
     * Vide la table des images
     */
    public void supprimerToutesLesImages() {
        bornearthDb.execSQL(BornearthConst.VIDER_TABLE_IMAGES_CONST);
    }

    /**
     * @param nomReelImg le nom reel de l' image à récupérer
     */
    public BornearthImage selectionner(String nomReelImg) {
        BornearthImage bi = null;
        Cursor c = bornearthDb.rawQuery(
                "select * from " + BORNEARTH_IMAGES_TABLE_NAME +
                " where " + NOM_IMG_REEL + " = ?", new String[] {nomReelImg});
        if (c.getCount() > 0) {
            c.moveToFirst();
            bi = new BornearthImage(c.getLong(1), c.getString(4), c.getString(5), c.getString(2), c.getString(3));
        }
        c.close();

        return bi;
    }

    /**
     * Renvoi la liste de toutes les chemins des images stockées dans la base de données
     * @return : Liste des URL (String) des images
     */
    public List<BornearthImage> selectionner() {
        Cursor c = bornearthDb.rawQuery("select * from " + BORNEARTH_IMAGES_TABLE_NAME, null);
        List<BornearthImage> listBi = new ArrayList<>(c.getCount());

        while (c.moveToNext()) {
            BornearthImage bi = new BornearthImage(c.getLong(1), c.getString(4), c.getString(5), c.getString(2), c.getString(3));
            listBi.add(bi);
        }
        c.close();

        return listBi;
    }

    public int getNbrTotalImg() {
        int nbrTotalImg = 0;
        Cursor c = bornearthDb.rawQuery("select * from " + BORNEARTH_IMAGES_TABLE_NAME, null);
        nbrTotalImg = c.getCount();
        return nbrTotalImg;
    }
}
