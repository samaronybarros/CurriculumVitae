package com.example.sam.curriculumvitae.activity;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sam.curriculumvitae.R;
import com.example.sam.curriculumvitae.curriculo.CriarPDF;
import com.example.sam.curriculumvitae.mensagem.Mensagem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

public class ActTelaInicial extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final static String PATH_NAME = "/sdcard/CurriculoAvatar/";
    private final static String AVATAR_NAME = "avatar.jpg";
    private final static int RESULT_LOAD_IMAGE = 1;

    private Mensagem mensagem;

    private ImageButton carregarAvatar;

    private ImageButton btGirarEsquerda;
    private ImageButton btGirarDireita;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String pathName = "/sdcard/CurriculoAvatar/avatar.jpg";
        final File avatar = new File(pathName);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_tela_inicial);
        Toolbar toolbarTelaInicial = findViewById(R.id.toolbarTelaInicial);
        setSupportActionBar(toolbarTelaInicial);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_tela_inicial);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbarTelaInicial, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mensagem = new Mensagem();

        /*
        //TODO: Implementar a função para pegar dados do banco e incluir no menu
        //TODO: Implementar a função de conexão com o Banco de Dados
        // Não está pegando, pois os TextViews não estão sendo associados ao menu
        tvUsuario   = (TextView) navigationView.findViewById(R.id.tvUsuario);
        tvEmail     = (TextView) navigationView.findViewById(R.id.tvEmail);

        infoPessoais = infoPessoaisRepositorio.buscar();

        if (infoPessoais != null) {
            tvUsuario.setText(infoPessoais.nome);
            tvEmail.setText(infoPessoais.email);
        }
        */

        carregarAvatar = findViewById(R.id.imgAvatar);
        if (avatar.exists()) {
            carregarAvatar.setImageURI(Uri.parse(pathName));
        }

        carregarAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galeriaIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galeriaIntent, RESULT_LOAD_IMAGE);
            }
        });

        Button gerarCurriculo = findViewById(R.id.btGerarCurriculo);

        gerarCurriculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CriarPDF criarPDF;
                criarPDF = new CriarPDF(getApplicationContext());
                String filename = "curriculo.pdf";

                String pdfContent = criarPDF.generatePDF();

                criarPDF.outputToFile(filename,pdfContent,"ISO-8859-1");

                File file = new File(criarPDF.getNomeArquivo());

                if (file.exists()) {
                    Uri path = Uri.fromFile(file);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(path, "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(ActTelaInicial.this,
                                R.string.message_programa_adequado_pdf,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btGirarDireita = findViewById(R.id.btGirarDireita);
        btGirarEsquerda = findViewById(R.id.btGirarEsquerda);

        btGirarDireita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float deg = carregarAvatar.getRotation() + 90F;
                carregarAvatar.animate().rotation(deg).setInterpolator(new AccelerateDecelerateInterpolator());
            }
        });

        btGirarEsquerda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float deg = carregarAvatar.getRotation() - 90F;
                carregarAvatar.animate().rotation(deg).setInterpolator(new AccelerateDecelerateInterpolator());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && data != null) {
            Uri imagemSelecionada = data.getData();
            String pathName = PATH_NAME;
            String avatarName = AVATAR_NAME;
            ContentResolver contentResolver = getContentResolver();

            File avatarDir = new File(pathName);

            avatarDir.mkdirs();

            File copyFile = new File(avatarDir, avatarName);

            try {
                InputStream imagemOrigem = contentResolver.openInputStream(imagemSelecionada);
                OutputStream imagemDestino = new FileOutputStream(copyFile);

                byte[] qtBytes = new byte[1024];

                int cnt;
                while((cnt = imagemOrigem.read(qtBytes)) > 0) {
                    imagemDestino.write(qtBytes, 0, cnt);
                }

                imagemOrigem.close();
                imagemDestino.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            carregarAvatar.setImageURI(Uri.parse(String.valueOf(imagemSelecionada)));
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
}
