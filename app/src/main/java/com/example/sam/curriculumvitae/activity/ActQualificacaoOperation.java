package com.example.sam.curriculumvitae.activity;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.sam.curriculumvitae.R;
import com.example.sam.curriculumvitae.database.DadosOpenHelper;
import com.example.sam.curriculumvitae.dominio.entidade.Qualificacao;
import com.example.sam.curriculumvitae.dominio.repositorio.QualificacaoRepositorio;
import com.example.sam.curriculumvitae.mensagem.Mensagem;

import java.util.Objects;

public class ActQualificacaoOperation extends AppCompatActivity {
    private EditText etAtividade;
    private EditText etDescricao;

    private Mensagem mensagem;

    private Qualificacao qualificacao;
    private QualificacaoRepositorio qualificacaoRepositorio;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_qualificacao_operation);
        Toolbar toolbarQualificacaoOperation = findViewById(R.id.toolbarQualificacaoOperation);
        setSupportActionBar(toolbarQualificacaoOperation);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_qualificacao);
        toolbarQualificacaoOperation.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToQualificacaoScreen();
            }
        });

        mensagem = new Mensagem();

        etAtividade   = findViewById(R.id.etAtividade);
        etDescricao   = findViewById(R.id.etDescricao);

        criarConexao();
        verificaParametro();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater  = getMenuInflater();
        menuInflater.inflate(R.menu.menu_act_qualificacao, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case (R.id.action_ok):
                confirmar();
                break;
            case (R.id.action_excluir):
                excluir();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validaCampos() {
        boolean ret;

        String atividade  = etAtividade.getText().toString();
        String descricao  = etDescricao.getText().toString();

        qualificacao.atividade    = atividade;
        qualificacao.descricao    = descricao;

        if (ret = isCampoVazio(atividade)) {
            etAtividade.requestFocus();
        } else if (ret = isCampoVazio(descricao)) {
            etDescricao.requestFocus();
        }

        if (ret) {
            mensagem.alert(this, getString(R.string.message_aviso), getString(R.string.message_campos_invalidos_brancos));
        }

        return ret;
    }

    private boolean isCampoVazio(String campo) {
        return (TextUtils.isEmpty(campo) || campo.trim().isEmpty());
    }

    private void confirmar() {
        if (!validaCampos()) {
            try {
                if (qualificacao.codigo == 0) {
                    qualificacaoRepositorio.inserir(qualificacao);
                } else {
                    qualificacaoRepositorio.alterar(qualificacao);
                }

                mensagem.mostraTexto(this, getString(R.string.message_dados_atualizados_sucesso));

                goToQualificacaoScreen();
            } catch (Exception ex) {
                mensagem.alert(this, getString(R.string.message_erro), ex.getMessage());
            }
        }
    }

    private void excluir() {
        try {
            mensagem.msgYesNo(this, getString(R.string.message_excluir), getString(R.string.message_excluir_qualificacao), execExcluir());
        } catch (Exception ex) {
            mensagem.alert(this, getString(R.string.message_erro), ex.getMessage());
        }
    }

    private Runnable execExcluir() {
        return new Runnable() {
            @Override
            public void run() {
                qualificacaoRepositorio.excluir(qualificacao.codigo);
                goToQualificacaoScreen();
            }
        };
    }

    private void criarConexao() {
        try {
            DadosOpenHelper dadosOpenHelper = new DadosOpenHelper(this);
            SQLiteDatabase conexao = dadosOpenHelper.getWritableDatabase();
            qualificacaoRepositorio = new QualificacaoRepositorio(conexao);
        } catch (SQLException ex) {
            mensagem.alert(this, getString(R.string.message_erro), ex.getMessage());
        }
    }

    public void goToQualificacaoScreen() {
        Intent intent = new Intent(ActQualificacaoOperation.this, ActQualificacao.class);
        startActivity(intent);
    }

    private void insereDados(Qualificacao q) {
        etAtividade.setText(q.atividade);
        etDescricao.setText(q.descricao);
    }

    private void verificaParametro() {
        Bundle bundle = getIntent().getExtras();

        qualificacao = new Qualificacao();

        if (bundle != null && bundle.containsKey("QUALIFICACAO")) {
            qualificacao = (Qualificacao) bundle.getSerializable("QUALIFICACAO");

            if (qualificacao != null) {
                insereDados(qualificacao);
            }
        }
    }
}
