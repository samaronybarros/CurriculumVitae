package com.example.sam.curriculumvitae.dominio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sam.curriculumvitae.dominio.entidade.Formacao;

import java.util.ArrayList;
import java.util.List;

public class FormacaoRepositorio {
    private SQLiteDatabase conexao;

    public FormacaoRepositorio(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public void inserir(Formacao formacao) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("CURSO", formacao.curso);
        contentValues.put("INSTITUICAO", formacao.instituicao);
        contentValues.put("STATUS", formacao.status);
        contentValues.put("DATA_INICIO", formacao.dataInicio);
        contentValues.put("DATA_CONCLUSAO", formacao.dataConclusao);

        this.conexao.insertOrThrow("FORMACAO", null, contentValues);
    }

    public void alterar(Formacao formacao) {
        ContentValues contentValues = new ContentValues();
        String[] parametros = new String[1];

        parametros[0] = String.valueOf(formacao.codigo);

        contentValues.put("CURSO", formacao.curso);
        contentValues.put("INSTITUICAO", formacao.instituicao);
        contentValues.put("STATUS", formacao.status);
        contentValues.put("DATA_INICIO", formacao.dataInicio);
        contentValues.put("DATA_CONCLUSAO", formacao.dataConclusao);

        this.conexao.update("FORMACAO", contentValues,"CODIGO = ?", parametros);
    }

    public void excluir(int codigo) {
        String[] parametros = new String[1];

        parametros[0] = String.valueOf(codigo);

        this.conexao.delete("FORMACAO", "CODIGO = ?", parametros);
    }

    public Formacao buscar(int codigo) {
        Formacao formacao = new Formacao();
        StringBuilder sql = new StringBuilder();
        String[] parametros = new String[1];
        Cursor resultado;

        parametros[0] = String.valueOf(codigo);

        sql.append(" SELECT             ");
        sql.append(" CODIGO         ,   ");
        sql.append(" INSTITUICAO    ,   ");
        sql.append(" STATUS         ,   ");
        sql.append(" DATA_INICIO    ,   ");
        sql.append(" DATA_CONCLUSAO     ");
        sql.append(" FROM               ");
        sql.append(" FORMACAO           ");
        sql.append(" WHERE CODIGO = ?   ");

        resultado = this.conexao.rawQuery(sql.toString(), parametros);

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();

            formacao.codigo         = resultado.getInt(resultado.getColumnIndexOrThrow("CODIGO"));
            formacao.curso          = resultado.getString(resultado.getColumnIndexOrThrow("CURSO"));
            formacao.instituicao    = resultado.getString(resultado.getColumnIndexOrThrow("INSTITUICAO"));
            formacao.status         = resultado.getString(resultado.getColumnIndexOrThrow("STATUS"));
            formacao.dataInicio     = resultado.getString(resultado.getColumnIndexOrThrow("DATA_INICIO"));
            formacao.dataConclusao  = resultado.getString(resultado.getColumnIndexOrThrow("DATA_CONCLUSAO"));

            return formacao;
        }

        resultado.close();

        return null;
    }

    public List<Formacao> buscarTodasFormacoes() {
        List<Formacao> formacao = new ArrayList<>();
        StringBuilder sql;
        Cursor resultado;

        sql = new StringBuilder();

        sql.append(" SELECT             ");
        sql.append(" CODIGO         ,   ");
        sql.append(" CURSO          ,   ");
        sql.append(" INSTITUICAO    ,   ");
        sql.append(" STATUS         ,   ");
        sql.append(" DATA_INICIO    ,   ");
        sql.append(" DATA_CONCLUSAO     ");
        sql.append(" FROM               ");
        sql.append(" FORMACAO           ");

        resultado = this.conexao.rawQuery(sql.toString(), null);

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();

            do {
                Formacao f = new Formacao();

                f.codigo         = resultado.getInt(resultado.getColumnIndexOrThrow("CODIGO"));
                f.curso          = resultado.getString(resultado.getColumnIndexOrThrow("CURSO"));
                f.instituicao    = resultado.getString(resultado.getColumnIndexOrThrow("INSTITUICAO"));
                f.status         = resultado.getString(resultado.getColumnIndexOrThrow("STATUS"));
                f.dataInicio     = resultado.getString(resultado.getColumnIndexOrThrow("DATA_INICIO"));
                f.dataConclusao  = resultado.getString(resultado.getColumnIndexOrThrow("DATA_CONCLUSAO"));

                formacao.add(f);
            } while (resultado.moveToNext());
        }

        resultado.close();

        return formacao;
    }
}
