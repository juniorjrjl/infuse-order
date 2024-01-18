package br.com.infuse.adapter.validator;

import br.com.infuse.adapter.inbound.route.request.OrderInsertRequest;
import br.com.infuse.core.exception.ValidatorException;
import br.com.infuse.core.exception.ValidatorExceptionField;
import br.com.infuse.util.datafaker.CustomFaker;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidatorRequestTest {

    private final CustomFaker faker = CustomFaker.getInstance();

    private ValidatorRequest validatorRequest;

    @Mock
    private Validator validator;

    @BeforeEach
    void setup(){
        validatorRequest = new ValidatorRequestImpl(validator);
    }

    @Test
    void whenValidationNotFoundErrors_ThenDoesNotTrowError(){
        OrderInsertRequest request = faker.request().getOrderInsertRequestBot().build();
        when(validator.validate(eq(request))).thenReturn(Collections.emptySet());

        assertThatCode(() -> validatorRequest.validate(request)).doesNotThrowAnyException();
        verify(validator).validate(eq(request));

    }

    @Test
    void whenValidationFoundErrors_ThenTrowError(){
        OrderInsertRequest request = faker.request().getOrderInsertRequestBot().build();
        Set<ConstraintViolation<OrderInsertRequest>> constraints = new HashSet<>();
        ConstraintViolation<OrderInsertRequest> constraint = ConstraintViolationImpl.forBeanValidation(null, null, null, null, null, null, null, null, PathImpl.createPathFromString("teste"), null, null);
        constraints.add(constraint);
        when(validator.validate(eq(request))).thenReturn(constraints);

        List<ValidatorExceptionField> fields = Collections.singletonList(ValidatorExceptionField.builder()
                        .field("teste")
                .build());
        assertThatThrownBy(() -> validatorRequest.validate(request))
                .isInstanceOf(ValidatorException.class)
                .hasMessage("Sua requisição contém erros")
                .hasFieldOrPropertyWithValue("fields", fields);
        verify(validator).validate(eq(request));

    }

}
