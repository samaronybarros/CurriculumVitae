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
import com.example.sam.curriculumvitae.adapter.FormacaoAdapter;
import com.example.sam.curriculumvitae.database.DadosOpenHelper;
import com.example.sam.curriculumvitae.dominio.entidade.Formacao;
import com.example.sam.curriculumvitae.dominio.repositorio.FormacaoRepositorio;
import com.example.sam.curriculumvitae.mensagem.Mensagem;

import java.util.List;
import java.util.Objects;

public class ActFormacao extends AppCompatActivity {
    private FormacaoRepositorio formacaoRepositorio;

    private SQLiteDatabase conexao;

    private Mensagem mensagem;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_formacao);
        Toolbar toolbarFormacao =  findViewById(R.id.toolbarFormacao);
        setSupportActionBar(toolbarFormacao);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_formacao);
        toolbarFormacao.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainScreen();
            }
        });

        mensagem = new Mensagem();
        RecyclerView lstDadosFormacao = findViewById(R.id.lstDadosFormacao);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFormacaoOperationScreen();
            }
        });

        criarConexao();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lstDadosFormacao.setLayoutManager(linearLayoutManager);
        formacaoRepositorio = new FormacaoRepositorio(conexao);

        List<Formacao> dadosFormacao = formacaoRepositorio.buscarTodasFormacoes();

        FormacaoAdapter formacaoAdapter = new FormacaoAdapter(dadosFormacao);

        lstDadosFormacao.setAdapter(formacaoAdapter);
    }

    public void goToMainScreen() {
        Intent intent = new Intent(ActFormacao.this, ActTelaInicial.class);
        startActivity(intent);
    }

    public void goToFormacaoOperationScreen() {
        Intent intent = new Intent(ActFormacao.this, ActFormacaoOperation.class);
        startActivity(intent);
    }

    private void criarConexao() {
        try {
            DadosOpenHelper dadosOpenHelper = new DadosOpenHelper(this);
            conexao = dadosOpenHelper.getWritableDatabase();
            formacaoRepositorio = new FormacaoRepositorio(conexao);
        } catch (SQLException ex) {
            mensagem.alert(this, getString(R.string.message_erro), ex.getMessage());
        }
    }
}
