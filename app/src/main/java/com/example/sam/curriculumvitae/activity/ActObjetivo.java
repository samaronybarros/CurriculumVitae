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
import com.example.sam.curriculumvitae.dominio.entidade.Objetivo;
import com.example.sam.curriculumvitae.dominio.repositorio.ObjetivoRepositorio;
import com.example.sam.curriculumvitae.mensagem.Mensagem;

import java.util.Objects;

public class ActObjetivo extends AppCompatActivity {
    private EditText etObjetivo;

    private Mensagem mensagem;

    private Objetivo objetivo;
    private ObjetivoRepositorio objetivoRepositorio;

    private boolean flagInsereAltera; //INSERE=false / ALTERA=true

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_objetivo);
        Toolbar toolbarObjetivo = findViewById(R.id.toolbarObjetivo);
        setSupportActionBar(toolbarObjetivo);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_objetivo);
        toolbarObjetivo.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainScreen();
            }
        });

        mensagem = new Mensagem();
        flagInsereAltera = false;

        etObjetivo = findViewById(R.id.etObjetivo);

        criarConexao();

        insereDados();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_act_objetivo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case (R.id.action_ok):
                confirmar();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToMainScreen() {
        Intent intent = new Intent(ActObjetivo.this, ActTelaInicial.class);
        startActivity(intent);
    }

    private boolean validaCampos() {
        boolean ret;

        String desc = etObjetivo.getText().toString();

        objetivo.descricao = desc;

        if (ret = isCampoVazio(desc)) {
            etObjetivo.requestFocus();
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
                if (flagInsereAltera) {
                    objetivoRepositorio.alterar(objetivo);
                } else {
                    objetivoRepositorio.inserir(objetivo);
                }
                mensagem.mostraTexto(this, getString(R.string.message_dados_atualizados_sucesso));

                goToMainScreen();
            } catch (Exception ex) {
                mensagem.alert(this, getString(R.string.message_erro), ex.getMessage());
            }
        }
    }

    private void criarConexao() {
        try {
            DadosOpenHelper dadosOpenHelper = new DadosOpenHelper(this);
            SQLiteDatabase conexao = dadosOpenHelper.getWritableDatabase();
            objetivoRepositorio = new ObjetivoRepositorio(conexao);
        } catch (SQLException ex) {
            mensagem.alert(this, getString(R.string.message_erro), ex.getMessage());
        }
    }

    private void insereDados() {
        objetivo = objetivoRepositorio.buscar();

        if (objetivo != null) {
            flagInsereAltera = true;

            etObjetivo.setText(objetivo.descricao);
        } else {
            flagInsereAltera = false;
            objetivo = new Objetivo();
        }
    }
}
