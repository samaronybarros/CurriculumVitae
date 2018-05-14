package com.example.sam.curriculumvitae.dominio.entidade;

import java.io.Serializable;

public class Objetivo implements Serializable {
    public int codigo;
    public String descricao;

    public Objetivo() {
        this.codigo = 0;
    }
}
