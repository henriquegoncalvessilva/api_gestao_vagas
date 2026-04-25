package br.com.henriquegoncalves.gestao_vagas.exceptions;

public class CompanynotFoundException extends RuntimeException {
    public CompanynotFoundException (){
        super("Company not found");
    }
}
