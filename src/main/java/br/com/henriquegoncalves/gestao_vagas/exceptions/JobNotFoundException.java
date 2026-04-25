package br.com.henriquegoncalves.gestao_vagas.exceptions;

public class JobNotFoundException  extends RuntimeException {
    public JobNotFoundException (){
        super("Job não existe");
    }
}
