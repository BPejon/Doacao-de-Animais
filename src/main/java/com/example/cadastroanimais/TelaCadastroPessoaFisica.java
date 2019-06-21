package com.example.cadastroanimais;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TelaCadastroPessoaFisica extends AppCompatActivity {
    private EditText Email;
    private EditText Senha;
    private EditText SenhaCheck;
    private EditText Nome, Telefone, CPF, Endereco, Cidade, Estado;
    private Button ConfirmaCadastroBotao;
    private Spinner spinnerSexo, spinnerEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro_pessoa_fisica);

        //Edits
        Nome =      findViewById(R.id.editNome);
        Telefone =  findViewById(R.id.editTel);
        CPF =       findViewById(R.id.editCPF);
        Endereco =  findViewById(R.id.editEndereco);
        Cidade =    findViewById(R.id.editCidade);
        Email =     findViewById(R.id.editMail);
        Senha =     findViewById(R.id.editSenha);
        SenhaCheck = findViewById(R.id.editSenhaConfirma);

        //Botao
        ConfirmaCadastroBotao = findViewById(R.id.buttonCadastrar);

        //Spinner
        spinnerSexo = findViewById(R.id.spinnerSexo);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.SexoPessoa, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSexo.setAdapter(adapter);

        spinnerEstado = findViewById(R.id.spinnerEstado);
        ArrayAdapter<CharSequence> adapt = ArrayAdapter.createFromResource(this, R.array.Estados, android.R.layout.simple_spinner_item);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstado.setAdapter(adapt);

        ConfirmaCadastroBotao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ValidaCadastro()) {
                    //Manda  informaçoes para o banco de dados
                    FuncaoCadastrarPessoa();
                }
            }
        });

    }

    /*
    ImageView imagemAnimal;
    EditText setIdade, setNome, setBreveDescricao;
    Button botaoFinalizar, botaoEditar;
    Spinner spinnerEspecie, spinnerSexo, spinnerCondicao;
    */
    private boolean ValidaCadastro() {
        boolean valido = true;

        String nome =         Nome.getText().toString().trim();
        String telefone =     Telefone.getText().toString().trim();
        String cpf =          CPF.getText().toString().trim().trim();
        String endereco =     Endereco.getText().toString().trim();
        String cidade =       Cidade.getText().toString().trim();
        String email =        Email.getText().toString().trim();
        String senha =        Senha.getText().toString().trim();
        String senhaCheck =   SenhaCheck.getText().toString().trim();


        boolean res = false;

        if (res = Validacao.isCampoVazio(nome)) {
            Nome.requestFocus();
            Nome.setError("Erro");
        } else if (res = Validacao.isCampoVazio(telefone)) {
            Telefone.requestFocus();
            Telefone.setError("Erro");

          //Verifica se o cpf está em branco ou se está correto!
        } else if (res = (Validacao.isCampoVazio(cpf) && !PessoaFisica.verificaCPF(cpf))) {
            CPF.requestFocus();
            CPF.setError("Erro Cpf inváido!");
        } else if (res = Validacao.isCampoVazio(endereco)) {
            Endereco.requestFocus();
            Endereco.setError("Erro");

        } else if (res = Validacao.isCampoVazio(endereco)) {
            Endereco.requestFocus();
            Endereco.setError("Erro");


        } else if (res = Validacao.isCampoVazio(cidade)) {
            Cidade.requestFocus();
            Cidade.setError("Erro");


        } else if (res = !Validacao.isEmailValido(email)) {
            Email.requestFocus();
            Email.setError("Erro");


        } else if (res = Validacao.isCampoVazio(senha)) {
            Senha.requestFocus();
            Senha.setError("Erro");


        } else if (res = Validacao.isCampoVazio(senhaCheck)) {
            SenhaCheck.requestFocus();
            SenhaCheck.setError("Erro");
        }

        //Verifica se a senha digitada e a confirmação são iguais
        if(!senha.equals(senhaCheck)){
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setTitle("Aviso!");
            alerta.setMessage("Senhas não conferem!!");
            alerta.setNeutralButton("Ok", null);
            alerta.show();
            valido = false;
        }

        else if(res){
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
     * Funcao que cadastra uma Ong em nosso banco de dados no SQL
     */
    private void FuncaoCadastrarPessoa(){
        final String nome =         Nome.getText().toString();
        final String telefone =     Telefone.getText().toString();
        final String cpf =          CPF.getText().toString();
        final String endereco =     Endereco.getText().toString();
        final String cidade =       Cidade.getText().toString();
        final String email =        Email.getText().toString();
        final String senha =        Senha.getText().toString();

        int pos;

        pos = spinnerSexo.getSelectedItemPosition();
        final String sexo = spinnerSexo.getItemAtPosition(pos).toString();

        pos = spinnerEstado.getSelectedItemPosition();
        final String estado = spinnerEstado.getItemAtPosition(pos).toString();

        /**
         * Manda o registro do usuario para o banco de dados
         */
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.URL_PESSOA,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                if(!obj.getBoolean("error")){
                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(TelaCadastroPessoaFisica.this, Login.class);
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
                    params.put("nome", nome);
                    params.put("telefone", telefone);
                    params.put("cpf", cpf);
                    params.put("endereco", endereco);
                    params.put("cidade", cidade);
                    params.put("uf", estado);
                    params.put("email", email);
                    params.put("senha", senha);
                    params.put("sexo", sexo);
                    return params;
                }
            };

            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);




    }//Fim da cadastra pessoa


    public void open_telaMenu(){
        Intent intent = new Intent(TelaCadastroPessoaFisica.this, TelaMenu.class);
        startActivity(intent);
    }

}
