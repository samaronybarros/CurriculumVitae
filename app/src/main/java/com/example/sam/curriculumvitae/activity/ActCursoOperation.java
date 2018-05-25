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
import com.example.sam.curriculumvitae.dominio.entidade.Curso;
import com.example.sam.curriculumvitae.dominio.repositorio.CursoRepositorio;
import com.example.sam.curriculumvitae.mensagem.Mensagem;

import java.util.Objects;

public class ActCursoOperation extends AppCompatActivity {
    private EditText etCurso;
    private EditText etInstituicao;
    private Spinner  spStatus;
    private EditText etDataInicio;
    private EditText etDataConclusao;
    private EditText etDescricao;

    private Mensagem mensagem;

    private Curso curso;
    private CursoRepositorio cursoRepositorio;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_curso_operation);
        Toolbar toolbarCursoOperation = findViewById(R.id.toolbarCursoOperation);
        setSupportActionBar(toolbarCursoOperation);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_curso);
        toolbarCursoOperation.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCursoScreen();
            }
        });

        mensagem = new Mensagem();

        etCurso         = findViewById(R.id.etCurso);
        etInstituicao   = findViewById(R.id.etInstituicao);
        spStatus        = findViewById(R.id.spStatus);
        etDataInicio    = findViewById(R.id.etDataInicio);
        etDataConclusao = findViewById(R.id.etDataConclusao);
        etDescricao     = findViewById(R.id.etDescricao);

        criarConexao();
        verificaParametro();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater  = getMenuInflater();
        menuInflater.inflate(R.menu.menu_act_curso, menu);
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

        String strCurso       = etCurso.getText().toString();
        String instituicao    = etInstituicao.getText().toString();
        String status         = spStatus.toString();
        String dataInicio     = etDataInicio.getText().toString();
        String dataConclusao  = etDataConclusao.getText().toString();
        String descricao      = etDescricao.getText().toString();

        curso.curso          = strCurso;
        curso.instituicao    = instituicao;
        curso.status         = status;
        curso.dataInicio     = dataInicio;
        curso.dataConclusao  = dataConclusao;
        curso.descricao      = descricao;

        if (ret = isCampoVazio(strCurso)) {
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
        return (TextUtils.isEmpty(campo) || campo.trim().isEmpty());
    }

    private void confirmar() {
        if (!validaCampos()) {
            try {
                if (curso.codigo == 0) {
                    cursoRepositorio.inserir(curso);
                } else {
                    cursoRepositorio.alterar(curso);
                }

                mensagem.mostraTexto(this, getString(R.string.message_dados_atualizados_sucesso));

                goToCursoScreen();
            } catch (Exception ex) {
                mensagem.alert(this, getString(R.string.message_erro), ex.getMessage());
            }
        }
    }

    private void excluir() {
        try {
            mensagem.msgYesNo(this, getString(R.string.message_excluir), getString(R.string.message_excluir_curso), execExcluir());
        } catch (Exception ex) {
            mensagem.alert(this, getString(R.string.message_erro), ex.getMessage());
        }
    }

    private Runnable execExcluir() {
        return new Runnable() {
            @Override
            public void run() {
                cursoRepositorio.excluir(curso.codigo);
                goToCursoScreen();
            }
        };
    }

    private void criarConexao() {
        try {
            DadosOpenHelper dadosOpenHelper = new DadosOpenHelper(this);
            SQLiteDatabase conexao = dadosOpenHelper.getWritableDatabase();
            cursoRepositorio = new CursoRepositorio(conexao);
        } catch (SQLException ex) {
            mensagem.alert(this, getString(R.string.message_erro), ex.getMessage());
        }
    }

    public void goToCursoScreen() {
        Intent intent = new Intent(ActCursoOperation.this, ActCurso.class);
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

    private void insereDados(Curso c) {
        etCurso.setText(c.curso);
        etInstituicao.setText(c.instituicao);
        spStatus.setSelection(getPosStatus(c.status));
        etDataInicio.setText(c.dataInicio);
        etDataConclusao.setText(c.dataConclusao);
    }

    private void verificaParametro() {
        Bundle bundle = getIntent().getExtras();

        curso = new Curso();

        if (bundle != null && bundle.containsKey("CURSO")) {
            curso = (Curso) bundle.getSerializable("CURSO");

            if (curso != null) {
                insereDados(curso);
            }
        }
    }
}
