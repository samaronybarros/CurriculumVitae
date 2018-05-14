package com.example.sam.curriculumvitae.database;

public class ScriptDDL {
    public static String createTableInfoPessoais() {
        StringBuilder sql = new StringBuilder();

        sql.append(" CREATE TABLE IF NOT EXISTS INFO_PESSOAIS( ");
        sql.append("    CODIGO               INTEGER         PRIMARY KEY AUTOINCREMENT NOT NULL,");
        sql.append("    NOME                 VARCHAR(250)    NOT NULL DEFAULT(''), ");
        sql.append("    NACIONALIDADE        VARCHAR(255)    NOT NULL DEFAULT(''), ");
        sql.append("    ESTADO_CIVIL         VARCHAR(20)     NOT NULL DEFAULT(''), ");
        sql.append("    CIDADE_NASCIMENTO    VARCHAR(30)     NOT NULL DEFAULT(''), ");
        sql.append("    DATA_NASCIMENTO      VARCHAR(10)     NOT NULL DEFAULT(''), ");
        sql.append("    TELEFONE_CELULAR     VARCHAR(20)     NOT NULL DEFAULT(''), ");
        sql.append("    TELEFONE_FIXO        VARCHAR(20)     NOT NULL DEFAULT(''), ");
        sql.append("    EMAIL                VARCHAR(100)    NOT NULL DEFAULT(''), ");
        sql.append("    ENDERECO             VARCHAR(100)    NOT NULL DEFAULT(''), ");
        sql.append("    TIPO_CNH             VARCHAR(2)      NOT NULL DEFAULT('')  ");
        sql.append(" ) ");

        return sql.toString();
    }

    public static String dropTableInfoPessoais() {
        StringBuilder sql = new StringBuilder();

        sql.append(" DROP TABLE IF EXISTS INFO_PESSOAIS; ");

        return sql.toString();
    }

    public static String createTableObjetivo() {
        StringBuilder sql = new StringBuilder();

        sql.append(" CREATE TABLE IF NOT EXISTS OBJETIVO( ");
        sql.append("    CODIGO               INTEGER         PRIMARY KEY AUTOINCREMENT NOT NULL,");
        sql.append("    DESCRICAO            VARCHAR(250)    NOT NULL DEFAULT('') ");
        sql.append(" ) ");

        return sql.toString();
    }

    public static String dropTableObjetivo() {
        StringBuilder sql = new StringBuilder();

        sql.append(" DROP TABLE IF EXISTS OBJETIVO; ");

        return sql.toString();
    }
}