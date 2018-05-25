package com.example.sam.curriculumvitae.dominio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sam.curriculumvitae.dominio.entidade.InfoPessoais;

public class InfoPessoaisRepositorio {
    private SQLiteDatabase conexao;

    public InfoPessoaisRepositorio(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public void inserir(InfoPessoais infoPessoais) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("NOME",               infoPessoais.nome);
        contentValues.put("NACIONALIDADE",      infoPessoais.nacionalidade);
        contentValues.put("ESTADO_CIVIL",       infoPessoais.estadoCivil);
        contentValues.put("CIDADE_NASCIMENTO",  infoPessoais.cidadeNascimento);
        contentValues.put("DATA_NASCIMENTO",    infoPessoais.dataNascimento);
        contentValues.put("TELEFONE_CELULAR",   infoPessoais.telefoneCelular);
        contentValues.put("TELEFONE_FIXO",      infoPessoais.telefoneFixo);
        contentValues.put("ENDERECO",           infoPessoais.endereco);
        contentValues.put("EMAIL",           infoPessoais.email);
        contentValues.put("TIPO_CNH",           infoPessoais.tipoCNH);

        this.conexao.insertOrThrow("INFO_PESSOAIS", null, contentValues);
    }

    public void alterar(InfoPessoais infoPessoais) {
        ContentValues contentValues = new ContentValues();
        String[] parametros= new String[1];

        parametros[0] = String.valueOf(infoPessoais.codigo);

        contentValues.put("NOME",               infoPessoais.nome);
        contentValues.put("NACIONALIDADE",      infoPessoais.nacionalidade);
        contentValues.put("ESTADO_CIVIL",       infoPessoais.estadoCivil);
        contentValues.put("CIDADE_NASCIMENTO",  infoPessoais.cidadeNascimento);
        contentValues.put("DATA_NASCIMENTO",    infoPessoais.dataNascimento);
        contentValues.put("TELEFONE_CELULAR",   infoPessoais.telefoneCelular);
        contentValues.put("TELEFONE_FIXO",      infoPessoais.telefoneFixo);
        contentValues.put("EMAIL",              infoPessoais.email);
        contentValues.put("ENDERECO",           infoPessoais.endereco);
        contentValues.put("TIPO_CNH",           infoPessoais.tipoCNH);

        this.conexao.update("INFO_PESSOAIS", contentValues,"CODIGO = ?", parametros);
    }

    public void excluir (int codigo) {
        String[] parametros = new String[1];

        parametros[0] = String.valueOf(codigo);

        this.conexao.delete("INFO_PESSOAIS", "CODIGO = ?", parametros);
    }

    public InfoPessoais buscar() {
        InfoPessoais infoPessoais = new InfoPessoais();
        StringBuilder sql;
        Cursor resultado;

        sql = new StringBuilder();

        sql.append(" SELECT ");
        sql.append(" CODIGO               , ");
        sql.append(" NOME                 , ");
        sql.append(" NACIONALIDADE        , ");
        sql.append(" ESTADO_CIVIL         , ");
        sql.append(" CIDADE_NASCIMENTO    , ");
        sql.append(" DATA_NASCIMENTO      , ");
        sql.append(" TELEFONE_CELULAR     , ");
        sql.append(" TELEFONE_FIXO        , ");
        sql.append(" EMAIL                , ");
        sql.append(" ENDERECO             , ");
        sql.append(" TIPO_CNH               ");
        sql.append(" FROM                   ");
        sql.append(" INFO_PESSOAIS          ");

        resultado = this.conexao.rawQuery(sql.toString(), null);

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();

            infoPessoais.codigo             = resultado.getInt(resultado.getColumnIndexOrThrow("CODIGO"));
            infoPessoais.nome               = resultado.getString(resultado.getColumnIndexOrThrow("NOME"));
            infoPessoais.nacionalidade      = resultado.getString(resultado.getColumnIndexOrThrow("NACIONALIDADE"));
            infoPessoais.estadoCivil        = resultado.getString(resultado.getColumnIndexOrThrow("ESTADO_CIVIL"));
            infoPessoais.cidadeNascimento   = resultado.getString(resultado.getColumnIndexOrThrow("CIDADE_NASCIMENTO"));
            infoPessoais.dataNascimento     = resultado.getString(resultado.getColumnIndexOrThrow("DATA_NASCIMENTO"));
            infoPessoais.telefoneCelular    = resultado.getString(resultado.getColumnIndexOrThrow("TELEFONE_CELULAR"));
            infoPessoais.telefoneFixo       = resultado.getString(resultado.getColumnIndexOrThrow("TELEFONE_FIXO"));
            infoPessoais.email              = resultado.getString(resultado.getColumnIndexOrThrow("EMAIL"));
            infoPessoais.endereco           = resultado.getString(resultado.getColumnIndexOrThrow("ENDERECO"));
            infoPessoais.tipoCNH            = resultado.getString(resultado.getColumnIndexOrThrow("TIPO_CNH"));

            return infoPessoais;
        }

        resultado.close();

        return null;
    }
}
