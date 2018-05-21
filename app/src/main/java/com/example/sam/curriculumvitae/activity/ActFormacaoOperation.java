package com.example.sam.curriculumvitae.activity;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
import com.example.sam.curriculumvitae.dominio.entidade.Formacao;
import com.example.sam.curriculumvitae.dominio.repositorio.FormacaoRepositorio;
import com.example.sam.curriculumvitae.mensagem.Mensagem;

public class ActFormacaoOperation extends AppCompatActivity {
    private EditText etCurso;
    private EditText etInstituicao;
    private Spinner  spStatus;
    private EditText etDataInicio;
    private EditText etDataConclusao;

    private Mensagem mensagem;

    private Formacao formacao;
    private FormacaoRepositorio formacaoRepositorio;

    private DadosOpenHelper dadosOpenHelper;
    private SQLiteDatabase conexao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_formacao_operation);
        Toolbar toolbarFormacaoOperation = findViewById(R.id.toolbarFormacaoOperation);
        setSupportActionBar(toolbarFormacaoOperation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarFormacaoOperation.setTitle(R.string.title_formacao);
        toolbarFormacaoOperation.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFormacaoScreen();
            }
        });

        mensagem = new Mensagem();

        etCurso         = (EditText) findViewById(R.id.etCurso);
        etInstituicao   = (EditText) findViewById(R.id.etInstituicao);
        spStatus        = (Spinner) findViewById(R.id.spStatus);
        etDataInicio    = (EditText) findViewById(R.id.etDataInicio);
        etDataConclusao = (EditText) findViewById(R.id.etDataConclusao);

        criarConexao();
        verificaParametro();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater  = getMenuInflater();
        menuInflater.inflate(R.menu.menu_act_formacao, menu);
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

        String curso          = etCurso.getText().toString();
        String instituicao    = etInstituicao.getText().toString();
        String status         = spStatus.toString();
        String dataInicio     = etDataInicio.getText().toString();
        String dataConclusao  = etDataConclusao.getText().toString();

        formacao.curso          = curso;
        formacao.instituicao    = instituicao;
        formacao.status         = status;
        formacao.dataInicio     = dataInicio;
        formacao.dataConclusao  = dataConclusao;

        if (ret = isCampoVazio(curso)) {
            etCurso.requestFocus();
        } else if (ret = isCampoVazio(instituicao)) {
            etInstituicao.requestFocus();
        } else if (ret = isCampoVazio(dataInicio)) {
            etDataInicio.requestFocus();
        }

        if (ret) {
            mensagem.alert(this, getString(R.string.message_aviso), getString(R.string.message_campos_invalidos_brancos));
        }

        return ret;
    }

    private boolean isCampoVazio(String campo) {
        boolean ret = (TextUtils.isEmpty(campo) || campo.trim().isEmpty());

        return ret;
    }

    private void confirmar() {
        if (!validaCampos()) {
            try {
                if (formacao.codigo == 0) {
                    formacaoRepositorio.inserir(formacao);
                } else {
                    formacaoRepositorio.alterar(formacao);
                }

                mensagem.mostraTexto(this, getString(R.string.message_dados_atualizados_sucesso));

                goToFormacaoScreen();
            } catch (Exception ex) {
                mensagem.alert(this, getString(R.string.message_erro), ex.getMessage());
            }
        }
    }

    private void excluir() {
        try {
            mensagem.msgYesNo(this, getString(R.string.message_excluir), getString(R.string.message_excluir_formacao), execExcluir());
        } catch (Exception ex) {
            mensagem.alert(this, getString(R.string.message_erro), ex.getMessage());
        }
    }

    private Runnable execExcluir() {
        return new Runnable() {
            @Override
            public void run() {
                formacaoRepositorio.excluir(formacao.codigo);
                goToFormacaoScreen();
            }
        };
    }

    private void criarConexao() {
        try {
            dadosOpenHelper = new DadosOpenHelper(this);
            conexao = dadosOpenHelper.getWritableDatabase();
            formacaoRepositorio = new FormacaoRepositorio(conexao);
        } catch (SQLException ex) {
            mensagem.alert(this, getString(R.string.message_erro), ex.getMessage());
        }
    }

    public void goToFormacaoScreen() {
        Intent intent = new Intent(ActFormacaoOperation.this, ActFormacao.class);
        startActivity(intent);
    }

    private int getPosStatus(String spinnerStatus) {
        int ret = 0;

        switch (spinnerStatus) {
            case ("Em Conclusão"):
                ret = 1; break;
            case ("Concluído"):
                ret = 2; break;
            case ("Incompleto"):
                ret = 3; break;
        }

        return ret;
    }

    private void insereDados(Formacao f) {
        etCurso.setText(f.curso);
        etInstituicao.setText(f.instituicao);
        spStatus.setSelection(getPosStatus(f.status));
        etDataInicio.setText(f.dataInicio);
        etDataConclusao.setText(f.dataConclusao);
    }

    private void verificaParametro() {
        Bundle bundle = getIntent().getExtras();

        formacao = new Formacao();

        if (bundle != null && bundle.containsKey("FORMACAO")) {
            formacao = (Formacao) bundle.getSerializable("FORMACAO");

            insereDados(formacao);
        }
    }
}
