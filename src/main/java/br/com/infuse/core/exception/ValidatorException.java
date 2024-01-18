package br.com.infuse.core.exception;

import java.util.List;

public class ValidatorException extends InfuseException {

    private final List<ValidatorExceptionField> fields;

    public ValidatorException(final String message, final List<ValidatorExceptionField> fields){
        super(message);
        this.fields = fields;
    }

    public ValidatorException(final  String message, List<ValidatorExceptionField> fields, final Throwable ex){
        super(message, ex);
        this.fields = fields;
    }

    public List<ValidatorExceptionField> getFields() {
        return fields;
    }

}
