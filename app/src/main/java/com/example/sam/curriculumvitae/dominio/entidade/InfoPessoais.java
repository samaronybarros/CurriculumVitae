package com.example.sam.curriculumvitae.dominio.entidade;

import java.io.Serializable;

public class InfoPessoais implements Serializable {
    public int codigo;
    public String nome;
    public String nacionalidade;
    public String estadoCivil;
    public String cidadeNascimento;
    public String dataNascimento;
    public String telefoneCelular;
    public String telefoneFixo;
    public String email;
    public String endereco;
    public String tipoCNH;

    public InfoPessoais() {
        this.codigo = 0;
    }
}
