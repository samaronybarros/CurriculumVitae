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
import com.example.sam.curriculumvitae.adapter.IdiomaAdapter;
import com.example.sam.curriculumvitae.database.DadosOpenHelper;
import com.example.sam.curriculumvitae.dominio.entidade.Idioma;
import com.example.sam.curriculumvitae.dominio.repositorio.IdiomaRepositorio;
import com.example.sam.curriculumvitae.mensagem.Mensagem;

import java.util.List;

public class ActIdioma extends AppCompatActivity {
    private Idioma idioma;
    private IdiomaRepositorio idiomaRepositorio;
    private IdiomaAdapter idiomaAdapter;

    private DadosOpenHelper dadosOpenHelper;
    private SQLiteDatabase conexao;

    private Mensagem mensagem;

    private RecyclerView lstDadosIdioma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_idioma);
        Toolbar toolbarIdioma = (Toolbar) findViewById(R.id.toolbarIdioma);
        setSupportActionBar(toolbarIdioma);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_idioma);
        toolbarIdioma.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainScreen();
            }
        });

        mensagem = new Mensagem();
        lstDadosIdioma = (RecyclerView) findViewById(R.id.lstDadosIdioma);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToIdiomaOperationScreen();
            }
        });

        criarConexao();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lstDadosIdioma.setLayoutManager(linearLayoutManager);
        idiomaRepositorio = new IdiomaRepositorio(conexao);

        List<Idioma> dadosIdioma = idiomaRepositorio.buscarTodosIdiomas();

        idiomaAdapter = new IdiomaAdapter(dadosIdioma);

        lstDadosIdioma.setAdapter(idiomaAdapter);
    }

    public void goToMainScreen() {
        Intent intent = new Intent(ActIdioma.this, ActTelaInicial.class);
        startActivity(intent);
    }

    public void goToIdiomaOperationScreen() {
        Intent intent = new Intent(ActIdioma.this, ActIdiomaOperation.class);
        startActivity(intent);
    }

    private void criarConexao() {
        try {
            dadosOpenHelper = new DadosOpenHelper(this);
            conexao = dadosOpenHelper.getWritableDatabase();
            idiomaRepositorio = new IdiomaRepositorio(conexao);
        } catch (SQLException ex) {
            mensagem.alert(this, getString(R.string.message_erro), ex.getMessage());
        }
    }
}
