package br.com.infuse.util.datafaker.bot.domain;

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
public class FindAllOrderDomainBot extends AbstractBot<FindAllOrderDomain> {

    private Supplier<Long> id = () -> faker.number().randomNumber();
    private Supplier<Long> controlId = () -> faker.number().randomNumber();
    private Supplier<BigDecimal> discount = () -> faker.monetary().random();
    private Supplier<BigDecimal> subTotal = () -> faker.monetary().random();
    private Supplier<BigDecimal> total = () -> faker.monetary().random();
    private Supplier<Long> clientId = () -> faker.number().randomNumber();
    private Supplier<OffsetDateTime> createdAt = () -> faker.offsetDateTime().birthday();
    private Supplier<Long> amount = () -> faker.number().randomNumber();

    public static FindAllOrderDomainBot builder(){
        return new FindAllOrderDomainBot();
    }

    @Override
    public FindAllOrderDomain build() {
        return FindAllOrderDomain.builder()
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
