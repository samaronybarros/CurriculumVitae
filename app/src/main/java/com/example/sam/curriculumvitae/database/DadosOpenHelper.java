package com.example.sam.curriculumvitae.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DadosOpenHelper extends SQLiteOpenHelper {

    public DadosOpenHelper(Context context) {
        super(context, "Dados", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ScriptDDL.createTableInfoPessoais());
        db.execSQL(ScriptDDL.createTableObjetivo());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ScriptDDL.dropTableInfoPessoais());
        db.execSQL(ScriptDDL.dropTableObjetivo());
        onCreate(db);
    }
}
