package com.example.api_frontend_zero;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class FormSingUp extends AppCompatActivity implements View.OnClickListener{

    EditText etNome;
    EditText etEmail;
    EditText etSenha;
    EditText etTelefone;
    EditText etCpf;

    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        etNome = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etSenha = findViewById(R.id.et_senha);
        etTelefone = findViewById(R.id.et_telefone);
        etCpf = findViewById(R.id.et_cpf);


        Button botao = findViewById(R.id.bt_cadastrar);
        botao.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(FormSingUp.this, FormLogin.class);
        startActivity(intent);
        System.out.println("parou aqui");

        String url = "http://192.168.1.10:8080/zero/registration";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> Toast.makeText(FormSingUp.this, "Cadastrado", Toast.LENGTH_LONG).show(),
                error -> Toast.makeText(FormSingUp.this, "Erro", Toast.LENGTH_LONG).show()){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", etNome.getText().toString());
                params.put("email", etEmail.getText().toString());
                params.put("password", etSenha.getText().toString());
                params.put("phoneNumber", etTelefone.getText().toString());
                params.put("cpf", etCpf.getText().toString());

                return params;
            }
        };

        requestQueue = Volley.newRequestQueue(FormSingUp.this);
        requestQueue.add(stringRequest);
    }
}
