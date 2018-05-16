package com.example.sam.curriculumvitae.dominio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sam.curriculumvitae.dominio.entidade.Curso;

import java.util.ArrayList;
import java.util.List;

public class CursoRepositorio {
    private SQLiteDatabase conexao;

    public CursoRepositorio(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public void inserir(Curso curso) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("CURSO", curso.curso);
        contentValues.put("INSTITUICAO", curso.instituicao);
        contentValues.put("STATUS", curso.status);
        contentValues.put("DATA_INICIO", curso.dataInicio);
        contentValues.put("DATA_CONCLUSAO", curso.dataConclusao);
        contentValues.put("DESCRICAO", curso.descricao);

        this.conexao.insertOrThrow("CURSO", null, contentValues);
    }

    public void alterar(Curso curso) {
        ContentValues contentValues = new ContentValues();
        String[] parametros = new String[1];

        parametros[0] = String.valueOf(curso.codigo);

        contentValues.put("CURSO", curso.curso);
        contentValues.put("INSTITUICAO", curso.instituicao);
        contentValues.put("STATUS", curso.status);
        contentValues.put("DATA_INICIO", curso.dataInicio);
        contentValues.put("DATA_CONCLUSAO", curso.dataConclusao);
        contentValues.put("DESCRICAO", curso.descricao);

        this.conexao.update("CURSO", contentValues,"CODIGO = ?", parametros);
    }

    public void excluir(int codigo) {
        String[] parametros = new String[1];

        parametros[0] = String.valueOf(codigo);

        this.conexao.delete("CURSO", "CODIGO = ?", parametros);
    }

    public Curso buscar(int codigo) {
        Curso curso = new Curso();
        StringBuilder sql = new StringBuilder();
        String[] parametros = new String[1];
        Cursor resultado;

        parametros[0] = String.valueOf(codigo);

        sql.append(" SELECT             ");
        sql.append(" CODIGO         ,   ");
        sql.append(" INSTITUICAO    ,   ");
        sql.append(" STATUS         ,   ");
        sql.append(" DATA_INICIO    ,   ");
        sql.append(" DATA_CONCLUSAO ,   ");
        sql.append(" DESCRICAO          ");
        sql.append(" FROM               ");
        sql.append(" CURSO              ");
        sql.append(" WHERE CODIGO = ?   ");

        resultado = this.conexao.rawQuery(sql.toString(), parametros);

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();

            curso.codigo         = resultado.getInt(resultado.getColumnIndexOrThrow("CODIGO"));
            curso.curso          = resultado.getString(resultado.getColumnIndexOrThrow("CURSO"));
            curso.instituicao    = resultado.getString(resultado.getColumnIndexOrThrow("INSTITUICAO"));
            curso.status         = resultado.getString(resultado.getColumnIndexOrThrow("STATUS"));
            curso.dataInicio     = resultado.getString(resultado.getColumnIndexOrThrow("DATA_INICIO"));
            curso.dataConclusao  = resultado.getString(resultado.getColumnIndexOrThrow("DATA_CONCLUSAO"));
            curso.descricao      = resultado.getString(resultado.getColumnIndexOrThrow("DESCRICAO"));

            return curso;
        }

        return null;
    }

    public List<Curso> buscarTodosCursos() {
        List<Curso> curso = new ArrayList<Curso>();
        StringBuilder sql = new StringBuilder();
        Cursor resultado;

        sql.append(" SELECT             ");
        sql.append(" CODIGO         ,   ");
        sql.append(" CURSO          ,   ");
        sql.append(" INSTITUICAO    ,   ");
        sql.append(" STATUS         ,   ");
        sql.append(" DATA_INICIO    ,   ");
        sql.append(" DATA_CONCLUSAO ,   ");
        sql.append(" DESCRICAO          ");
        sql.append(" FROM               ");
        sql.append(" CURSO              ");

        resultado = this.conexao.rawQuery(sql.toString(), null);

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();

            do {
                Curso c = new Curso();

                c.codigo         = resultado.getInt(resultado.getColumnIndexOrThrow("CODIGO"));
                c.curso          = resultado.getString(resultado.getColumnIndexOrThrow("CURSO"));
                c.instituicao    = resultado.getString(resultado.getColumnIndexOrThrow("INSTITUICAO"));
                c.status         = resultado.getString(resultado.getColumnIndexOrThrow("STATUS"));
                c.dataInicio     = resultado.getString(resultado.getColumnIndexOrThrow("DATA_INICIO"));
                c.dataConclusao  = resultado.getString(resultado.getColumnIndexOrThrow("DATA_CONCLUSAO"));
                c.descricao      = resultado.getString(resultado.getColumnIndexOrThrow("DESCRICAO"));

                curso.add(c);
            } while (resultado.moveToNext());
        }

        return curso;
    }
}
