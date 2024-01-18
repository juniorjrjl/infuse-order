package br.com.infuse.util.datafaker.bot.domain;

import br.com.infuse.core.domain.findall.FindAllOrderFilterDomain;
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
public class FindAllOrderFilterDomainBot extends AbstractBot<FindAllOrderFilterDomain> {

    private Supplier<Long> controlId = () -> faker.number().randomNumber();
    private Supplier<String> productName = () -> faker.name().name();
    private Supplier<BigDecimal> minValue = () -> faker.monetary().random();
    private Supplier<BigDecimal> maxValue = () -> faker.monetary().random();
    private Supplier<Long> minAmount = () -> faker.number().randomNumber();
    private Supplier<Long> maxAmount = () -> faker.number().randomNumber();
    private Supplier<Long> clientId = () -> faker.number().randomNumber();
    private Supplier<OffsetDateTime> minCreatedAt = () -> faker.offsetDateTime().birthday();
    private Supplier<OffsetDateTime> maxCreatedAt = () -> faker.offsetDateTime().birthday();

    public static FindAllOrderFilterDomainBot builder(){
        return new FindAllOrderFilterDomainBot();
    }

    @Override
    public FindAllOrderFilterDomain build() {
        return FindAllOrderFilterDomain.builder()
                .controlId(controlId.get())
                .productName(productName.get())
                .minValue(minValue.get())
                .maxValue(maxValue.get())
                .minAmount(minAmount.get())
                .maxAmount(maxAmount.get())
                .clientId(clientId.get())
                .minCreatedAt(minCreatedAt.get())
                .maxCreatedAt(maxCreatedAt.get())
                .build();
    }
}
