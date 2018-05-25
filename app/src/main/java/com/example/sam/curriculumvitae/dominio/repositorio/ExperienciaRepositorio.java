package com.example.sam.curriculumvitae.dominio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sam.curriculumvitae.dominio.entidade.Experiencia;

import java.util.ArrayList;
import java.util.List;

public class ExperienciaRepositorio {
    private SQLiteDatabase conexao;

    public ExperienciaRepositorio(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public void inserir(Experiencia experiencia) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("EMPRESA", experiencia.empresa);
        contentValues.put("CARGO", experiencia.cargo);
        contentValues.put("ATIVIDADES", experiencia.atividades);
        contentValues.put("DATA_INICIO", experiencia.dataInicio);
        contentValues.put("DATA_FIM", experiencia.dataFim);
        contentValues.put("TRABALHO_ATUAL", experiencia.trabalhoAtual);

        this.conexao.insertOrThrow("EXPERIENCIA", null, contentValues);
    }

    public void alterar(Experiencia experiencia) {
        ContentValues contentValues = new ContentValues();
        String[] parametros = new String[1];

        parametros[0] = String.valueOf(experiencia.codigo);

        contentValues.put("EMPRESA", experiencia.empresa);
        contentValues.put("CARGO", experiencia.cargo);
        contentValues.put("ATIVIDADES", experiencia.atividades);
        contentValues.put("DATA_INICIO", experiencia.dataInicio);
        contentValues.put("DATA_FIM", experiencia.dataFim);
        contentValues.put("TRABALHO_ATUAL", experiencia.trabalhoAtual);

        this.conexao.update("EXPERIENCIA", contentValues,"CODIGO = ?", parametros);
    }

    public void excluir(int codigo) {
        String[] parametros = new String[1];

        parametros[0] = String.valueOf(codigo);

        this.conexao.delete("EXPERIENCIA", "CODIGO = ?", parametros);
    }

    public Experiencia buscar(int codigo) {
        Experiencia experiencia = new Experiencia();
        StringBuilder sql = new StringBuilder();
        String[] parametros = new String[1];
        Cursor resultado;

        parametros[0] = String.valueOf(codigo);

        sql.append(" SELECT             ");
        sql.append(" CODIGO         ,   ");
        sql.append(" EMPRESA        ,   ");
        sql.append(" CARGO          ,   ");
        sql.append(" ATIVIDADES     ,   ");
        sql.append(" DATA_INICIO    ,   ");
        sql.append(" DATA_FIM       ,   ");
        sql.append(" TRABALHO_ATUAL     ");
        sql.append(" FROM               ");
        sql.append(" EXPERIENCIA        ");
        sql.append(" WHERE CODIGO = ?   ");

        resultado = this.conexao.rawQuery(sql.toString(), parametros);

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();

            experiencia.codigo          = resultado.getInt(resultado.getColumnIndexOrThrow("CODIGO"));
            experiencia.empresa         = resultado.getString(resultado.getColumnIndexOrThrow("EMPRESA"));
            experiencia.cargo           = resultado.getString(resultado.getColumnIndexOrThrow("CARGO"));
            experiencia.atividades      = resultado.getString(resultado.getColumnIndexOrThrow("ATIVIDADES"));
            experiencia.dataInicio      = resultado.getString(resultado.getColumnIndexOrThrow("DATA_INICIO"));
            experiencia.dataFim         = resultado.getString(resultado.getColumnIndexOrThrow("DATA_FIM"));
            experiencia.trabalhoAtual   = resultado.getString(resultado.getColumnIndexOrThrow("TRABALHO_ATUAL"));

            return experiencia;
        }

        resultado.close();

        return null;
    }

    public List<Experiencia> buscarTodasExperiencias() {
        List<Experiencia> experiencia = new ArrayList<>();
        StringBuilder sql;
        Cursor resultado;

        sql = new StringBuilder();

        sql.append(" SELECT             ");
        sql.append(" CODIGO         ,   ");
        sql.append(" EMPRESA        ,   ");
        sql.append(" CARGO          ,   ");
        sql.append(" ATIVIDADES     ,   ");
        sql.append(" DATA_INICIO    ,   ");
        sql.append(" DATA_FIM       ,   ");
        sql.append(" TRABALHO_ATUAL     ");
        sql.append(" FROM               ");
        sql.append(" EXPERIENCIA        ");

        resultado = this.conexao.rawQuery(sql.toString(), null);

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();

            do {
                Experiencia e = new Experiencia();

                e.codigo        = resultado.getInt(resultado.getColumnIndexOrThrow("CODIGO"));
                e.empresa       = resultado.getString(resultado.getColumnIndexOrThrow("EMPRESA"));
                e.cargo         = resultado.getString(resultado.getColumnIndexOrThrow("CARGO"));
                e.atividades    = resultado.getString(resultado.getColumnIndexOrThrow("ATIVIDADES"));
                e.dataInicio    = resultado.getString(resultado.getColumnIndexOrThrow("DATA_INICIO"));
                e.dataFim       = resultado.getString(resultado.getColumnIndexOrThrow("DATA_FIM"));
                e.trabalhoAtual = resultado.getString(resultado.getColumnIndexOrThrow("TRABALHO_ATUAL"));

                experiencia.add(e);
            } while (resultado.moveToNext());
        }

        resultado.close();

        return experiencia;
    }
}