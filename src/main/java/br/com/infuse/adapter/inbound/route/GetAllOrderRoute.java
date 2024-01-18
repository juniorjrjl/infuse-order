package br.com.infuse.adapter.inbound.route;

import br.com.infuse.adapter.inbound.route.response.FindAllOrderResponse;
import br.com.infuse.adapter.mapper.route.GetAllOrderMapper;
import br.com.infuse.adapter.serializer.ISerializer;
import br.com.infuse.core.domain.findall.FindAllOrderDomain;
import br.com.infuse.core.service.OrderQueryService;
import com.google.inject.Inject;
import lombok.AllArgsConstructor;
import spark.QueryParamsMap;
import spark.Route;

import java.util.List;

@AllArgsConstructor(onConstructor = @__({@Inject}))
public class GetAllOrderRoute {

    private final GetAllOrderMapper mapper;
    private final ISerializer<FindAllOrderResponse> serializer;
    private final OrderQueryService queryService;

    public Route resolve(){
        return (request, response) ->{
            QueryParamsMap queryParams = request.queryMap();
            List<FindAllOrderDomain> domain = queryService.findAll(mapper.toDomain(queryParams));
            String responseFormat = request.contentType() == null ? "application/json" : request.contentType();
            response.status(200);
            response.header("Content-Type", responseFormat);
            return serializer.toResponse(mapper.toResponse(domain), responseFormat);
        };
    }

}
