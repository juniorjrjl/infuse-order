package br.com.infuse.core.exception;

import java.util.Objects;

public class ValidatorExceptionField {

    private final String field;
    private final String message;

    public static ValidatorExceptionFieldBuilder builder(){
        return new ValidatorExceptionFieldBuilder();
    }

    public ValidatorExceptionField(final String field, final String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidatorExceptionField that = (ValidatorExceptionField) o;
        return Objects.equals(field, that.field) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, message);
    }

    public static final class ValidatorExceptionFieldBuilder {
        private String field;
        private String message;

        public ValidatorExceptionFieldBuilder field(String field) {
            this.field = field;
            return this;
        }

        public ValidatorExceptionFieldBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ValidatorExceptionField build() {
            return new ValidatorExceptionField(field, message);
        }
    }
}
