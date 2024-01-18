package br.com.infuse.util.datafaker.provider;

import net.datafaker.providers.base.AbstractProvider;
import net.datafaker.providers.base.BaseProviders;

import java.math.BigDecimal;

public class MonetaryProvider extends AbstractProvider<BaseProviders> {

    public MonetaryProvider(final BaseProviders faker) {
        super(faker);
    }

    public BigDecimal random(){
        return between(2, 9_999_999_999_999L);
    }

    public BigDecimal between(final long min, final long max){
        return BigDecimal.valueOf(faker.number().randomDouble(2, min, max));
    }

}
