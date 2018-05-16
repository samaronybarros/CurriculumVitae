package com.example.sam.curriculumvitae.dominio.entidade;

import java.io.Serializable;

public class Formacao implements Serializable {
    public int codigo;
    public String curso;
    public String instituicao;
    public String status;
    public String dataInicio;
    public String dataConclusao;

    public Formacao() {
        this.codigo = 0;
    }
}
