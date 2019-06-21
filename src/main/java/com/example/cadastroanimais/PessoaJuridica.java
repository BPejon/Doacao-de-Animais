package com.example.cadastroanimais;

import java.util.InputMismatchException;
/**
 * Esta classe representa uma pessoa juridica, ela eh uma subclasse da classe Pessoa
 * @author ICMC
 *
 */
public class PessoaJuridica extends Pessoa{

    private String CNPJ;
    private String nome_responsavel;

    /**
     * M�todo set do atributo cnpj
     * @param cnpj - o que sera setado no atributo
     */
    public void setCNPJ(String CNPJ){
        this.CNPJ = CNPJ;
    }

    /**
     * M�todo get do atributo cnpj
     * @return - cnpj da pessoa juridica
     */
    public String CNPJ(){
        return this.CNPJ;
    }

    /**
     * M�todo set do nome do responsavel da empresa
     * @param nome_responsavel - o que sera setado no atributo
     */
    public void setNomeResponsavel(String nome_responsavel){
        this.nome_responsavel = nome_responsavel;
    }

    /**
     * M�todo get do nome do responsavel da empresa
     * @return - nome do responsavel da empresa
     */
    public String getNomeResponsavel(){
        return this.nome_responsavel;
    }

    /**
     * Este m�todo verifica se um cnpj � valido, cnpj no formato de apenas numeros
     * @param CNPJ - CNPJ a ser verificado
     * @return - boolean se o cnpj eh valido ou nao
     */
    public static boolean verificaCNPJ(String CNPJ) {
        // considera-se erro CNPJ's formados por uma sequencia de numeros iguais
        if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111") ||
                CNPJ.equals("22222222222222") || CNPJ.equals("33333333333333") ||
                CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555") ||
                CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777") ||
                CNPJ.equals("88888888888888") || CNPJ.equals("99999999999999") ||
                (CNPJ.length() != 14))
            return(false);

        char dig13, dig14;
        int sm, i, r, num, peso;

        // "try" - protege o c�digo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i=11; i>=0; i--) {
                // converte o i-�simo caractere do CNPJ em um n�mero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posi��o de '0' na tabela ASCII)
                num = (int)(CNPJ.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig13 = '0';
            else dig13 = (char)((11-r) + 48);

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i=12; i>=0; i--) {
                num = (int)(CNPJ.charAt(i)- 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig14 = '0';
            else dig14 = (char)((11-r) + 48);

            // Verifica se os d�gitos calculados conferem com os d�gitos informados.
            if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13)))
                return(true);
            else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }

}
