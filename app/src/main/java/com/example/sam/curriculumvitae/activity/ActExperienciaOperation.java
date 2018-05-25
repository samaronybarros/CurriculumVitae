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
import com.example.sam.curriculumvitae.dominio.entidade.Experiencia;
import com.example.sam.curriculumvitae.dominio.repositorio.ExperienciaRepositorio;
import com.example.sam.curriculumvitae.mensagem.Mensagem;

import java.util.Objects;

public class ActExperienciaOperation extends AppCompatActivity {
    private EditText etEmpresa;
    private EditText etCargo;
    private EditText etDataInicio;
    private EditText etDataFim;
    private EditText etAtividades;

    private Mensagem mensagem;

    private Experiencia experiencia;
    private ExperienciaRepositorio experienciaRepositorio;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_experiencia_operation);
        Toolbar toolbarExperienciaOperation = findViewById(R.id.toolbarExperienciaOperation);
        setSupportActionBar(toolbarExperienciaOperation);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_experiencia);
        toolbarExperienciaOperation.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToExperienciaScreen();
            }
        });

        mensagem = new Mensagem();

        etEmpresa    = findViewById(R.id.etEmpresa);
        etCargo      = findViewById(R.id.etCargo);
        etDataInicio = findViewById(R.id.etDataInicio);
        etDataFim    = findViewById(R.id.etDataFim);
        etAtividades = findViewById(R.id.etAtividades);

        criarConexao();
        verificaParametro();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater  = getMenuInflater();
        menuInflater.inflate(R.menu.menu_act_experiencia, menu);
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

        String empresa     = etEmpresa.getText().toString();
        String cargo       = etCargo.getText().toString();
        String dataInicio  = etDataInicio.getText().toString();
        String dataFim     = etDataFim.getText().toString();
        String atividades  = etAtividades.getText().toString();

        experiencia.empresa     = empresa;
        experiencia.cargo       = cargo;
        experiencia.dataInicio  = dataInicio;
        experiencia.dataFim     = dataFim;
        experiencia.atividades  = atividades;

        if (ret = isCampoVazio(empresa)) {
            etEmpresa.requestFocus();
        } else if (ret = isCampoVazio(cargo)) {
            etCargo.requestFocus();
        } else if (ret = isCampoVazio(dataInicio)) {
            etDataInicio.requestFocus();
        } else if (ret = isCampoVazio(atividades)) {
            etAtividades.requestFocus();
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
                if (experiencia.codigo == 0) {
                    experienciaRepositorio.inserir(experiencia);
                } else {
                    experienciaRepositorio.alterar(experiencia);
                }

                mensagem.mostraTexto(this, getString(R.string.message_dados_atualizados_sucesso));

                goToExperienciaScreen();
            } catch (Exception ex) {
                mensagem.alert(this, getString(R.string.message_erro), ex.getMessage());
            }
        }
    }

    private void excluir() {
        try {
            mensagem.msgYesNo(this, getString(R.string.message_excluir), getString(R.string.message_excluir_experiencia), execExcluir());
        } catch (Exception ex) {
            mensagem.alert(this, getString(R.string.message_erro), ex.getMessage());
        }
    }

    private Runnable execExcluir() {
        return new Runnable() {
            @Override
            public void run() {
                experienciaRepositorio.excluir(experiencia.codigo);
                goToExperienciaScreen();
            }
        };
    }

    private void criarConexao() {
        try {
            DadosOpenHelper dadosOpenHelper = new DadosOpenHelper(this);
            SQLiteDatabase conexao = dadosOpenHelper.getWritableDatabase();
            experienciaRepositorio = new ExperienciaRepositorio(conexao);
        } catch (SQLException ex) {
            mensagem.alert(this, getString(R.string.message_erro), ex.getMessage());
        }
    }

    public void goToExperienciaScreen() {
        Intent intent = new Intent(ActExperienciaOperation.this, ActExperiencia.class);
        startActivity(intent);
    }


    private void insereDados(Experiencia e) {
        etEmpresa.setText(e.empresa);
        etCargo.setText(e.cargo);
        etDataInicio.setText(e.dataInicio);
        etDataFim.setText(e.dataFim);
        etAtividades.setText(e.atividades);
    }

    private void verificaParametro() {
        Bundle bundle = getIntent().getExtras();

        experiencia = new Experiencia();

        if (bundle != null && bundle.containsKey("EXPERIENCIA")) {
            experiencia = (Experiencia) bundle.getSerializable("EXPERIENCIA");

            if (experiencia != null) {
                insereDados(experiencia);
            }
        }
    }
}
