package com.example.sam.curriculumvitae.dominio.entidade;

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
}