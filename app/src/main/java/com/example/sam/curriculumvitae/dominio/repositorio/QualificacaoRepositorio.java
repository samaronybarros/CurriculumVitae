package com.example.sam.curriculumvitae.dominio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sam.curriculumvitae.dominio.entidade.Qualificacao;

import java.util.ArrayList;
import java.util.List;

public class QualificacaoRepositorio {
    private SQLiteDatabase conexao;

    public QualificacaoRepositorio(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public void inserir(Qualificacao qualificacao) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("ATIVIDADE", qualificacao.atividade);
        contentValues.put("DESCRICAO", qualificacao.descricao);

        this.conexao.insertOrThrow("QUALIFICACAO", null, contentValues);
    }

    public void alterar(Qualificacao qualificacao) {
        ContentValues contentValues = new ContentValues();
        String[] parametros = new String[1];

        parametros[0] = String.valueOf(qualificacao.codigo);

        contentValues.put("ATIVIDADE", qualificacao.atividade);
        contentValues.put("DESCRICAO", qualificacao.descricao);

        this.conexao.update("QUALIFICACAO", contentValues,"CODIGO = ?", parametros);
    }

    public void excluir(int codigo) {
        String[] parametros = new String[1];

        parametros[0] = String.valueOf(codigo);

        this.conexao.delete("QUALIFICACAO", "CODIGO = ?", parametros);
    }

    public Qualificacao buscar(int codigo) {
        Qualificacao qualificacao = new Qualificacao();
        StringBuilder sql = new StringBuilder();
        String[] parametros = new String[1];
        Cursor resultado;

        parametros[0] = String.valueOf(codigo);

        sql.append(" SELECT             ");
        sql.append(" ATIVIDADE      ,   ");
        sql.append(" DESCRICAO          ");
        sql.append(" FROM               ");
        sql.append(" QUALIFICACAO       ");
        sql.append(" WHERE CODIGO = ?   ");

        resultado = this.conexao.rawQuery(sql.toString(), parametros);

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();

            qualificacao.codigo     = resultado.getInt(resultado.getColumnIndexOrThrow("CODIGO"));
            qualificacao.atividade  = resultado.getString(resultado.getColumnIndexOrThrow("ATIVIDADE"));
            qualificacao.descricao  = resultado.getString(resultado.getColumnIndexOrThrow("DESCRICAO"));

            return qualificacao;
        }

        return null;
    }

    public List<Qualificacao> buscarTodasQualificacoes() {
        List<Qualificacao> qualificacao = new ArrayList<Qualificacao>();
        StringBuilder sql = new StringBuilder();
        Cursor resultado;

        sql.append(" SELECT             ");
        sql.append(" ATIVIDADE      ,   ");
        sql.append(" DESCRICAO          ");
        sql.append(" FROM               ");
        sql.append(" QUALIFICACAO       ");

        resultado = this.conexao.rawQuery(sql.toString(), null);

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();

            do {
                Qualificacao q = new Qualificacao();

                q.codigo    = resultado.getInt(resultado.getColumnIndexOrThrow("CODIGO"));
                q.atividade = resultado.getString(resultado.getColumnIndexOrThrow("ATIVIDADE"));
                q.descricao = resultado.getString(resultado.getColumnIndexOrThrow("DESCRICAO"));

                qualificacao.add(q);
            } while (resultado.moveToNext());
        }

        return qualificacao;
    }

}
