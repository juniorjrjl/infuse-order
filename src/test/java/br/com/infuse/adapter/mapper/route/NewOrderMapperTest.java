package br.com.infuse.adapter.mapper.route;

import br.com.infuse.adapter.inbound.route.request.OrderInsertRequest;
import br.com.infuse.adapter.inbound.route.request.OrderItemInsertRequest;
import br.com.infuse.adapter.inbound.route.response.OrderInsertedResponse;
import br.com.infuse.adapter.inbound.route.response.OrderItemInsertedResponse;
import br.com.infuse.core.domain.insert.OrderInsertDomain;
import br.com.infuse.core.domain.insert.OrderInsertedDomain;
import br.com.infuse.core.domain.insert.OrderItemInsertDomain;
import br.com.infuse.core.domain.insert.OrderItemInsertedDomain;
import br.com.infuse.util.datafaker.CustomFaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static java.math.RoundingMode.HALF_UP;
import static org.assertj.core.api.Assertions.assertThat;

class NewOrderMapperTest {

    private static final CustomFaker faker =  CustomFaker.getInstance();

    private NewOrderMapper newOrderMapper;

    @BeforeEach
    void setup(){
        newOrderMapper = new NewOrderMapperImpl();
    }

    @Test
    void whenSendInsertRequest_thenReturnInsertDomain(){
        List<OrderInsertRequest> requests = faker.request()
                .getOrderInsertRequestBot()
                .withItems(() -> faker.request().getOrderItemInsertRequestBot().build(1))
                .build(1);
        List<OrderInsertDomain> actual = newOrderMapper.toDomain(requests);

        assertThat(actual).hasSize(requests.size());
        OrderInsertRequest request = requests.get(0);
        OrderInsertDomain domain = actual.get(0);
        assertThat(domain).usingRecursiveComparison()
                .ignoringFields("subTotal", "total", "discount", "items")
                .isEqualTo(request);
        assertThat(domain.getItems()).hasSize(request.getItems().size());
        OrderItemInsertRequest requestItem = request.getItems().get(0);
        OrderItemInsertDomain domainItem = domain.getItems().get(0);
        assertThat(domainItem).usingRecursiveComparison()
                .ignoringFields("value")
                .isEqualTo(requestItem);
        assertThat(domainItem.getValue()).usingComparator(BigDecimal::compareTo).isEqualTo(requestItem.getValue());
    }


    @Test
    void whenHasFourOrLess_thenHasNoDiscount() throws Exception {
        int itemsToGen = faker.number().numberBetween(1, 5);
        List<OrderItemInsertRequest> itemsRequest = faker.request()
                .getOrderItemInsertRequestBot()
                .withValue(() -> faker.monetary().between(1, 100))
                .withAmount(() -> 1L)
                .build(itemsToGen);
        BigDecimal expectedTotal = itemsRequest.stream()
                .map(i -> i.getValue().multiply(new BigDecimal(i.getAmount())))
                .reduce(BigDecimal::add).orElseThrow(Exception::new);
        BigDecimal expectedDiscount = BigDecimal.ZERO;
        OrderInsertRequest request = faker.request().getOrderInsertRequestBot().withItems(() -> itemsRequest).build();

        OrderInsertDomain actual = newOrderMapper.toDomain(Collections.singletonList(request)).get(0);

        assertThat(actual.getDiscount()).isEqualTo(expectedDiscount);
        assertThat(actual.getTotal()).isEqualTo(expectedTotal);
        assertThat(actual.getSubTotal()).isEqualTo(expectedTotal);
    }

    @Test
    void whenHasBetweenFiveAndNine_thenHasAFivePercentDiscount() throws Exception {
        int itemsToGen = faker.number().numberBetween(5, 10);
        List<OrderItemInsertRequest> itemsRequest = faker.request()
                .getOrderItemInsertRequestBot()
                .withValue(() -> faker.monetary().between(1, 100))
                .withAmount(() -> 1L)
                .build(itemsToGen);
        BigDecimal expectedSubTotal = itemsRequest.stream()
                .map(i -> i.getValue().multiply(new BigDecimal(i.getAmount())))
                .reduce(BigDecimal::add).orElseThrow(Exception::new);
        BigDecimal expectedDiscount = new BigDecimal("5.00");
        BigDecimal expectedTotal = expectedSubTotal.multiply(new BigDecimal("95.00")).divide(new BigDecimal("100.00"), 2, HALF_UP);
        OrderInsertRequest request = faker.request().getOrderInsertRequestBot().withItems(() -> itemsRequest).build();

        OrderInsertDomain actual = newOrderMapper.toDomain(Collections.singletonList(request)).get(0);

        assertThat(actual.getDiscount().setScale(2, HALF_UP)).isEqualTo(expectedDiscount.setScale(2, HALF_UP));
        assertThat(actual.getSubTotal()).isEqualTo(expectedSubTotal);
        assertThat(actual.getTotal()).isEqualTo(expectedTotal);
    }

    @Test
    void whenHasMoreThanTen_thenHasATenPercentDiscount() throws Exception {
        int itemsToGen = faker.number().numberBetween(10, 20);
        List<OrderItemInsertRequest> itemsRequest = faker.request()
                .getOrderItemInsertRequestBot()
                .withValue(() -> faker.monetary().between(1, 100))
                .build(itemsToGen);
        BigDecimal expectedSubTotal = itemsRequest.stream()
                .map(i -> i.getValue().multiply(new BigDecimal(i.getAmount())))
                .reduce(BigDecimal::add).orElseThrow(Exception::new);
        BigDecimal expectedDiscount = new BigDecimal("10.00");
        BigDecimal expectedTotal = expectedSubTotal.multiply(new BigDecimal("90.00")).divide(new BigDecimal("100.00"), 2, HALF_UP);
        OrderInsertRequest request = faker.request().getOrderInsertRequestBot().withItems(() -> itemsRequest).build();

        OrderInsertDomain actual = newOrderMapper.toDomain(Collections.singletonList(request)).get(0);

        assertThat(actual.getDiscount().setScale(2, HALF_UP)).isEqualTo(expectedDiscount.setScale(2, HALF_UP));
        assertThat(actual.getSubTotal()).isEqualTo(expectedSubTotal);
        assertThat(actual.getTotal()).isEqualTo(expectedTotal);
    }

    @Test
    void whenItemIsWithoutAmount_thenInputOne(){
        int itemsToGen = faker.number().numberBetween(10, 20);
        List<OrderItemInsertRequest> itemsRequest = faker.request()
                .getOrderItemInsertRequestBot()
                .withAmount(() -> null)
                .build(itemsToGen);
        OrderInsertRequest request = faker.request().getOrderInsertRequestBot().withItems(() -> itemsRequest).build();

        List<OrderItemInsertDomain> actual = newOrderMapper.toDomain(Collections.singletonList(request)).get(0).getItems();

        actual.forEach(i -> assertThat(i.getAmount()).isOne());
    }

    @Test
    void whenReceiveInsertedDomain_thenReturnInsertedResponse(){
        List<OrderInsertedDomain> domain = faker.domainApp()
                .getOrderInsertedDomainBot()
                .withItems(() -> faker.domainApp().getOrderItemInsertedDomainBot().build(1))
                .build(1);

        List<OrderInsertedResponse> actual = newOrderMapper.toResponse(domain);

        assertThat(actual).hasSize(domain.size());
        OrderInsertedResponse orderResponse = actual.get(0);
        OrderInsertedDomain orderDomain = domain.get(0);
        assertThat(orderResponse).usingRecursiveComparison()
                .ignoringFields("total", "subTotal", "discount", "items")
                .isEqualTo(orderDomain);
        assertThat(orderResponse.getTotal()).usingComparator(BigDecimal::compareTo)
                .isEqualTo(orderDomain.getTotal());
        assertThat(orderResponse.getSubTotal()).usingComparator(BigDecimal::compareTo)
                .isEqualTo(orderDomain.getSubTotal());
        assertThat(orderResponse.getDiscount()).usingComparator(BigDecimal::compareTo)
                .isEqualTo(orderDomain.getDiscount());

        assertThat(orderResponse.getItems()).hasSize(orderDomain.getItems().size());
        OrderItemInsertedResponse itemResponse = orderResponse.getItems().get(0);
        OrderItemInsertedDomain itemDomain = orderDomain.getItems().get(0);
        assertThat(itemResponse).usingRecursiveComparison()
                .ignoringFields("value")
                .isEqualTo(itemDomain);
        assertThat(itemResponse.getValue()).usingComparator(BigDecimal::compareTo)
                .isEqualTo(itemDomain.getValue());

    }

}
