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

import com.example.sam.curriculumvitae.R;
import com.example.sam.curriculumvitae.adapter.CursoAdapter;
import com.example.sam.curriculumvitae.database.DadosOpenHelper;
import com.example.sam.curriculumvitae.dominio.entidade.Curso;
import com.example.sam.curriculumvitae.dominio.repositorio.CursoRepositorio;
import com.example.sam.curriculumvitae.mensagem.Mensagem;

import java.util.List;

public class ActCurso extends AppCompatActivity {
    private Curso curso;
    private CursoRepositorio cursoRepositorio;
    private CursoAdapter cursoAdapter;

    private DadosOpenHelper dadosOpenHelper;
    private SQLiteDatabase conexao;

    private Mensagem mensagem;

    private RecyclerView lstDadosCurso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_curso);
        Toolbar toolbarCurso = (Toolbar) findViewById(R.id.toolbarCurso);
        setSupportActionBar(toolbarCurso);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarCurso.setTitle(R.string.title_curso);
        toolbarCurso.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainScreen();
            }
        });

        mensagem = new Mensagem();
        lstDadosCurso = (RecyclerView) findViewById(R.id.lstDadosCurso);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCursoOperationScreen();
            }
        });

        criarConexao();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lstDadosCurso.setLayoutManager(linearLayoutManager);
        cursoRepositorio = new CursoRepositorio(conexao);

        List<Curso> dadosCurso = cursoRepositorio.buscarTodosCursos();

        cursoAdapter = new CursoAdapter(dadosCurso);

        lstDadosCurso.setAdapter(cursoAdapter);
    }

    public void goToMainScreen() {
        Intent intent = new Intent(ActCurso.this, ActTelaInicial.class);
        startActivity(intent);
    }

    public void goToCursoOperationScreen() {
        Intent intent = new Intent(ActCurso.this, ActCursoOperation.class);
        startActivity(intent);
    }

    private void criarConexao() {
        try {
            dadosOpenHelper = new DadosOpenHelper(this);
            conexao = dadosOpenHelper.getWritableDatabase();
            cursoRepositorio = new CursoRepositorio(conexao);
        } catch (SQLException ex) {
            mensagem.alert(this, getString(R.string.message_erro), ex.getMessage());
        }
    }
}