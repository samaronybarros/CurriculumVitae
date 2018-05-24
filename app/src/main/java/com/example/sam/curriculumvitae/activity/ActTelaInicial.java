package com.example.sam.curriculumvitae.activity;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sam.curriculumvitae.R;
import com.example.sam.curriculumvitae.database.DadosOpenHelper;
import com.example.sam.curriculumvitae.dominio.entidade.InfoPessoais;
import com.example.sam.curriculumvitae.dominio.repositorio.InfoPessoaisRepositorio;
import com.example.sam.curriculumvitae.mensagem.Mensagem;

public class ActTelaInicial extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tvUsuario;
    private TextView tvEmail;

    private InfoPessoais infoPessoais;
    private InfoPessoaisRepositorio infoPessoaisRepositorio;

    private DadosOpenHelper dadosOpenHelper;
    private SQLiteDatabase conexao;

    private Mensagem mensagem;

    private ImageButton imgAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_tela_inicial);
        Toolbar toolbarTelaInicial = (Toolbar) findViewById(R.id.toolbarTelaInicial);
        setSupportActionBar(toolbarTelaInicial);
        getSupportActionBar().setTitle(R.string.title_tela_inicial);

        criarConexao();

        mensagem = new Mensagem();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbarTelaInicial, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*
        //TODO: Implementar a função para pegar dados do banco e incluir no menu
        // Não está pegando, pois os TextViews não estão sendo associados ao menu
        tvUsuario   = (TextView) navigationView.findViewById(R.id.tvUsuario);
        tvEmail     = (TextView) navigationView.findViewById(R.id.tvEmail);

        infoPessoais = infoPessoaisRepositorio.buscar();

        if (infoPessoais != null) {
            tvUsuario.setText(infoPessoais.nome);
            tvEmail.setText(infoPessoais.email);
        }
        */
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.act_tela_inicial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addListenerOnButton() {
        imgAvatar = (ImageButton) findViewById(R.id.imgAvatar);

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensagem.alert(getApplicationContext(), "Botao", "Botão clicado");
            }
        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case (R.id.nav_info_pessoais):
                goToScreenInfo();
                break;
            case (R.id.nav_objetivo):
                goToScreenObjetivo();
                break;
            case (R.id.nav_formacao):
                goToScreenFormacao();
                break;
            case (R.id.nav_experiencia):
                goToScreenExperiencia();
                break;
            case (R.id.nav_curso):
                goToScreenCurso();
                break;
            case (R.id.nav_qualificacao):
                goToScreenQualificacao();
                break;
            case (R.id.nav_idioma):
                goToScreenIdioma();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void goToScreenInfo() {
        Intent intent = new Intent(ActTelaInicial.this, ActInfoPessoais.class);
        startActivity(intent);
    }

    public void goToScreenObjetivo() {
        Intent intent = new Intent(ActTelaInicial.this, ActObjetivo.class);
        startActivity(intent);
    }

    public void goToScreenFormacao() {
        Intent intent = new Intent(ActTelaInicial.this, ActFormacao.class);
        startActivity(intent);
    }

    public void goToScreenExperiencia() {
        Intent intent = new Intent(ActTelaInicial.this, ActExperiencia.class);
        startActivity(intent);
    }

    public void goToScreenCurso() {
        Intent intent = new Intent(ActTelaInicial.this, ActCurso.class);
        startActivity(intent);
    }

    public void goToScreenQualificacao() {
        Intent intent = new Intent(ActTelaInicial.this, ActQualificacao.class);
        startActivity(intent);
    }

    public void goToScreenIdioma() {
        Intent intent = new Intent(ActTelaInicial.this, ActIdioma.class);
        startActivity(intent);
    }

    private void criarConexao() {
        try {
            dadosOpenHelper = new DadosOpenHelper(this);
            conexao = dadosOpenHelper.getWritableDatabase();
            infoPessoaisRepositorio = new InfoPessoaisRepositorio(conexao);
        } catch (SQLException ex) {
            mensagem.alert(this, getString(R.string.message_erro), ex.getMessage());
        }
    }
}
