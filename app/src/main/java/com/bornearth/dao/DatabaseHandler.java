/*
 * Copyright - All Rights Reserved
 */
package com.bornearth.dao;

import static com.bornearth.utils.BornearthConst.BORNEARTH_IMAGES_TABLE_DROP;
import static com.bornearth.utils.BornearthConst.BORNEARTH_IMAGES_TABLE_CREATE;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Marcellin RWEGO
 * @since 1.0.0 [19/02/2017]
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BORNEARTH_IMAGES_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(BORNEARTH_IMAGES_TABLE_DROP);
        onCreate(db);
    }
}
