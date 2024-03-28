package com.br.picpaysimplificado.infra.exceptions;

public class ValidateException extends RuntimeException{

    public ValidateException(){
        super("Erro de validação!");
    }

    public ValidateException(String msg){
        super(msg);
    }
}
