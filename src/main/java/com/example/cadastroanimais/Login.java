package com.example.cadastroanimais;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

/**
 * Classe inicial da aplicação
 * Carrega a tela para que o usuario insira seus dados para utilizar o aplicativo
 * Também permite o cadastro de novos usuarios como pessoa fisica ou instituição
 */
public class Login extends AppCompatActivity {


    private EditText Email;
    private EditText Senha;
    private Button Entrar;
    private Button Cadastrar;
    ImageView Logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = findViewById(R.id.loginUsuario);
        Senha = findViewById(R.id.loginSenha);
        Entrar = findViewById(R.id.loginBotao);
        Cadastrar = findViewById(R.id.cadastroBotao);

        Logo = findViewById(R.id.logo);
        Logo.setImageResource(R.drawable.logo3);


        Entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userLogin();
            }
        });

        Cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuCadastro();
            }
        });


    }

    private void userLogin(){

        if(!ValidaCadastro()){
            return;
        }
        //valida_login(Nome.getText().toString(), Senha.getText().toString());
        final String email = Email.getText().toString().trim();
        final String senha = Senha.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){

                                SharedPreferences preferencias = getSharedPreferences("Pessoa", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferencias.edit();

                                editor.putString("id_pessoa", obj.getString("id_pessoa") );
                                editor.apply();

                                Intent i = new Intent(Login.this, TelaMenu.class);
                                startActivity(i);
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

    public void menuCadastro(){
        Intent i = new Intent(Login.this, MenuCadastro.class);
        startActivity(i);
    }


    public void cadastrar_usuario(View view) {
        Intent intent = new Intent(Login.this, TelaCadastroPessoaFisica.class);
        startActivity(intent);

    }

    /**
     *
     * Verifica se todos os campos foram preenchidos
     * @return true - Cadastro Válido || false - Cadastro Inválido
     */
    private boolean ValidaCadastro(){
        boolean valido = true;

        String email = Email.getText().toString();
        String senha = Senha.getText().toString();

        boolean res = false;

        if(res = !(Validacao.isEmailValido(email))){
            Email.requestFocus();

        }else if (res = Validacao.isCampoVazio(senha)) {
            Senha.requestFocus();
        }


        //Se houver erro aparece uma mensagem
        if(res){
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setTitle("Aviso!");
            alerta.setMessage("Há campos inválidos ou em branco!");
            alerta.setNeutralButton("Ok", null);
            alerta.show();
            valido = false;
        }

        return valido;
    }

}


