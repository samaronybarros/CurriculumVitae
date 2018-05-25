package com.example.sam.curriculumvitae.dominio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sam.curriculumvitae.dominio.entidade.Objetivo;

public class ObjetivoRepositorio {
    private SQLiteDatabase conexao;

    public ObjetivoRepositorio(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public void inserir(Objetivo objetivo) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("DESCRICAO", objetivo.descricao);

        this.conexao.insertOrThrow("OBJETIVO", null, contentValues);
    }

    public void alterar(Objetivo objetivo) {
        ContentValues contentValues = new ContentValues();
        String[] parametros = new String[1];

        parametros[0] = String.valueOf(objetivo.codigo);

        contentValues.put("DESCRICAO", objetivo.descricao);

        this.conexao.update("OBJETIVO", contentValues,"CODIGO = ?", parametros);
    }

    public void excluir(int codigo) {
        String[] parametros = new String[1];

        parametros[0] = String.valueOf(codigo);

        this.conexao.delete("OBJETIVO", "CODIGO = ?", parametros);
    }

    public Objetivo buscar() {
        Objetivo objetivo = new Objetivo();
        StringBuilder sql;
        Cursor resultado;

        sql = new StringBuilder();

        sql.append(" SELECT     ");
        sql.append(" CODIGO   , ");
        sql.append(" DESCRICAO  ");
        sql.append(" FROM       ");
        sql.append(" OBJETIVO   ");

        resultado = this.conexao.rawQuery(sql.toString(), null);

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();

            objetivo.codigo    = resultado.getInt(resultado.getColumnIndexOrThrow("CODIGO"));
            objetivo.descricao = resultado.getString(resultado.getColumnIndexOrThrow("DESCRICAO"));

            return objetivo;
        }

        resultado.close();

        return null;
    }
}
