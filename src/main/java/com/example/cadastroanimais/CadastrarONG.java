package com.example.cadastroanimais;

import android.content.Intent;
import android.media.MediaCodec;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class CadastrarONG extends AppCompatActivity {

    Button CadastroOng;
    EditText CNPJ, NomeONG, Email, Telefone, NomeResp, Endereco, Cidade, Senha, ConfirmarSenha;
    Spinner spinnerEstado;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_ong);

        CNPJ = (EditText) findViewById(R.id.set_cnpj);
        NomeONG = (EditText) findViewById(R.id.set_nome);
        Email = (EditText) findViewById(R.id.set_email);
        Telefone = (EditText) findViewById(R.id.set_telefone);
        NomeResp = (EditText) findViewById(R.id.set_nome_resp);
        Endereco = (EditText) findViewById(R.id.set_endereco);
        Cidade = (EditText) findViewById(R.id.set_cidade);
        Senha = (EditText) findViewById(R.id.set_senha);
        ConfirmarSenha = (EditText) findViewById(R.id.set_confirma_senha);

        CadastroOng = findViewById(R.id.cadastroBotao);

        spinnerEstado= findViewById(R.id.spinner_estado);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Estados, android.R.layout.simple_spinner_item);
        spinnerEstado.setAdapter(adapter);

        CadastroOng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ValidaCadastro()) {
                    FuncaoCadastrarOng();
                    Intent trocar_tela = new Intent(CadastrarONG.this, Login.class);
                    startActivity(trocar_tela);
                }

            }
        });
    }

    /**
     * Funcao que cadastra uma Ong em nosso banco de dados no SQL
     */
    private void FuncaoCadastrarOng(){
        int posicaospinnerEstado =  spinnerEstado.getSelectedItemPosition();

        final String cnpj    =      CNPJ.getText().toString().trim();
        final String nomeOng =      NomeONG.getText().toString();
        final String email   =      Senha.getText().toString().trim();
        final String telefone=      Telefone.getText().toString().trim();
        final String nomeResp=      NomeResp.getText().toString();
        final String endereco=      Endereco.getText().toString();
        final String cidade  =      Cidade.getText().toString();
        final String estado  =      spinnerEstado.getItemAtPosition(posicaospinnerEstado).toString();
        final String senha   =      Senha.getText().toString().trim();



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

            /**
             *
             * @return retorna o Hashmap com os dados da ONG
             * @throws AuthFailureError
             */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("cnpj", cnpj);
                params.put("nomeOng", nomeOng);
                params.put("email", email);
                params.put("telefone", telefone);
                params.put("nomeResp", nomeResp);
                params.put("endereco", endereco);
                params.put("cidade", cidade);
                params.put("email", email);
                params.put("estado", estado);
                params.put("senha", senha);

                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    /**
     *
     * Verifica se todos os campos foram preenchidos
     * @return true - Cadastro Válido || false - Cadastro Inválido
     */
    private boolean ValidaCadastro(){
        boolean valido = true;

        String cnpj = CNPJ.getText().toString();
        String nomeONG = NomeONG.getText().toString();
        String email = Email.getText().toString();
        String telefone = Telefone.getText().toString();
        String nomeResp = NomeResp.getText().toString();
        String endereco = Endereco.getText().toString();
        String cidade = Cidade.getText().toString();
        String senha = Senha.getText().toString();
        String confirmarsenha = ConfirmarSenha.getText().toString();

        boolean res = false;

        if(res = isCampoVazio(cnpj)) {
            NomeONG.requestFocus();

        }else if (res = isCampoVazio(nomeONG)) {
            Endereco.requestFocus();

        }else if(res = !isEmailValido(email)) {
            Email.requestFocus();

        }else if(res = isCampoVazio(telefone)){
            Telefone.requestFocus();

        }else if(res = isCampoVazio(nomeResp)) {
            NomeResp.requestFocus();

        }else if(res = isCampoVazio(endereco)){
            Endereco.requestFocus();

        }else if(res = isCampoVazio(cidade)){
            Cidade.requestFocus();

        }else if(res = isCampoVazio(senha)){
            Senha.requestFocus();

        }else if(res = isCampoVazio(confirmarsenha)){
            ConfirmarSenha.requestFocus();
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

    /**
     * Verifica se o campo está vazio
     * @param valor string a ser avaliada
     * @return true - campo é vazio || false - campo não é vaio
     */
    private boolean isCampoVazio(String valor){
        boolean resultado = (valor.trim().isEmpty());
        return resultado;
    }

    /**
     * Verifica se o Email é valido
     * @param email
     * @return
     */
    private boolean isEmailValido(String email){
        boolean resultado = (!isCampoVazio(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        return resultado;
    }
}
