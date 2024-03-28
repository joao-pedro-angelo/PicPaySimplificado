package com.br.picpaysimplificado.infra.exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String msg){
        super(msg);
    }

    public NotFoundException(){
        super("Não encontrado!");
    }
}
