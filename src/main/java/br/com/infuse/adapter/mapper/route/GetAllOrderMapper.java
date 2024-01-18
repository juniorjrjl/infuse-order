package br.com.infuse.adapter.mapper.route;

import br.com.infuse.adapter.inbound.route.response.FindAllOrderResponse;
import br.com.infuse.core.domain.findall.FindAllOrderDomain;
import br.com.infuse.core.domain.findall.FindAllOrderFilterDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spark.QueryParamsMap;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Mapper
public abstract class GetAllOrderMapper {

    @Mapping(target = "controlId", expression = "java(toLong(queryParamsMap, \"controlId\"))")
    @Mapping(target = "productName", expression = "java(toString(queryParamsMap, \"productName\"))")
    @Mapping(target = "minValue", expression = "java(toBigDecimal(queryParamsMap, \"minValue\"))")
    @Mapping(target = "maxValue", expression = "java(toBigDecimal(queryParamsMap, \"maxValue\"))")
    @Mapping(target = "minAmount", expression = "java(toLong(queryParamsMap, \"minAmount\"))")
    @Mapping(target = "maxAmount", expression = "java(toLong(queryParamsMap, \"maxAmount\"))")
    @Mapping(target = "clientId", expression = "java(toLong(queryParamsMap, \"clientId\"))")
    @Mapping(target = "minCreatedAt", expression = "java(toOffsetDateTime(queryParamsMap, \"minCreatedAt\"))")
    @Mapping(target = "maxCreatedAt", expression = "java(toOffsetDateTime(queryParamsMap, \"maxCreatedAt\"))")
    public abstract FindAllOrderFilterDomain toDomain(final QueryParamsMap queryParamsMap);

    protected boolean fieldHasNoBlankValue(final QueryParamsMap queryParamsMap, final String field){
        return queryParamsMap.get(field).hasValue() &&
                queryParamsMap.get(field).value() != null &&
                !queryParamsMap.get(field).value().trim().isEmpty();
    }

    protected String toString(final QueryParamsMap queryParamsMap, final String field){
        return fieldHasNoBlankValue(queryParamsMap, field) ? queryParamsMap.get(field).value() : null;
    }

    protected BigDecimal toBigDecimal(final QueryParamsMap queryParamsMap, final String field){
        return fieldHasNoBlankValue(queryParamsMap, field) ? new BigDecimal(queryParamsMap.get(field).value()) : null;
    }

    protected Long toLong(final QueryParamsMap queryParamsMap, final String field){
        return fieldHasNoBlankValue(queryParamsMap, field) ?  queryParamsMap.get(field).longValue() : null;
    }

    protected OffsetDateTime toOffsetDateTime(final QueryParamsMap queryParamsMap, final String field){
        return fieldHasNoBlankValue(queryParamsMap, field) ? OffsetDateTime.parse(queryParamsMap.get(field).value()) : null;
    }

    public abstract List<FindAllOrderResponse> toResponse(final List<FindAllOrderDomain> domain);

}
