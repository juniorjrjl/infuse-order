package br.com.infuse.core.service;

import br.com.infuse.adapter.decorator.OrderServiceDecorator;
import br.com.infuse.core.domain.insert.OrderInsertDomain;
import br.com.infuse.core.domain.insert.OrderInsertedDomain;
import br.com.infuse.core.exception.ControlIdInUseException;
import br.com.infuse.core.exception.ValidatorException;
import br.com.infuse.core.port.ClientPort;
import br.com.infuse.core.port.OrderPort;
import br.com.infuse.util.datafaker.CustomFaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    private final static CustomFaker faker = CustomFaker.getInstance();

    private OrderService orderService;

    @Mock
    private OrderPort orderPort;

    @Mock
    private ClientPort clientPort;

    @BeforeEach
    void setup(){
        orderService = new OrderServiceDecorator(orderPort, clientPort);
    }

    private static Stream<List<OrderInsertDomain>> whenListDomainIsEmptyOrGreaterThanEleven_ThenThrowError(){
        return Stream.of(
                Collections.emptyList(),
                faker.domainApp().getOrderInsertDomainBot().build(11)
        );
    }


    @ParameterizedTest
    @MethodSource
    void whenListDomainIsEmptyOrGreaterThanEleven_ThenThrowError(final List<OrderInsertDomain> domain){
        assertThatThrownBy(() -> orderService.save(domain))
                .isInstanceOf(ValidatorException.class);

        verify(orderPort, times(0)).hasControlId(anyLong());
        verify(clientPort, times(0)).hasClientId(anyLong());
        verify(orderPort, times(0)).save(anyList());
    }

    @Test
    void whenControlIdInUse_thenThrowError(){
        int toGenerate = faker.number().randomDigitNotZero();
        List<OrderInsertDomain> domain = faker.domainApp()
                .getOrderInsertDomainBot()
                .build(toGenerate);
        when(orderPort.hasControlId(anyLong())).thenReturn(true);

        assertThatThrownBy(() -> orderService.save(domain))
                .isInstanceOf(ControlIdInUseException.class);

        verify(orderPort).hasControlId(anyLong());
        verify(clientPort, times(0)).hasClientId(anyLong());
        verify(orderPort, times(0)).save(anyList());
    }

    @Test
    void whenClientNonStored_thenThrowError(){
        int toGenerate = faker.number().randomDigitNotZero();
        List<OrderInsertDomain> domain = faker.domainApp()
                .getOrderInsertDomainBot()
                .build(toGenerate);
        when(orderPort.hasControlId(anyLong())).thenReturn(false);
        when(clientPort.hasClientId(anyLong())).thenReturn(false);

        assertThatThrownBy(() -> orderService.save(domain))
                .isInstanceOf(ValidatorException.class);

        verify(orderPort).hasControlId(anyLong());
        verify(clientPort).hasClientId(anyLong());
        verify(orderPort, times(0)).save(anyList());
    }

    @Test
    void whenOrdersIsValid_thenSaveThey(){
        int toGenerate = faker.number().randomDigitNotZero();
        List<OrderInsertDomain> domain = faker.domainApp()
                .getOrderInsertDomainBot()
                .build(toGenerate);
        List<OrderInsertedDomain> resultDomain = faker.domainApp()
                .getOrderInsertedDomainBot()
                .build(toGenerate);
        when(orderPort.hasControlId(anyLong())).thenReturn(false);
        when(clientPort.hasClientId(anyLong())).thenReturn(true);
        when(orderPort.save(eq(domain))).thenReturn(resultDomain);

        orderService.save(domain);

        verify(orderPort, times(toGenerate)).hasControlId(anyLong());
        verify(clientPort, times(toGenerate)).hasClientId(anyLong());
        verify(orderPort).save(eq(domain));
    }

}
