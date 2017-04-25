/*
 * Copyright - All Rights Reserved
 */
package com.bornearth.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bornearth.utils.BornearthConst;

/**
 * @author Marcellin RWEGO
 * @since 1.0.0 [19/02/2017]
 */
public abstract class DAOBase {
    protected SQLiteDatabase bornearthDb = null;
    protected DatabaseHandler handler = null;

    public DAOBase(Context context) {
        this.handler = new DatabaseHandler(context, BornearthConst.BORNEARTH_DB_NAME, null, BornearthConst.DB_VERSION);
    }

    public SQLiteDatabase open() {
        // Pas besoin de fermer la dernière base puisque getWritableDatabase s'en charge
        bornearthDb = handler.getWritableDatabase();
        return bornearthDb;
    }

    public void close() {
        bornearthDb.close();
    }

    public SQLiteDatabase getBornearthDb() {
        return bornearthDb;
    }
}
