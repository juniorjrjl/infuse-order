package br.com.infuse.adapter.validator;

import br.com.infuse.core.exception.ValidatorException;
import br.com.infuse.core.exception.ValidatorExceptionField;
import com.google.inject.Inject;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor(onConstructor = @__({@Inject}))
public class ValidatorRequestImpl implements ValidatorRequest {

    private final javax.validation.Validator validator;

    @Override
    public void validate(final Object... request) {
        Set<ConstraintViolation<Object>> constraints = new HashSet<>();
        Arrays.stream(request).forEach(r -> constraints.addAll(validator.validate(r)));

        if (constraints.isEmpty()) return;

        List<ValidatorExceptionField> fields = new ArrayList<>();
        constraints.forEach(c -> fields.add(ValidatorExceptionField.builder()
                        .field(c.getPropertyPath().toString())
                        .message(c.getMessage())
                .build()));

        throw new ValidatorException("Sua requisição contém erros", fields);
    }

}
