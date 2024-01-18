package br.com.infuse.core.exception;

public class ControlIdInUseException extends InfuseException{

    public ControlIdInUseException(final String message) {
        super(message);
    }

    public ControlIdInUseException(final String message, final Throwable ex) {
        super(message, ex);
    }

}
