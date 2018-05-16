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

    public static String createTableFormacao() {
        StringBuilder sql = new StringBuilder();

        sql.append(" CREATE TABLE IF NOT EXISTS FORMACAO( ");
        sql.append("    CODIGO           INTEGER         PRIMARY KEY AUTOINCREMENT NOT NULL,");
        sql.append("    CURSO            VARCHAR(250)    NOT NULL DEFAULT(''), ");
        sql.append("    INSTITUICAO      VARCHAR(255)    NOT NULL DEFAULT(''), ");
        sql.append("    STATUS           VARCHAR(20)     NOT NULL DEFAULT(''), ");
        sql.append("    DATA_INICIO      VARCHAR(10)     NOT NULL DEFAULT(''), ");
        sql.append("    DATA_CONCLUSAO   VARCHAR(10)     NOT NULL DEFAULT('') ");
        sql.append(" ) ");

        return sql.toString();
    }

    public static String dropTableFormacao() {
        StringBuilder sql = new StringBuilder();

        sql.append(" DROP TABLE IF EXISTS FORMACAO; ");

        return sql.toString();
    }

    public static String createTableExperiencia() {
        StringBuilder sql = new StringBuilder();

        sql.append(" CREATE TABLE IF NOT EXISTS EXPERIENCIA( ");
        sql.append("    CODIGO           INTEGER         PRIMARY KEY AUTOINCREMENT NOT NULL,");
        sql.append("    EMPRESA          VARCHAR(50)     NOT NULL DEFAULT(''), ");
        sql.append("    CARGO            VARCHAR(50)     NOT NULL DEFAULT(''), ");
        sql.append("    ATIVIDADES       VARCHAR(255)    NOT NULL DEFAULT(''), ");
        sql.append("    DATA_INICIO      VARCHAR(10)     NOT NULL DEFAULT(''), ");
        sql.append("    DATA_FIM         VARCHAR(10)     NOT NULL DEFAULT(''), ");
        sql.append("    TRABALHO_ATUAL   VARCHAR(03)     NOT NULL DEFAULT('') ");
        sql.append(" ) ");

        return sql.toString();
    }

    public static String dropTableExperiencia() {
        StringBuilder sql = new StringBuilder();

        sql.append(" DROP TABLE IF EXISTS EXPERIENCIA; ");

        return sql.toString();
    }

    public static String createTableCurso() {
        StringBuilder sql = new StringBuilder();

        sql.append(" CREATE TABLE IF NOT EXISTS CURSO( ");
        sql.append("    CODIGO           INTEGER         PRIMARY KEY AUTOINCREMENT NOT NULL,");
        sql.append("    CURSO            VARCHAR(250)    NOT NULL DEFAULT(''), ");
        sql.append("    INSTITUICAO      VARCHAR(255)    NOT NULL DEFAULT(''), ");
        sql.append("    STATUS           VARCHAR(20)     NOT NULL DEFAULT(''), ");
        sql.append("    DATA_INICIO      VARCHAR(10)     NOT NULL DEFAULT(''), ");
        sql.append("    DATA_CONCLUSAO   VARCHAR(10)     NOT NULL DEFAULT(''), ");
        sql.append("    DESCRICAO        VARCHAR(10)     NOT NULL DEFAULT('')  ");
        sql.append(" ) ");

        return sql.toString();
    }

    public static String dropTableCurso() {
        StringBuilder sql = new StringBuilder();

        sql.append(" DROP TABLE IF EXISTS CURSO; ");

        return sql.toString();
    }

    public static String createTableQualificacao() { //Qualificações e Atividades Profissionais
        StringBuilder sql = new StringBuilder();

        sql.append(" CREATE TABLE IF NOT EXISTS QUALIFICACAO( ");
        sql.append("    CODIGO           INTEGER          PRIMARY KEY AUTOINCREMENT NOT NULL,");
        sql.append("    ATIVIDADE        VARCHAR(50)      NOT NULL DEFAULT(''), ");
        sql.append("    DESCRICAO        VARCHAR(100)     NOT NULL DEFAULT('')  ");
        sql.append(" ) ");

        return sql.toString();
    }

    public static String dropTableQualificacao() {
        StringBuilder sql = new StringBuilder();

        sql.append(" DROP TABLE IF EXISTS QUALIFICACAO; ");

        return sql.toString();
    }

    public static String createTableIdioma() { //Qualificações e Atividades Profissionais
        StringBuilder sql = new StringBuilder();

        sql.append(" CREATE TABLE IF NOT EXISTS IDIOMA( ");
        sql.append("    CODIGO       INTEGER         PRIMARY KEY AUTOINCREMENT NOT NULL,");
        sql.append("    IDIOMA       VARCHAR(10)     NOT NULL DEFAULT(''), ");
        sql.append("    NIVEL        VARCHAR(10)     NOT NULL DEFAULT('')  ");
        sql.append(" ) ");

        return sql.toString();
    }

    public static String dropTableIdioma() {
        StringBuilder sql = new StringBuilder();

        sql.append(" DROP TABLE IF EXISTS IDIOMA; ");

        return sql.toString();
    }

    public static String createTableApresentacao() {
        StringBuilder sql = new StringBuilder();

        sql.append(" CREATE TABLE IF NOT EXISTS APRESENTACAO( ");
        sql.append("    CODIGO               INTEGER         PRIMARY KEY AUTOINCREMENT NOT NULL,");
        sql.append("    DESCRICAO            VARCHAR(250)    NOT NULL DEFAULT('') ");
        sql.append(" ) ");

        return sql.toString();
    }

    public static String dropTableApresentacao() {
        StringBuilder sql = new StringBuilder();

        sql.append(" DROP TABLE IF EXISTS APRESENTACAO; ");

        return sql.toString();
    }
}