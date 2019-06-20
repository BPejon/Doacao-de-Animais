package com.example.cadastroanimais;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class Login extends AppCompatActivity {


    private EditText Nome;
    private EditText Senha;
    private Button Entrar;
    private Button Cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Nome = findViewById(R.id.loginUsuario);
        Senha = findViewById(R.id.loginSenha);
        Entrar = findViewById(R.id.loginBotao);
        Cadastrar = findViewById(R.id.cadastroBotao);



        Entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userLogin();
            }
        });


    }

    private void userLogin(){
        //valida_login(Nome.getText().toString(), Senha.getText().toString());
        final String email = Nome.getText().toString().trim();
        final String senha = Senha.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                Toast.makeText(getApplicationContext(), obj.getString("id_pessoa"), Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("senha", senha);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    //funcao que valida o login e senha
    public void valida_login(String usuarioLog, String senhaLog) {


        if (usuarioLog.equals("a") && senhaLog.equals("a")) {
            Intent intent = new Intent(this, TelaMenu.class); //SecondActivity será a pagina que abrirá ao logar
            // pra adicionar uma nova activity, clicar em com.example.login -> new -> Activity -> empty
            startActivity(intent);

        } else {
            System.out.println("Usuário não cadastrado");
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setTitle("Aviso:");
            alerta.setMessage("Há campos inválidos ou em branco.");
            alerta.setNeutralButton("Ok.", null);
            alerta.show();
        }
    }

    public void cadastrar_usuario(View view) {
        Intent intent = new Intent(Login.this, TelaCadastroPessoaFisica.class);
        startActivity(intent);

    }
}


