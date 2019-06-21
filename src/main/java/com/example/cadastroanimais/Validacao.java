package com.example.cadastroanimais;

import android.support.v7.app.AlertDialog;
import android.util.Patterns;

/**
 * Classe utilizana na validacao dos campos obrigatorios
 */
public class Validacao {
    /**
     * Verifica se o campo está vazio
     * @param valor string a ser avaliada
     * @return true - campo é vazio || false - campo não é vaio
     */
    public static boolean isCampoVazio(String valor){
        boolean resultado = (valor.trim().isEmpty());
        return resultado;
    }

    /**
     * Verifica se o Email é valido
     * @param email
     * @return
     */
    public static boolean isEmailValido(String email){
        boolean resultado = (!isCampoVazio(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        return resultado;
    }
}
