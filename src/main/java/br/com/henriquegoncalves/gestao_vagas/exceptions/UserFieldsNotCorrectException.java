package br.com.henriquegoncalves.gestao_vagas.exceptions;

public class UserFieldsNotCorrectException extends RuntimeException {
    public UserFieldsNotCorrectException (){
        super("Campos Inválidos");
    }
}
