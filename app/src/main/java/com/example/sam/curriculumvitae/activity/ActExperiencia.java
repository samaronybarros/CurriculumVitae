package com.example.sam.curriculumvitae.activity;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.example.sam.curriculumvitae.R;
import com.example.sam.curriculumvitae.adapter.ExperienciaAdapter;
import com.example.sam.curriculumvitae.database.DadosOpenHelper;
import com.example.sam.curriculumvitae.dominio.entidade.Experiencia;
import com.example.sam.curriculumvitae.dominio.repositorio.ExperienciaRepositorio;
import com.example.sam.curriculumvitae.mensagem.Mensagem;

import java.util.List;

public class ActExperiencia extends AppCompatActivity {
    private Experiencia experiencia;
    private ExperienciaRepositorio experienciaRepositorio;
    private ExperienciaAdapter experienciaAdapter;

    private DadosOpenHelper dadosOpenHelper;
    private SQLiteDatabase conexao;

    private Mensagem mensagem;

    private RecyclerView lstDadosExperiencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_experiencia);
        Toolbar toolbarExperiencia = (Toolbar) findViewById(R.id.toolbarExperiencia);
        setSupportActionBar(toolbarExperiencia);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarExperiencia.setTitle(R.string.title_experiencia);
        toolbarExperiencia.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainScreen();
            }
        });

        mensagem = new Mensagem();
        lstDadosExperiencia = (RecyclerView) findViewById(R.id.lstDadosExperiencia);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToExperienciaOperationScreen();
            }
        });

        criarConexao();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lstDadosExperiencia.setLayoutManager(linearLayoutManager);
        experienciaRepositorio = new ExperienciaRepositorio(conexao);

        List<Experiencia> dadosExperiencia = experienciaRepositorio.buscarTodasExperiencias();

        experienciaAdapter = new ExperienciaAdapter(dadosExperiencia);

        lstDadosExperiencia.setAdapter(experienciaAdapter);
    }

    public void goToMainScreen() {
        Intent intent = new Intent(ActExperiencia.this, ActTelaInicial.class);
        startActivity(intent);
    }

    public void goToExperienciaOperationScreen() {
        Intent intent = new Intent(ActExperiencia.this, ActExperienciaOperation.class);
        startActivity(intent);
    }

    private void criarConexao() {
        try {
            dadosOpenHelper = new DadosOpenHelper(this);
            conexao = dadosOpenHelper.getWritableDatabase();
            experienciaRepositorio = new ExperienciaRepositorio(conexao);
        } catch (SQLException ex) {
            mensagem.alert(this, getString(R.string.message_erro), ex.getMessage());
        }
    }
}
