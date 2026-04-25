package br.com.henriquegoncalves.gestao_vagas.exceptions;

public class UserNotFoundException  extends RuntimeException {
    public UserNotFoundException (){
        super("Usuario não existe");
    }
}
