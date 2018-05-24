package com.example.sam.curriculumvitae.dominio.entidade;

import android.text.TextUtils;

import java.io.Serializable;

public class Curso implements Serializable {
    public int codigo;
    public String curso;
    public String instituicao;
    public String status;
    public String dataInicio;
    public String dataConclusao;
    public String descricao;

    public Curso() {
        this.codigo = 0;
    }

    private boolean isCampoVazio(String campo) {
        boolean ret = (TextUtils.isEmpty(campo) || campo.trim().isEmpty());

        return ret;
    }

    public boolean isDataConclusaoVazia() {
        return isCampoVazio(dataConclusao);
    }
}
