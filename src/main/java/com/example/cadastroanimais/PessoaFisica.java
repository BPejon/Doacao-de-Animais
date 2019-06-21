package com.example.cadastroanimais;

/**
 * Esta classe representa uma pessoa fisica, ela eh uma subclasse da classe Pessoa
 *
 */
public class PessoaFisica extends Pessoa {

    private String CPF;

    /**
     * M�todo set do atributo cpf
     * @param cpf - o que sera setado no atributo
     */
    public void setCPF(String cpf){
        this.CPF = cpf;
    }

    /**
     * M�todo get do atributo cpf
     * @return - cpf da pessoa fisica
     */
    public String getCPF(){
        return this.CPF;
    }

    /**
     * Este m�todo verifica se um cpf � valido, cpf no formato de apenas numeros
     * @param CPF - CPF a ser verificado
     * @return - boolean se o cpf � valido ou nao
     */
    public static boolean verificaCPF(String CPF){

        int[] peso = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        String aux = CPF.substring(0,9);

        int soma = 0;

        for (int indice=aux.length()-1, digito; indice >= 0; indice-- ) {
            digito = Integer.parseInt(aux.substring(indice,indice+1));
            soma += digito*peso[peso.length-aux.length()+indice];
        }

        soma = 11 - soma % 11;

        if(soma > 9){
            soma = 0;
        }

        Integer digito1 = soma;


        aux = CPF.substring(0,9) + digito1;

        soma = 0;

        for (int indice=aux.length()-1, digito; indice >= 0; indice-- ) {
            digito = Integer.parseInt(aux.substring(indice,indice+1));
            soma += digito*peso[peso.length-aux.length()+indice];
        }

        soma = 11 - soma % 11;

        if(soma > 9){
            soma = 0;
        }

        Integer digito2 = soma;

        return(CPF.equals(CPF.substring(0,9) + digito1.toString() + digito2.toString()));

    }

}
