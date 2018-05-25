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
import android.widget.Spinner;

import com.example.sam.curriculumvitae.R;
import com.example.sam.curriculumvitae.database.DadosOpenHelper;
import com.example.sam.curriculumvitae.dominio.entidade.Idioma;
import com.example.sam.curriculumvitae.dominio.repositorio.IdiomaRepositorio;
import com.example.sam.curriculumvitae.mensagem.Mensagem;

import java.util.Objects;

public class ActIdiomaOperation extends AppCompatActivity {
    private EditText etIdioma;
    private Spinner  spNivel;

    private Mensagem mensagem;

    private Idioma idioma;
    private IdiomaRepositorio idiomaRepositorio;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_idioma_operation);
        Toolbar toolbarIdiomaOperation = findViewById(R.id.toolbarIdiomaOperation);
        setSupportActionBar(toolbarIdiomaOperation);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_idioma);
        toolbarIdiomaOperation.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToIdiomaScreen();
            }
        });

        mensagem = new Mensagem();

        etIdioma  = findViewById(R.id.etIdioma);
        spNivel   = findViewById(R.id.spNivel);

        criarConexao();
        verificaParametro();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater  = getMenuInflater();
        menuInflater.inflate(R.menu.menu_act_idioma, menu);
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

        String strIdioma  = etIdioma.getText().toString();
        String nivel      = spNivel.getItemAtPosition(spNivel.getFirstVisiblePosition()).toString();//spNivel.toString();

        idioma.idioma     = strIdioma;
        idioma.nivel      = nivel;

        if (ret = isCampoVazio(strIdioma)) {
            etIdioma.requestFocus();
        } else if (ret = isCampoVazio(nivel)) {
            spNivel.requestFocus();
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
                if (idioma.codigo == 0) {
                    idiomaRepositorio.inserir(idioma);
                } else {
                    idiomaRepositorio.alterar(idioma);
                }

                mensagem.mostraTexto(this, getString(R.string.message_dados_atualizados_sucesso));

                goToIdiomaScreen();
            } catch (Exception ex) {
                mensagem.alert(this, getString(R.string.message_erro), ex.getMessage());
            }
        }
    }

    private void excluir() {
        try {
            mensagem.msgYesNo(this, getString(R.string.message_excluir), getString(R.string.message_excluir_idioma), execExcluir());
        } catch (Exception ex) {
            mensagem.alert(this, getString(R.string.message_erro), ex.getMessage());
        }
    }

    private Runnable execExcluir() {
        return new Runnable() {
            @Override
            public void run() {
                idiomaRepositorio.excluir(idioma.codigo);
                goToIdiomaScreen();
            }
        };
    }

    private void criarConexao() {
        try {
            DadosOpenHelper dadosOpenHelper = new DadosOpenHelper(this);
            SQLiteDatabase conexao = dadosOpenHelper.getWritableDatabase();
            idiomaRepositorio = new IdiomaRepositorio(conexao);
        } catch (SQLException ex) {
            mensagem.alert(this, getString(R.string.message_erro), ex.getMessage());
        }
    }

    public void goToIdiomaScreen() {
        Intent intent = new Intent(ActIdiomaOperation.this, ActIdioma.class);
        startActivity(intent);
    }

    private int getPosNivel(String spinnerNivel) {
        int ret = 0;

        switch (spinnerNivel) {
            case ("Básico"):
                ret = 1; break;
            case ("Intermediário"):
                ret = 2; break;
            case ("Avançado"):
                ret = 3; break;
            case ("Fluente"):
                ret = 4; break;
        }

        return ret;
    }

    private void insereDados(Idioma i) {
        etIdioma.setText(i.idioma);
        spNivel.setSelection(getPosNivel(i.nivel));
    }

    private void verificaParametro() {
        Bundle bundle = getIntent().getExtras();

        idioma = new Idioma();

        if (bundle != null && bundle.containsKey("IDIOMA")) {
            idioma = (Idioma) bundle.getSerializable("IDIOMA");

            if (idioma != null) {
                insereDados(idioma);
            }
        }
    }
}
