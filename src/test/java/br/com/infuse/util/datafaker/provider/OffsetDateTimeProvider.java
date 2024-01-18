package br.com.infuse.util.datafaker.provider;

import net.datafaker.providers.base.AbstractProvider;
import net.datafaker.providers.base.BaseProviders;

import java.time.OffsetDateTime;

import static java.time.ZoneOffset.UTC;

public class OffsetDateTimeProvider extends AbstractProvider<BaseProviders> {
    public OffsetDateTimeProvider(final BaseProviders faker) {
        super(faker);
    }

    public OffsetDateTime birthday(){
        return faker.date().birthday().toLocalDateTime().atOffset(UTC);
    }

}
