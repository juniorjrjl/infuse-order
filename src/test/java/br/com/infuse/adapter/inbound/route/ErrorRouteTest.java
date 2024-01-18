package br.com.infuse.adapter.inbound.route;

import br.com.infuse.adapter.inbound.route.response.ErrorResponse;
import br.com.infuse.adapter.serializer.ISerializer;
import br.com.infuse.core.exception.ControlIdInUseException;
import br.com.infuse.core.exception.InfuseException;
import br.com.infuse.core.exception.OrderNotFoundException;
import br.com.infuse.core.exception.ValidatorException;
import br.com.infuse.core.exception.ValidatorExceptionField;
import br.com.infuse.util.ResponseStub;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spark.Request;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ErrorRouteTest {

    private ErrorRoute errorRoute;
    @Mock
    private ISerializer<ErrorResponse> errorResponseSerializer;

    @Mock
    private Request request;

    @BeforeEach
    void setup(){
        errorRoute = new ErrorRoute(errorResponseSerializer);
    }

    @Test
    void notFoundExceptionHandlerTest() throws Exception {
        when(request.url()).thenReturn("/test");
        when(request.contentType()).thenReturn("Application/json");
        when(errorResponseSerializer.toResponse(any(ErrorResponse.class), eq("Application/json"))).thenReturn("{'error': '404'}");
        ResponseStub response = new ResponseStub();
        Object body = errorRoute.notFoundRoute().handle(request, response);

        String json = (String) body;
        assertThat(response.getHeaders().keySet()).contains("Content-Type");
        assertThat(response.getHeaders().values()).contains("Application/json");
        assertThat(response.getStatusCode()).isEqualTo(404);
        assertThat(json).contains("{'error': '404'}");
    }

    @Test
    void exceptionHandlerTest() throws Exception {
        when(request.contentType()).thenReturn("Application/json");
        when(errorResponseSerializer.toResponse(any(ErrorResponse.class), eq("Application/json"))).thenReturn("{'error': '500'}");
        ResponseStub response = new ResponseStub();
        errorRoute.exceptionHandler().handle(new Exception(), request, response);

        assertThat(response.getHeaders().keySet()).contains("Content-Type");
        assertThat(response.getHeaders().values()).contains("Application/json");
        assertThat(response.getStatusCode()).isEqualTo(500);
    }

    @Test
    void infuseExceptionHandlerTest() throws Exception {
        when(request.contentType()).thenReturn("Application/json");
        when(errorResponseSerializer.toResponse(any(ErrorResponse.class), eq("Application/json"))).thenReturn("{'error': '500'}");
        ResponseStub response = new ResponseStub();
        errorRoute.infuseExceptionHandler().handle(new InfuseException(""), request, response);

        assertThat(response.getHeaders().keySet()).contains("Content-Type");
        assertThat(response.getHeaders().values()).contains("Application/json");
        assertThat(response.getStatusCode()).isEqualTo(500);
    }

    @Test
    void orderNotFoundExceptionHandlerTest() throws Exception {
        when(request.contentType()).thenReturn("Application/json");
        when(errorResponseSerializer.toResponse(any(ErrorResponse.class), eq("Application/json"))).thenReturn("{'error': '404'}");
        ResponseStub response = new ResponseStub();
        errorRoute.orderNotFoundExceptionHandler().handle(new OrderNotFoundException(""), request, response);

        assertThat(response.getHeaders().keySet()).contains("Content-Type");
        assertThat(response.getHeaders().values()).contains("Application/json");
        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    @Test
    void validatorExceptionHandlerTest() throws Exception {
        when(request.contentType()).thenReturn("Application/json");
        when(errorResponseSerializer.toResponse(any(ErrorResponse.class), eq("Application/json"))).thenReturn("{'error': '400'}");
        ResponseStub response = new ResponseStub();
        List<ValidatorExceptionField> fields = Collections.singletonList(ValidatorExceptionField.builder()
                        .field("stub")
                        .message("stub error")
                .build());
        errorRoute.validatorExceptionHandler().handle(new ValidatorException("error", fields), request, response);

        assertThat(response.getHeaders().keySet()).contains("Content-Type");
        assertThat(response.getHeaders().values()).contains("Application/json");
        assertThat(response.getStatusCode()).isEqualTo(400);
    }

    @Test
    void jsonProcessingExceptionHandlerTest() throws Exception {
        when(request.contentType()).thenReturn("Application/json");
        when(errorResponseSerializer.toResponse(any(ErrorResponse.class), eq("Application/json"))).thenReturn("{'error': '400'}");
        ResponseStub response = new ResponseStub();
        errorRoute.jsonProcessingExceptionHandler().handle(new JsonProcessingException(""){}, request, response);

        assertThat(response.getHeaders().keySet()).contains("Content-Type");
        assertThat(response.getHeaders().values()).contains("Application/json");
        assertThat(response.getStatusCode()).isEqualTo(400);
    }

    @Test
    void controlIdInUseException() throws JsonProcessingException {
        when(request.contentType()).thenReturn("Application/json");
        when(errorResponseSerializer.toResponse(any(ErrorResponse.class), eq("Application/json"))).thenReturn("{'error': '409'}");
        ResponseStub response = new ResponseStub();

        errorRoute.controlIdInUseException().handle(new ControlIdInUseException(""), request, response);

        assertThat(response.getHeaders().keySet()).contains("Content-Type");
        assertThat(response.getHeaders().values()).contains("Application/json");
        assertThat(response.getStatusCode()).isEqualTo(409);
    }

}
