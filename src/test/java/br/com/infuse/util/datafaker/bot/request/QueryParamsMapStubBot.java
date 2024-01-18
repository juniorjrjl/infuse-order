package br.com.infuse.util.datafaker.bot.request;

import br.com.infuse.util.QueryParamsMapStub;
import br.com.infuse.util.datafaker.bot.AbstractBot;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static lombok.AccessLevel.PRIVATE;

@With
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PRIVATE)
public class QueryParamsMapStubBot extends AbstractBot<QueryParamsMapStub> {

    private Supplier<Long> controlId = () -> faker.number().randomNumber();
    private Supplier<String> productName = () -> faker.name().name();
    private Supplier<BigDecimal> minValue = () -> faker.monetary().random();
    private Supplier<BigDecimal> maxValue = () -> faker.monetary().random();
    private Supplier<Long> minAmount = () -> faker.number().randomNumber();
    private Supplier<Long> maxAmount = () -> faker.number().randomNumber();
    private Supplier<Long> clientId = () -> faker.number().randomNumber();
    private Supplier<OffsetDateTime> minCreatedAt = () -> faker.offsetDateTime().birthday();
    private Supplier<OffsetDateTime> maxCreatedAt = () -> faker.offsetDateTime().birthday();

    public static QueryParamsMapStubBot builder(){
        return new QueryParamsMapStubBot();
    }


    @Override
    public QueryParamsMapStub build() {
        Map<String, String[]> params = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        params.put("controlId", Collections.singletonList(controlId.get() == null ? null : controlId.get().toString()).toArray(new String[0]));
        params.put("productName", Collections.singletonList(productName.get() == null ? null : productName.get()).toArray(new String[0]));
        params.put("minValue", Collections.singletonList(minValue.get() == null ? null : minValue.get().toString()).toArray(new String[0]));
        params.put("maxValue", Collections.singletonList(maxValue.get() == null ? null : maxValue.get().toString()).toArray(new String[0]));
        params.put("minAmount", Collections.singletonList(minAmount.get() == null ? null : minAmount.get().toString()).toArray(new String[0]));
        params.put("maxAmount", Collections.singletonList(maxAmount.get() == null ? null : maxAmount.get().toString()).toArray(new String[0]));
        params.put("clientId", Collections.singletonList(clientId.get() == null ? null : clientId.get().toString()).toArray(new String[0]));
        params.put("minCreatedAt", Collections.singletonList(minCreatedAt.get() == null ? null : formatter.format(minCreatedAt.get())).toArray(new String[0]));
        params.put("maxCreatedAt", Collections.singletonList(maxCreatedAt.get() == null ? null : formatter.format(maxCreatedAt.get())).toArray(new String[0]));
        return QueryParamsMapStub.getInstance(params);
    }
}
