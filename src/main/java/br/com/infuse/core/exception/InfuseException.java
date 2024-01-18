package br.com.infuse.core.exception;

public class InfuseException extends RuntimeException{

    public InfuseException(final String message){
        super(message);
    }

    public InfuseException(final String message, final Throwable ex){
        super(message, ex);
    }

}
