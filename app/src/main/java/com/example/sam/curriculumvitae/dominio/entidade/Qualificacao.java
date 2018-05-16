package com.example.sam.curriculumvitae.dominio.entidade;

import java.io.Serializable;

public class Qualificacao implements Serializable {
    public int codigo;
    public String atividade;
    public String descricao;

    public Qualificacao() {
        this.codigo = 0;
    }
}
