package br.com.infuse.util.datafaker.bot;

import br.com.infuse.util.datafaker.CustomFaker;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractBot<T> {

    protected final CustomFaker faker = CustomFaker.getInstance();

    public abstract T build();

    public List<T> build(final int times){
        return Stream.generate(this::build).limit(times).collect(Collectors.toList());
    }

}
