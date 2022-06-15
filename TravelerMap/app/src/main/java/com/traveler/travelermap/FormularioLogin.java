package com.traveler.travelermap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Objects;

public class FormularioLogin extends AppCompatActivity {

    private TextView text_tela_cadastro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_login);

        Objects.requireNonNull(getSupportActionBar()).hide();
        iniciarComponentes();

        text_tela_cadastro.setOnClickListener(view -> {
            Intent intent = new Intent(FormularioLogin.this, FormularioCadastro.class);
            startActivity(intent);
        });
    }

    private void iniciarComponentes(){
        text_tela_cadastro = findViewById(R.id.textoCadastreSe);
    }
}