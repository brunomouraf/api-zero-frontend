package com.example.api_frontend_zero;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FormLogin extends AppCompatActivity {

    RequestQueue requestQueue;
    Button botao_entrar;
    TextView email;
    TextView senha;
    TextView botao_cadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fazerLogin();
    }

    public void fazerLogin(){
        setContentView(R.layout.activity_login);

        botao_cadastro = findViewById(R.id.text_tela_cadastro);
        botao_entrar = findViewById(R.id.bt_entrar);
        email = findViewById(R.id.edit_email);
        senha = findViewById(R.id.edit_senha);

        botao_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FormSingUp.class);
                startActivity(intent);
            }
        });

        botao_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://192.168.1.7:8080/after/login";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>(){

                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onResponse(String response){

                                try {
                                    JSONObject respObj = new JSONObject(response);
                                    System.out.println("Email: " + email.getText().toString());

                                    String fotoPerfil = respObj.getString("fotoPerfil");
                                    String nomeCompleto = respObj.getString("nomeCompleto");
                                    String numeroSeguidores = respObj.getString("numeroSeguidores");
                                    String descricao = respObj.getString("descricao");

                                    Bundle params = new Bundle();
                                    params.putString("fotoPerfil", respObj.getString("fotoPerfil"));
                                    params.putString("nomeCompleto", respObj.getString("nomeCompleto"));
                                    params.putString("descricao", respObj.getString("descricao"));
                                    params.putString("numeroSeguidores", respObj.getString("numeroSeguidores"));

                                    Intent intent = new Intent(FormLogin.this, PageHome.class);
                                    intent.putExtra("email", email.getText().toString());
                                    intent.putExtra("fotoPerfil", fotoPerfil);
                                    intent.putExtra("nomeCompleto", nomeCompleto);
                                    intent.putExtra("descricao", descricao);
                                    intent.putExtra("numeroSeguidores", numeroSeguidores);

                                    startActivity(intent);

                                } catch (JSONException e) {
                                    email.setText("");
                                    senha.setText("");
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FormLogin.this, "Usuario Incorreto", Toast.LENGTH_SHORT).show();
                    }
                })

                {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("email", email.getText().toString());
                        params.put("senha", senha.getText().toString());

                        return params;
                    }
                };

                requestQueue = Volley.newRequestQueue(FormLogin.this);
                requestQueue.add(stringRequest);
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        fazerLogin();
    }

    @Override
    protected void onStart() {
        super.onStart();
        fazerLogin();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
