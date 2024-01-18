package br.com.infuse.util.datafaker.bot.response;

import br.com.infuse.adapter.inbound.route.response.FindAllOrderResponse;
import br.com.infuse.core.domain.findall.FindAllOrderDomain;
import br.com.infuse.util.datafaker.bot.AbstractBot;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.function.Supplier;

import static lombok.AccessLevel.PRIVATE;

@With
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PRIVATE)
public class FindAllOrderResponseBot extends AbstractBot<FindAllOrderResponse> {

    private Supplier<Long> id = () -> faker.number().randomNumber();
    private Supplier<Long> controlId = () -> faker.number().randomNumber();
    private Supplier<BigDecimal> discount = () -> faker.monetary().random();
    private Supplier<BigDecimal> subTotal = () -> faker.monetary().random();
    private Supplier<BigDecimal> total = () -> faker.monetary().random();
    private Supplier<Long> clientId = () -> faker.number().randomNumber();
    private Supplier<OffsetDateTime> createdAt = () -> faker.offsetDateTime().birthday();
    private Supplier<Long> amount = () -> faker.number().randomNumber();

    public static FindAllOrderResponseBot builder(){
        return new FindAllOrderResponseBot();
    }

    @Override
    public FindAllOrderResponse build() {
        return FindAllOrderResponse.builder()
                .id(id.get())
                .controlId(controlId.get())
                .discount(discount.get())
                .subTotal(subTotal.get())
                .total(total.get())
                .clientId(clientId.get())
                .createdAt(createdAt.get())
                .amount(amount.get())
                .build();
    }
}
