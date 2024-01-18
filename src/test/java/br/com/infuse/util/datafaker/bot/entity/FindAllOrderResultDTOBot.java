package br.com.infuse.util.datafaker.bot.entity;

import br.com.infuse.adapter.outbound.persistence.dto.FindAllOrderResultDTO;
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
public class FindAllOrderResultDTOBot extends AbstractBot<FindAllOrderResultDTO> {

    private Supplier<Long> id = () -> faker.number().randomNumber();
    private Supplier<Long> controlId = () -> faker.number().randomNumber();
    private Supplier<BigDecimal> discount = () -> faker.monetary().random();
    private Supplier<BigDecimal> subTotal = () -> faker.monetary().random();
    private Supplier<BigDecimal> total = () -> faker.monetary().random();
    private Supplier<Long> clientId = () -> faker.number().randomNumber();
    private Supplier<OffsetDateTime> externalCreatedAt = () -> faker.offsetDateTime().birthday();
    private Supplier<Long> amount = () -> faker.number().randomNumber();

    public static FindAllOrderResultDTOBot builder(){
        return new FindAllOrderResultDTOBot();
    }

    @Override
    public FindAllOrderResultDTO build() {
        return new FindAllOrderResultDTO(id.get(), controlId.get(), discount.get(), subTotal.get(),
                total.get(), clientId.get(), externalCreatedAt.get(), amount.get());
    }
}
