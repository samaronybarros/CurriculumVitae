package com.example.sam.curriculumvitae.dominio.entidade;

import android.text.TextUtils;

import java.io.Serializable;

public class Experiencia implements Serializable {
    public int codigo;
    public String empresa;
    public String cargo;
    public String atividades;
    public String dataInicio;
    public String dataFim;
    public String trabalhoAtual; //SIM/NÃO

    public Experiencia() {
        this.codigo = 0;
        this.trabalhoAtual = "N";
    }

    private boolean isCampoVazio(String campo) {
        boolean ret = (TextUtils.isEmpty(campo) || campo.trim().isEmpty());

        return ret;
    }

    public boolean isDataFimVazia() {
        return isCampoVazio(dataFim);
    }
}