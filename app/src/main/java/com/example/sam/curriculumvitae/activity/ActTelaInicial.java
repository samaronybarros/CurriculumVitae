package com.example.sam.curriculumvitae.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.sam.curriculumvitae.R;

public class ActTelaInicial extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_info_pessoais:
                    //mTextMessage.setText(R.string.title_info_pessoais);
                    goToScreenInfo();
                    return true;
                case R.id.navigation_objetivo:
                    //mTextMessage.setText(R.string.title_objetivo);
                    goToScreenObjetivo();
                    return true;
                case R.id.navigation_formacao:
                    //mTextMessage.setText(R.string.title_formacao);
                    goToScreenFormacao();
                    return true;
                case R.id.navigation_experiencia:
                    mTextMessage.setText(R.string.title_experiencia);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_tela_inicial);

        mTextMessage = (TextView) findViewById(R.id.message);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
}
