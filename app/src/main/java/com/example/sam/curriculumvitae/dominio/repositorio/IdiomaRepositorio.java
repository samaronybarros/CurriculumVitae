package com.example.sam.curriculumvitae.dominio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sam.curriculumvitae.dominio.entidade.Idioma;

import java.util.ArrayList;
import java.util.List;

public class IdiomaRepositorio {
    private SQLiteDatabase conexao;

    public IdiomaRepositorio(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public void inserir(Idioma idioma) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("IDIOMA", idioma.idioma);
        contentValues.put("NIVEL", idioma.nivel);

        this.conexao.insertOrThrow("IDIOMA", null, contentValues);
    }

    public void alterar(Idioma idioma) {
        ContentValues contentValues = new ContentValues();
        String[] parametros = new String[1];

        parametros[0] = String.valueOf(idioma.codigo);

        contentValues.put("IDIOMA", idioma.idioma);
        contentValues.put("NIVEL", idioma.nivel);

        this.conexao.update("IDIOMA", contentValues,"CODIGO = ?", parametros);
    }

    public void excluir(int codigo) {
        String[] parametros = new String[1];

        parametros[0] = String.valueOf(codigo);

        this.conexao.delete("IDIOMA", "CODIGO = ?", parametros);
    }

    public Idioma buscar(int codigo) {
        Idioma idioma = new Idioma();
        StringBuilder sql = new StringBuilder();
        String[] parametros = new String[1];
        Cursor resultado;

        parametros[0] = String.valueOf(codigo);

        sql.append(" SELECT             ");
        sql.append(" CODIGO         ,   ");
        sql.append(" IDIOMA         ,   ");
        sql.append(" NIVEL              ");
        sql.append(" FROM               ");
        sql.append(" IDIOMA             ");
        sql.append(" WHERE CODIGO = ?   ");

        resultado = this.conexao.rawQuery(sql.toString(), parametros);

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();

            idioma.codigo = resultado.getInt(resultado.getColumnIndexOrThrow("CODIGO"));
            idioma.idioma = resultado.getString(resultado.getColumnIndexOrThrow("IDIOMA"));
            idioma.nivel  = resultado.getString(resultado.getColumnIndexOrThrow("NIVEL"));

            return idioma;
        }

        resultado.close();

        return null;
    }

    public List<Idioma> buscarTodosIdiomas() {
        List<Idioma> idioma = new ArrayList<>();
        StringBuilder sql;
        Cursor resultado;

        sql = new StringBuilder();

        sql.append(" SELECT             ");
        sql.append(" CODIGO         ,   ");
        sql.append(" IDIOMA         ,   ");
        sql.append(" NIVEL              ");
        sql.append(" FROM               ");
        sql.append(" IDIOMA             ");

        resultado = this.conexao.rawQuery(sql.toString(), null);

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();

            do {
                Idioma i = new Idioma();

                i.codigo = resultado.getInt(resultado.getColumnIndexOrThrow("CODIGO"));
                i.idioma = resultado.getString(resultado.getColumnIndexOrThrow("IDIOMA"));
                i.nivel  = resultado.getString(resultado.getColumnIndexOrThrow("NIVEL"));

                idioma.add(i);
            } while (resultado.moveToNext());
        }

        resultado.close();

        return idioma;
    }
}