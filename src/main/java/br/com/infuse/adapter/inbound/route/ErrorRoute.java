package br.com.infuse.adapter.inbound.route;

import br.com.infuse.core.exception.ControlIdInUseException;
import br.com.infuse.core.exception.InfuseException;
import br.com.infuse.core.exception.OrderNotFoundException;
import br.com.infuse.core.exception.ValidatorException;
import br.com.infuse.adapter.inbound.route.response.ErrorResponse;
import br.com.infuse.adapter.inbound.route.response.FieldErrorResponse;
import br.com.infuse.adapter.serializer.ISerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spark.ExceptionHandler;
import spark.Route;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor(onConstructor = @__({@Inject}))
public class ErrorRoute {

    private final ISerializer<ErrorResponse> errorResponseSerializer;


    public Route notFoundRoute(){
        return (req, res) -> {
            res.header("Content-Type", req.contentType());
            res.status(404);
            log.error("notFoundError");
            return toResponse(ErrorResponse.builder()
                    .status(404)
                    .title("Route Not Found")
                    .details("The route '" + req.url() + "' has not been mapped")
                    .build(), req.contentType());
        };
    }

    public ExceptionHandler<Exception> exceptionHandler(){
        return (ex, req, res) ->{
            res.header("Content-Type", req.contentType());
            res.status(500);
            log.error("Exception", ex);
            res.body(toResponse(ErrorResponse.builder()
                    .status(500)
                    .title("Unexpected Error")
                    .details("A unexpected error happened")
                    .build(), req.contentType()));
        };
    }

    public ExceptionHandler<InfuseException> infuseExceptionHandler(){
        return (ex, req, res) ->{
            res.header("Content-Type", req.contentType());
            res.status(500);
            log.error("InfuseException", ex);
            res.body(toResponse(ErrorResponse.builder()
                    .status(500)
                    .title("Unexpected Error")
                    .details("A unexpected error happened")
                    .build(), req.contentType()));
        };
    }

    public ExceptionHandler<OrderNotFoundException> orderNotFoundExceptionHandler(){
        return (ex, req, res) ->{
            res.header("Content-Type", req.contentType());
            res.status(404);
            log.error("OrderNotFoundException", ex);
            res.body(toResponse(ErrorResponse.builder()
                    .status(404)
                    .title("Order not found")
                    .details(ex.getMessage())
                    .build(), req.contentType()));
        };
    }

    public ExceptionHandler<ValidatorException> validatorExceptionHandler(){
        return (ex, req, res) ->{
            List<FieldErrorResponse> fields = ex.getFields().stream()
                    .map(f -> FieldErrorResponse.builder()
                            .name(f.getField())
                            .message(f.getMessage())
                            .build())
                    .collect(Collectors.toList());
            res.header("Content-Type", req.contentType());
            res.status(400);
            log.error("ValidatorException", ex);
            res.body(toResponse(ErrorResponse.builder()
                    .status(400)
                    .title("A request have errors")
                    .details("Please check your information and try again")
                    .fields(fields)
                    .build(), req.contentType()));
        };
    }

    public ExceptionHandler<JsonProcessingException> jsonProcessingExceptionHandler() {
        return (ex, req, res) ->{
            res.header("Content-Type", req.contentType());
            res.status(400);
            log.error("JsonProcessingException", ex);
            res.body(toResponse(ErrorResponse.builder()
                    .status(400)
                    .title("A request have errors")
                    .details(ex.getMessage())
                    .build(), req.contentType()));
        };
    }

    public ExceptionHandler<ControlIdInUseException> controlIdInUseException() {
        return (ex, req, res) ->{
            res.header("Content-Type", req.contentType());
            res.status(409);
            log.error("ControlIdInUseException", ex);
            res.body(toResponse(ErrorResponse.builder()
                    .status(400)
                    .title("A request have errors")
                    .details(ex.getMessage())
                    .build(), req.contentType()));
        };
    }

    private String toResponse(final ErrorResponse response, final String format) {
        try {
            return errorResponseSerializer.toResponse(response, format);
        }catch (Exception ex){
            throw new InfuseException("Error to parse response", ex);
        }
    }

}
