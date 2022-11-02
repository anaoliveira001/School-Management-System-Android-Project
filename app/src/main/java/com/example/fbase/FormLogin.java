package com.example.fbase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class FormLogin extends AppCompatActivity {

    private TextView find_us;
    private EditText edit_email, edit_senha;
    private Button entrar;
    private ProgressBar progressBar;
    String [] mensagens ={"Please, fill in all the fields", "Successful Login, Welcome"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);

        Objects.requireNonNull(getSupportActionBar()).hide();
        InciarComponentes();

        find_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FormLogin.this, ContactUs.class);
                startActivity(intent);
            }
        });

        entrar.setOnClickListener(view -> {
            String email = edit_email.getText().toString();
            String senha = edit_senha.getText().toString();

            if(email.isEmpty() || senha.isEmpty()){
                Snackbar snackbar = Snackbar.make(view, mensagens[0], Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(Color.RED);
                snackbar.setTextColor(Color.BLACK);
                snackbar.show();
            }else{
                AutenticarUsuario(view);
            }
        });
    }

    private void AutenticarUsuario(View view){
        String email = edit_email.getText().toString();
        String senha = edit_senha.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressBar.setVisibility(View.VISIBLE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            TelaPrincipal();
                        }
                    },3000);
                }else{
                    String erro;

                    try {
                        throw Objects.requireNonNull(task.getException());
                    }catch (Exception e){
                        erro = "Error, try again";
                    }
                    Snackbar snackbar = Snackbar.make(view, erro, Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();

                    TelaPrincipal();
                }
            }
        });
    }

    // seempre que a aplicação iniciar, irá carregar o que já está na Tela Principal
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();

        if (usuarioAtual != null){
            TelaPrincipal();

        }
    }

    private void TelaPrincipal(){
        Intent intent = new Intent(FormLogin.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void InciarComponentes(){
        find_us = findViewById(R.id.findUs);
        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);
        entrar = findViewById(R.id.entrar);
        progressBar = findViewById(R.id.progressBar);
    }
}
