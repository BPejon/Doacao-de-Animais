package com.example.cadastroanimais;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class TelaCadastroPessoaFisica extends AppCompatActivity {
    private EditText Email;
    private EditText Password;
    private EditText PasswordCheck;
    private Button ConfirmaCadastroBotao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro_pessoa_fisica);

        /*
        Email = findViewById(R.id.fieldEmail);
        Password = findViewById(R.id.fieldSenha1);
        PasswordCheck = findViewById(R.id.fieldConfirmaSenha);
        ConfirmaCadastroBotao = findViewById(R.id.confirmaCadastro);

        final boolean checaEmail;
        final boolean checaSenha;

        checaEmail = isValidEmailAddressRegex(Email.getText().toString());
        if(!checaEmail){
            System.out.printf("Email inválido.");
        }

        checaSenha = isValidPassword(Password.getText().toString(), PasswordCheck.getText().toString());
        if(!checaSenha){
            System.out.printf("Senhas não conferem.");
        }

        ConfirmaCadastroBotao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidEmailAddressRegex(Email.getText().toString()) && isValidPassword(Password.getText().toString(), PasswordCheck.getText().toString())){
                    cria_novo_usuario(Email.getText().toString(), Password.getText().toString());
                    AlertDialog.Builder cadastrado = new AlertDialog.Builder(telaCadastroPessoaFisica.this);
                    cadastrado.setTitle("Aviso:");
                    cadastrado.setMessage("Usuário cadastrado com sucesso!");
                    cadastrado.setNeutralButton("Ok.", null);
                    cadastrado.show();
                    open_telaMenu();
                }
                else{
                    AlertDialog.Builder alerta = new AlertDialog.Builder(telaCadastroPessoaFisica.this);
                    alerta.setTitle("Aviso:");
                    alerta.setMessage("Usuário ou senha inválidos. usuario: " + checaEmail + " senha: " + checaSenha);
                    alerta.setNeutralButton("Ok.", null);
                    alerta.show();
                }
            }
        });

    }

    public static boolean isValidEmailAddressRegex(String email) {
        boolean isEmailIdValid = false;
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                System.out.println("Email é valido!!!!!");
                isEmailIdValid = true;
            }
            else{
                System.out.println("Email não é valido!!!!!");

            }
        }
        return isEmailIdValid;
    }

    public boolean isValidPassword(String senha, String senhaConfirma){
        if(senha.equals(senhaConfirma) ){
            System.out.println("Senhas conferem!!!!!");
            return true;
        }
        System.out.println("Senhas não conferem!");
        return false;
    }

    public void cria_novo_usuario(String username, String senha){
        //adicionar no banco de dados

    }

    public void open_telaMenu(){
        Intent intent = new Intent(telaCadastroPessoaFisica.this, telaMenu.class);
        startActivity(intent);
    }
    */
    }
}
