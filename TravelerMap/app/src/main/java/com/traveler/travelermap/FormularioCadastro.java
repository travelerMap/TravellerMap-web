package com.traveler.travelermap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FormularioCadastro extends AppCompatActivity {

    private EditText editTextNome, editEmail,editSenha;
    private Button botaoCadastrar;
    String[] mensagens = {"preencha todos os campos", "cadastro realizado"};
    String usuarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_cadastro);

        Objects.requireNonNull(getSupportActionBar()).hide();
        iniciarComponentes();

        botaoCadastrar.setOnClickListener(view -> {
            String nome = editTextNome.getText().toString();
            String email = editEmail.getText().toString();
            String senha = editSenha.getText().toString();

            if(nome.isEmpty() || email.isEmpty() || senha.isEmpty()){
                Snackbar snackbar = Snackbar.make(view, mensagens[0], Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(Color.WHITE);
                snackbar.setTextColor(Color.BLACK);
                snackbar.show();
            }else{
                cadastrarUsuario(view);
            }
        });
    }

    private void cadastrarUsuario(View view) {
        String nome = editTextNome.getText().toString();
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                salvarDadosDoUsuario();

                Snackbar snackbar = Snackbar.make(view, mensagens[1], Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(Color.WHITE);
                snackbar.setTextColor(Color.BLACK);
                snackbar.show();
            }else{
                String erro;
                try {
                    throw task.getException();

                }catch(FirebaseAuthWeakPasswordException e) {
                    erro = "a senha deve ter no minimo seis caracteres!!";
                }catch(FirebaseAuthUserCollisionException e ) {
                    erro = "já exixte um usuario cadastrado com esse e-mail!!";
                }catch(FirebaseAuthInvalidCredentialsException e ){
                    erro = "por favor, digite um e-mail valido!!";
                }catch(Exception e){
                    erro = "erro ao cadastrar usuario!!";
                }

                Snackbar snackbar = Snackbar.make(view, erro, Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(Color.WHITE);
                snackbar.setTextColor(Color.BLACK);
                snackbar.show();
            }
        });
    }

    private void salvarDadosDoUsuario() {
        String nome = editTextNome.getText().toString();
        String email = editEmail.getText().toString();

        FirebaseFirestore bancoDeDados = FirebaseFirestore.getInstance();

        Map<String, Object> usuarios = new HashMap<>();
        usuarios.put("nome", nome);
        usuarios.put("email", email);

        usuarioId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = bancoDeDados.collection("usuarios").document(usuarioId);
        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("bancoDeDados", "dados salvo");
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("bandoDeDados", "falha ao salvar os dados!" + e.toString());
            }
        });
    }

    private void iniciarComponentes() {
        editTextNome = findViewById(R.id.editTextNome);
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        botaoCadastrar = findViewById(R.id.botaoCadastrar);

    }
}