package br.com.infuse.adapter.inbound.route;

import br.com.infuse.adapter.deserializer.Deserializer;
import br.com.infuse.adapter.inbound.route.request.OrderInsertRequest;
import br.com.infuse.adapter.inbound.route.response.OrderInsertedResponse;
import br.com.infuse.adapter.mapper.route.NewOrderMapper;
import br.com.infuse.adapter.serializer.ISerializer;
import br.com.infuse.adapter.validator.ValidatorRequest;
import br.com.infuse.core.domain.insert.OrderInsertedDomain;
import br.com.infuse.core.service.OrderService;
import com.google.inject.Inject;
import lombok.AllArgsConstructor;
import spark.Route;

import java.util.List;

@AllArgsConstructor(onConstructor = @__({@Inject}))
public class NewOrderRoute {

    private final Deserializer<OrderInsertRequest> deserializer;
    private final ISerializer<OrderInsertedResponse> serializer;
    private final ValidatorRequest validatorRequest;
    private final NewOrderMapper mapper;
    private final OrderService service;

    public Route resolve(){
        return (request, response) ->{
            List<OrderInsertRequest> body = deserializer.toListRequest(request.body(), request.contentType(), OrderInsertRequest.class);
            validatorRequest.validate(body.toArray());
            List<OrderInsertedDomain> domain = service.save(mapper.toDomain(body));
            response.status(201);
            response.header("Content-Type", request.contentType());
            return serializer.toResponse(mapper.toResponse(domain), request.contentType());
        };
    }

}
