package br.com.infuse.core.service;

import br.com.infuse.adapter.decorator.OrderQueryServiceDecorator;
import br.com.infuse.core.domain.findall.FindAllOrderDomain;
import br.com.infuse.core.domain.findall.FindAllOrderFilterDomain;
import br.com.infuse.core.port.OrderPort;
import br.com.infuse.util.datafaker.CustomFaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderQueryServiceTest {

    private final static CustomFaker faker = CustomFaker.getInstance();

    private OrderQueryService orderQueryService;

    @Mock
    private OrderPort orderPort;

    @BeforeEach
    void setup(){
        orderQueryService = new OrderQueryServiceDecorator(orderPort);
    }

    private static Stream<List<FindAllOrderDomain>> whenRequestOrders_ThenReturnIt(){
        return Stream.of(
                Collections.emptyList(),
                faker.domainApp().getFindAllOrderDomainBot().build(faker.number().randomDigitNotZero())
        );
    }

    @ParameterizedTest
    @MethodSource
    void whenRequestOrders_ThenReturnIt(final List<FindAllOrderDomain> resultDomain){
        FindAllOrderFilterDomain domain = faker.domainApp().getFindAllOrderFilterDomainBot().build();
        when(orderPort.findAll(eq(domain))).thenReturn(resultDomain);

        assertThatCode(() -> orderQueryService.findAll(domain)).doesNotThrowAnyException();

        verify(orderPort).findAll(eq(domain));
    }


}
