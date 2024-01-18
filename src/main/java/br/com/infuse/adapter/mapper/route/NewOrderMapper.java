package br.com.infuse.adapter.mapper.route;

import br.com.infuse.adapter.inbound.route.request.OrderInsertRequest;
import br.com.infuse.adapter.inbound.route.request.OrderItemInsertRequest;
import br.com.infuse.adapter.inbound.route.response.OrderInsertedResponse;
import br.com.infuse.core.domain.insert.OrderInsertDomain;
import br.com.infuse.core.domain.insert.OrderInsertedDomain;
import br.com.infuse.core.domain.insert.OrderItemInsertDomain;
import br.com.infuse.core.exception.InfuseException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;

import static java.math.RoundingMode.HALF_UP;

@Mapper
public abstract class NewOrderMapper {

    public abstract List<OrderInsertDomain> toDomain(final List<OrderInsertRequest> request);

    @Mapping(target = "discount", expression = "java(calculateDiscount(request.getItems()))")
    @Mapping(target = "subTotal", expression = "java(calculateSubTotal(request.getItems()))")
    @Mapping(target = "total", expression = "java(calculateTotal(request.getItems()))")
    public abstract OrderInsertDomain toDomain(final OrderInsertRequest request);

    @Mapping(target = "amount", expression = "java(request.getAmount() == null ? 1 : request.getAmount())")
    public abstract OrderItemInsertDomain toDomain(final OrderItemInsertRequest request);

    public abstract List<OrderInsertedResponse> toResponse(final List<OrderInsertedDomain> domain);

    protected BigDecimal calculateDiscount(final List<OrderItemInsertRequest> items){
        long itemAmount = items.stream()
                .map(i -> i.getAmount() == null ? 1L : i.getAmount())
                .reduce(Long::sum)
                .orElseThrow(() -> new InfuseException("Erro"));
        if (itemAmount >= 5L && itemAmount < 10) {
            return new BigDecimal(5);
        } else if (itemAmount >= 10L) {
            return new BigDecimal(10);
        }
        return BigDecimal.ZERO;
    }

    protected BigDecimal calculateSubTotal(final List<OrderItemInsertRequest> items){
        return items.stream()
                .map(i -> i.getValue().multiply(i.getAmount() != null?
                        new BigDecimal(i.getAmount()):
                        BigDecimal.ONE))
                .reduce(BigDecimal::add).orElseThrow(() -> new InfuseException("Erro"));
    }

    protected BigDecimal calculateTotal(final List<OrderItemInsertRequest> items){
        BigDecimal subTotal = calculateSubTotal(items);
        BigDecimal discount = calculateDiscount(items);
        BigDecimal percentValue = subTotal.multiply(discount).divide(new BigDecimal(100), 2, HALF_UP);
        return subTotal.subtract(percentValue);
    }

}
