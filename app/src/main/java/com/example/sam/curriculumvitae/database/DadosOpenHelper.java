package com.example.sam.curriculumvitae.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DadosOpenHelper extends SQLiteOpenHelper {

    public DadosOpenHelper(Context context) {
        super(context, "Dados", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ScriptDDL.createTableInfoPessoais());
        db.execSQL(ScriptDDL.createTableObjetivo());
        db.execSQL(ScriptDDL.createTableFormacao());
        db.execSQL(ScriptDDL.createTableExperiencia());
        db.execSQL(ScriptDDL.createTableCurso());
        db.execSQL(ScriptDDL.createTableQualificacao());
        db.execSQL(ScriptDDL.createTableIdioma());
        db.execSQL(ScriptDDL.createTableApresentacao());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ScriptDDL.dropTableInfoPessoais());
        db.execSQL(ScriptDDL.dropTableObjetivo());
        db.execSQL(ScriptDDL.dropTableFormacao());
        db.execSQL(ScriptDDL.dropTableExperiencia());
        db.execSQL(ScriptDDL.dropTableCurso());
        db.execSQL(ScriptDDL.dropTableQualificacao());
        db.execSQL(ScriptDDL.dropTableIdioma());
        db.execSQL(ScriptDDL.dropTableApresentacao());

        onCreate(db);
    }
}
