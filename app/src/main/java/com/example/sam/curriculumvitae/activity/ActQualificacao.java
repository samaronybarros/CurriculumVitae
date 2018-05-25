package com.example.sam.curriculumvitae.activity;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.sam.curriculumvitae.R;
import com.example.sam.curriculumvitae.adapter.QualificacaoAdapter;
import com.example.sam.curriculumvitae.database.DadosOpenHelper;
import com.example.sam.curriculumvitae.dominio.entidade.Qualificacao;
import com.example.sam.curriculumvitae.dominio.repositorio.QualificacaoRepositorio;
import com.example.sam.curriculumvitae.mensagem.Mensagem;

import java.util.List;
import java.util.Objects;

public class ActQualificacao extends AppCompatActivity {
    private QualificacaoRepositorio qualificacaoRepositorio;

    private SQLiteDatabase conexao;

    private Mensagem mensagem;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_qualificacao);
        Toolbar toolbarQualificacao =  findViewById(R.id.toolbarQualificacao);
        setSupportActionBar(toolbarQualificacao);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_qualificacao);
        toolbarQualificacao.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainScreen();
            }
        });

        mensagem = new Mensagem();
        RecyclerView lstDadosQualificacao = findViewById(R.id.lstDadosQualificacao);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToQualificacaoOperationScreen();
            }
        });

        criarConexao();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lstDadosQualificacao.setLayoutManager(linearLayoutManager);
        qualificacaoRepositorio = new QualificacaoRepositorio(conexao);

        List<Qualificacao> dadosQualificacao = qualificacaoRepositorio.buscarTodasQualificacoes();

        QualificacaoAdapter qualificacaoAdapter = new QualificacaoAdapter(dadosQualificacao);

        lstDadosQualificacao.setAdapter(qualificacaoAdapter);
    }

    public void goToMainScreen() {
        Intent intent = new Intent(ActQualificacao.this, ActTelaInicial.class);
        startActivity(intent);
    }

    public void goToQualificacaoOperationScreen() {
        Intent intent = new Intent(ActQualificacao.this, ActQualificacaoOperation.class);
        startActivity(intent);
    }

    private void criarConexao() {
        try {
            DadosOpenHelper dadosOpenHelper = new DadosOpenHelper(this);
            conexao = dadosOpenHelper.getWritableDatabase();
            qualificacaoRepositorio = new QualificacaoRepositorio(conexao);
        } catch (SQLException ex) {
            mensagem.alert(this, getString(R.string.message_erro), ex.getMessage());
        }
    }
}
