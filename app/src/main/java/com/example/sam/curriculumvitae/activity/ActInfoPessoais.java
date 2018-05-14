package com.example.sam.curriculumvitae.activity;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sam.curriculumvitae.R;
import com.example.sam.curriculumvitae.database.DadosOpenHelper;
import com.example.sam.curriculumvitae.dominio.entidade.InfoPessoais;
import com.example.sam.curriculumvitae.dominio.repositorio.InfoPessoaisRepositorio;
import com.example.sam.curriculumvitae.mensagem.Mensagem;

import static com.example.sam.curriculumvitae.R.*;

public class ActInfoPessoais extends AppCompatActivity {
    private EditText etNome;
    private EditText etNacionalidade;
    private Spinner  spEstadoCivil;
    private EditText etCidadeNascimento;
    private EditText etDataNascimento;
    private EditText etTelefoneCelular;
    private EditText etTelefoneFixo;
    private EditText etEmail;
    private EditText etEndereco;
    private Spinner  spTipoCNH;

    private Mensagem mensagem;

    private InfoPessoais infoPessoais;
    private InfoPessoaisRepositorio infoPessoaisRepositorio;

    private DadosOpenHelper dadosOpenHelper;
    private SQLiteDatabase conexao;

    private boolean flagInsereAltera; //INSERE=false / ALTERA=true

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_act_info_pessoais);
        Toolbar toolbar = findViewById(id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(string.title_info);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainScreen();
            }
        });

        mensagem = new Mensagem();
        flagInsereAltera = false;

        etNome              = (EditText) findViewById(id.etNome);
        etNacionalidade     = (EditText) findViewById(id.etNacionalidade);
        spEstadoCivil       = (Spinner)  findViewById(id.spEstadoCivil);
        etCidadeNascimento  = (EditText) findViewById(id.etCidadeNascimento);
        etDataNascimento    = (EditText) findViewById(id.etDataNascimento);
        etTelefoneCelular   = (EditText) findViewById(id.etTelefoneCelular);
        etTelefoneFixo      = (EditText) findViewById(id.etTelefoneFixo);
        etEmail             = (EditText) findViewById(id.etEmail);
        etEndereco          = (EditText) findViewById(id.etEndereco);
        spTipoCNH           = (Spinner)  findViewById(id.spTipoCNH);

        criarConexao();

        insereDados();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater  = getMenuInflater();
        menuInflater.inflate(R.menu.menu_act_info_pessoais, menu);
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
        Intent intent = new Intent(ActInfoPessoais.this, ActTelaInicial.class);
        startActivity(intent);
    }

    private boolean validaCampos() {
        boolean ret;

        String nome             = etNome.getText().toString();
        String nacionalidade    = etNacionalidade.getText().toString();
        String estadoCivil      = spEstadoCivil.toString();
        String cidadeNascimento = etCidadeNascimento.getText().toString();
        String dataNascimento   = etDataNascimento.getText().toString();
        String telefoneCelular  = etTelefoneCelular.getText().toString();
        String telefoneFixo     = etTelefoneFixo.getText().toString();
        String email            = etEmail.getText().toString();
        String endereco         = etEndereco.getText().toString();
        String tipoCNH          = spTipoCNH.toString();

        infoPessoais.nome               = nome;
        infoPessoais.nacionalidade      = nacionalidade;
        infoPessoais.estadoCivil        = estadoCivil;
        infoPessoais.cidadeNascimento   = cidadeNascimento;
        infoPessoais.dataNascimento     = dataNascimento;
        infoPessoais.telefoneCelular    = telefoneCelular;
        infoPessoais.telefoneFixo       = telefoneFixo;
        infoPessoais.email              = email;
        infoPessoais.endereco           = endereco;
        infoPessoais.tipoCNH            = tipoCNH;

        if (ret = isCampoVazio(nome)) {
            etNome.requestFocus();
        } else if (ret = isCampoVazio(dataNascimento)) {
            etDataNascimento.requestFocus();
        } else if (ret = isCampoVazio(telefoneCelular)) {
            etTelefoneCelular.requestFocus();
        } else if (ret = isCampoVazio(telefoneFixo)) {
            etTelefoneFixo.requestFocus();
        } else if (ret = isEmailValido(email)) {
            etEmail.requestFocus();
        } else if (ret = isCampoVazio(endereco)) {
            etEndereco.requestFocus();
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

    private boolean isEmailValido(String email) {
        boolean ret = (!isCampoVazio(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());

        return ret;
    }

    private void confirmar() {
        if (!validaCampos()) {
            try {
                if (flagInsereAltera) {
                    infoPessoaisRepositorio.alterar(infoPessoais);
                } else {
                    infoPessoaisRepositorio.inserir(infoPessoais);
                }
                mensagem.mostraTexto(this, getString(string.message_dados_atualizados_sucesso));

                goToMainScreen();
            } catch (Exception ex) {
                mensagem.alert(this, getString(string.message_erro), ex.getMessage());
            }
        }
    }

    private void criarConexao() {
        try {
            dadosOpenHelper = new DadosOpenHelper(this);
            conexao = dadosOpenHelper.getWritableDatabase();
            infoPessoaisRepositorio = new InfoPessoaisRepositorio(conexao);
        } catch (SQLException ex) {
            mensagem.alert(this, getString(string.message_erro), ex.getMessage());
        }
    }

    private void insereDados() {
        infoPessoais = infoPessoaisRepositorio.buscar();

        if (infoPessoais != null) {
            flagInsereAltera = true;

            etNome.setText(infoPessoais.nome);
            etNacionalidade.setText(infoPessoais.nacionalidade);
            spEstadoCivil.setSelection(getPosEstadoCivil(infoPessoais.estadoCivil));
            etCidadeNascimento.setText(infoPessoais.cidadeNascimento);
            etDataNascimento.setText(infoPessoais.dataNascimento);
            etTelefoneCelular.setText(infoPessoais.telefoneCelular);
            etTelefoneFixo.setText(infoPessoais.telefoneFixo);
            etEmail.setText(infoPessoais.email);
            etEndereco.setText(infoPessoais.endereco);
            spTipoCNH.setSelection(getPosTipoCNH(infoPessoais.tipoCNH));
        } else {
            flagInsereAltera = false;
            infoPessoais = new InfoPessoais();
        }
    }

    private int getPosEstadoCivil(String spinnerEstadoCivil) {
        int ret = 0;

        switch (spinnerEstadoCivil) {
            case ("Solteiro (a)"):
                ret = 1; break;
            case ("Casado(a)"):
                ret = 2; break;
            case ("Divorciado(a)"):
                ret = 3; break;
            case ("Viúvo(a)"):
                ret = 4; break;
            case ("Separado(a)"):
                ret = 5; break;
        }

        return ret;
    }

    private int getPosTipoCNH(String spinnerTipoCNH) {
        int ret = 0;

        switch (spinnerTipoCNH) {
            case ("A"):
                ret = 1; break;
            case ("B"):
                ret = 2; break;
            case ("AB"):
                ret = 3; break;
            case ("C"):
                ret = 4; break;
            case ("D"):
                ret = 5; break;
            case ("E"):
                ret = 6; break;
        }

        return ret;
    }
}