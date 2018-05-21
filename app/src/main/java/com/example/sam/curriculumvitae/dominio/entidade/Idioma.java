package com.example.sam.curriculumvitae.dominio.entidade;

import java.io.Serializable;

public class Idioma implements Serializable {
    public int codigo;
    public String idioma;
    public String nivel;

    public Idioma() {
        this.codigo = 0;
    }
}
