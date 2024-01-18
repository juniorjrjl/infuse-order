package br.com.infuse.util.datafaker;

import br.com.infuse.util.datafaker.provider.DomainAppProvider;
import br.com.infuse.util.datafaker.provider.EntityProvider;
import br.com.infuse.util.datafaker.provider.MonetaryProvider;
import br.com.infuse.util.datafaker.provider.OffsetDateTimeProvider;
import br.com.infuse.util.datafaker.provider.RequestProvider;
import br.com.infuse.util.datafaker.provider.ResponseProvider;
import net.datafaker.Faker;

public class CustomFaker extends Faker {

    private static CustomFaker faker;

    public static CustomFaker getInstance(){
        if (faker == null)
            faker = new CustomFaker();

        return faker;
    }

    public EntityProvider entity(){
        return getProvider(EntityProvider.class, EntityProvider::new, this);
    }

    public DomainAppProvider domainApp(){
        return getProvider(DomainAppProvider.class, DomainAppProvider::new, this);
    }

    public ResponseProvider response(){
        return getProvider(ResponseProvider.class, ResponseProvider::new, this);
    }

    public RequestProvider request(){
        return getProvider(RequestProvider.class, RequestProvider::new, this);
    }

    public MonetaryProvider monetary(){
        return getProvider(MonetaryProvider.class, MonetaryProvider::new, this);
    }

    public OffsetDateTimeProvider offsetDateTime(){
        return getProvider(OffsetDateTimeProvider.class, OffsetDateTimeProvider::new, this);
    }

}
