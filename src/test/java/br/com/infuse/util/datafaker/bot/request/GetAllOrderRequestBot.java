package br.com.infuse.util.datafaker.bot.request;

import br.com.infuse.core.domain.findall.FindAllOrderFilterDomain;
import br.com.infuse.util.datafaker.bot.AbstractBot;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.function.Supplier;

import static java.time.ZoneOffset.UTC;
import static lombok.AccessLevel.PRIVATE;

@With
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PRIVATE)
public class GetAllOrderRequestBot extends AbstractBot<FindAllOrderFilterDomain> {

    private Supplier<Long> controlId = () -> faker.number().numberBetween(0L,  Long.MAX_VALUE);
    private Supplier<String> productName = () -> faker.lorem().word();
    private Supplier<BigDecimal> minValue = () -> faker.monetary().random();
    private Supplier<BigDecimal> maxValue = () -> faker.monetary().random();
    private Supplier<Long> minAmount = () -> faker.number().numberBetween(1L, 10L);
    private Supplier<Long> maxAmount = () -> faker.number().numberBetween(10L, 100L);
    private Supplier<Long> clientId = () -> faker.number().numberBetween(0L,  Long.MAX_VALUE);
    private Supplier<OffsetDateTime> minCreatedAt = () -> faker.offsetDateTime().birthday();;
    private Supplier<OffsetDateTime> maxCreatedAt = () -> faker.offsetDateTime().birthday();;

    public static GetAllOrderRequestBot builder(){
        return new GetAllOrderRequestBot();
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
                .maxCreatedAt(maxCreatedAt.get())
                .maxCreatedAt(maxCreatedAt.get())
                .build();
    }
}
