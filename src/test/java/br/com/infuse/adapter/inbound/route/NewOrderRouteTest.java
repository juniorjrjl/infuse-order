package br.com.infuse.adapter.inbound.route;

import br.com.infuse.adapter.deserializer.Deserializer;
import br.com.infuse.adapter.inbound.route.request.OrderInsertRequest;
import br.com.infuse.adapter.inbound.route.response.OrderInsertedResponse;
import br.com.infuse.adapter.mapper.route.NewOrderMapper;
import br.com.infuse.adapter.serializer.ISerializer;
import br.com.infuse.adapter.validator.ValidatorRequest;
import br.com.infuse.core.domain.insert.OrderInsertDomain;
import br.com.infuse.core.domain.insert.OrderInsertedDomain;
import br.com.infuse.core.exception.ValidatorException;
import br.com.infuse.core.service.OrderService;
import br.com.infuse.util.ResponseStub;
import br.com.infuse.util.datafaker.CustomFaker;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spark.Request;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewOrderRouteTest {

    private final static CustomFaker faker = CustomFaker.getInstance();

    @Mock
    private Deserializer<OrderInsertRequest> deserializer;
    @Mock
    private ISerializer<OrderInsertedResponse> serializer;
    @Mock
    private ValidatorRequest validatorRequest;
    @Mock
    private NewOrderMapper mapper;
    @Mock
    private OrderService service;

    @Mock
    private Request request;

    private NewOrderRoute newOrderRoute;

    @BeforeEach
    void setup(){
        newOrderRoute = new NewOrderRoute(deserializer, serializer, validatorRequest, mapper, service);
    }

    @ParameterizedTest
    @ValueSource(strings = {"application/json", "application/xml"})
    void whenSendOrdersToInsert_ThenStoredThey(final String contentType) throws Exception {
        String jsonRequest = String.format("[{'request': '%s'}]", faker.lorem().word());
        String jsonResponse = String.format("[{'response': '%s'}]", faker.lorem().word());
        int ordersToGen = faker.number().randomDigitNotZero();
        List<OrderInsertRequest> objectBody = faker.request().getOrderInsertRequestBot().build(ordersToGen);
        List<OrderInsertDomain> domain = faker.domainApp().getOrderInsertDomainBot().build(ordersToGen);
        List<OrderInsertedDomain> resultDomain = faker.domainApp().getOrderInsertedDomainBot().build(ordersToGen);
        List<OrderInsertedResponse> responseBody = faker.response().getOrderInsertedResponseBot().build(ordersToGen);
        ResponseStub response = new ResponseStub();
        when(request.body()).thenReturn(jsonRequest);
        when(request.contentType()).thenReturn(contentType);
        when(deserializer.toListRequest(eq(jsonRequest), eq(contentType), eq(OrderInsertRequest.class))).thenReturn(objectBody);
        doNothing().when(validatorRequest).validate(eq(objectBody.toArray()));
        when(mapper.toDomain(eq(objectBody))).thenReturn(domain);
        when(service.save(eq(domain))).thenReturn(resultDomain);
        when(mapper.toResponse(eq(resultDomain))).thenReturn(responseBody);
        when(serializer.toResponse(eq(responseBody), eq(contentType))).thenReturn(jsonResponse);

        newOrderRoute.resolve().handle(request, response);

        assertThat(response.getStatusCode()).isEqualTo(201);
        assertThat(response.getHeaders().get("Content-Type")).isEqualTo(contentType);

        verify(request).body();
        verify(request, times(3)).contentType();
        verify(deserializer).toListRequest(eq(jsonRequest), eq(contentType), eq(OrderInsertRequest.class));
        verify(validatorRequest).validate(eq(objectBody.toArray()));
        verify(mapper).toDomain(eq(objectBody));
        verify(service).save(eq(domain));
        verify(mapper).toResponse(eq(resultDomain));
        verify(serializer).toResponse(eq(responseBody), eq(contentType));
    }

    @ParameterizedTest
    @ValueSource(strings = {"application/json", "application/xml"})
    void whenSerializationFail_thenThrowError(final String contentType) throws Exception {
        String jsonRequest = String.format("[{'request': '%s'}]", faker.lorem().word());
        String jsonResponse = String.format("[{'response': '%s'}]", faker.lorem().word());
        int ordersToGen = faker.number().randomDigitNotZero();
        List<OrderInsertRequest> objectBody = faker.request().getOrderInsertRequestBot().build(ordersToGen);
        List<OrderInsertDomain> domain = faker.domainApp().getOrderInsertDomainBot().build(ordersToGen);
        List<OrderInsertedDomain> resultDomain = faker.domainApp().getOrderInsertedDomainBot().build(ordersToGen);
        List<OrderInsertedResponse> responseBody = faker.response().getOrderInsertedResponseBot().build(ordersToGen);
        ResponseStub response = new ResponseStub();
        when(request.body()).thenReturn(jsonRequest);
        when(request.contentType()).thenReturn(contentType);
        when(deserializer.toListRequest(eq(jsonRequest), eq(contentType), eq(OrderInsertRequest.class))).thenReturn(objectBody);
        doNothing().when(validatorRequest).validate(eq(objectBody.toArray()));
        when(mapper.toDomain(eq(objectBody))).thenReturn(domain);
        when(service.save(eq(domain))).thenReturn(resultDomain);
        when(mapper.toResponse(eq(resultDomain))).thenReturn(responseBody);
        when(serializer.toResponse(eq(responseBody), eq(contentType))).thenThrow(new JsonProcessingException("erro"){});

        assertThatThrownBy(() -> newOrderRoute.resolve().handle(request, response))
                .isInstanceOf(JsonProcessingException.class);

        verify(request).body();
        verify(request, times(3)).contentType();
        verify(deserializer).toListRequest(eq(jsonRequest), eq(contentType), eq(OrderInsertRequest.class));
        verify(validatorRequest).validate(eq(objectBody.toArray()));
        verify(mapper).toDomain(eq(objectBody));
        verify(service).save(eq(domain));
        verify(mapper).toResponse(eq(resultDomain));
        verify(serializer).toResponse(eq(responseBody), eq(contentType));
    }

    @ParameterizedTest
    @ValueSource(strings = {"application/json", "application/xml"})
    void whenDeserializationFail_thenThrowError(final String contentType) throws Exception {
        String jsonRequest = String.format("[{'request': '%s'}]", faker.lorem().word());
        ResponseStub response = new ResponseStub();
        when(request.body()).thenReturn(jsonRequest);
        when(request.contentType()).thenReturn(contentType);
        when(deserializer.toListRequest(eq(jsonRequest), eq(contentType), eq(OrderInsertRequest.class))).thenThrow(new JsonProcessingException("erro"){});

        assertThatThrownBy(() -> newOrderRoute.resolve().handle(request, response))
                .isInstanceOf(JsonProcessingException.class);

        verify(request).body();
        verify(request, times(1)).contentType();
        verify(deserializer).toListRequest(eq(jsonRequest), eq(contentType), eq(OrderInsertRequest.class));
        verify(validatorRequest, times(0)).validate(any());
        verify(mapper, times(0)).toDomain(any(OrderInsertRequest.class));
        verify(service, times(0)).save(anyList());
        verify(mapper, times(0)).toResponse(anyList());
        verify(serializer, times(0)).toResponse(anyList(), anyString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"application/json", "application/xml"})
    void whenSaveOrderFail_ThenThrowError(final String contentType) throws Exception {
        String jsonRequest = String.format("[{'request': '%s'}]", faker.lorem().word());
        int ordersToGen = faker.number().randomDigitNotZero();
        List<OrderInsertRequest> objectBody = faker.request().getOrderInsertRequestBot().build(ordersToGen);
        List<OrderInsertDomain> domain = faker.domainApp().getOrderInsertDomainBot().build(ordersToGen);
        List<OrderInsertedDomain> resultDomain = faker.domainApp().getOrderInsertedDomainBot().build(ordersToGen);
        ResponseStub response = new ResponseStub();
        when(request.body()).thenReturn(jsonRequest);
        when(request.contentType()).thenReturn(contentType);
        when(deserializer.toListRequest(eq(jsonRequest), eq(contentType), eq(OrderInsertRequest.class))).thenReturn(objectBody);
        doNothing().when(validatorRequest).validate(eq(objectBody.toArray()));
        when(mapper.toDomain(eq(objectBody))).thenReturn(domain);
        when(service.save(eq(domain))).thenThrow(new ValidatorException("error", Collections.emptyList()));

        assertThatThrownBy(() -> newOrderRoute.resolve().handle(request, response))
                .isInstanceOf(ValidatorException.class);

        verify(request).body();
        verify(request).contentType();
        verify(deserializer).toListRequest(eq(jsonRequest), eq(contentType), eq(OrderInsertRequest.class));
        verify(validatorRequest).validate(eq(objectBody.toArray()));
        verify(mapper).toDomain(eq(objectBody));
        verify(service).save(eq(domain));
        verify(mapper, times(0)).toResponse(anyList());
        verify(serializer, times(0)).toResponse(anyList(), anyString());
    }


    @ParameterizedTest
    @ValueSource(strings = {"application/json", "application/xml"})
    void whenValidationFail_ThenThrowError(final String contentType) throws Exception {
        String jsonRequest = String.format("[{'request': '%s'}]", faker.lorem().word());
        int ordersToGen = faker.number().randomDigitNotZero();
        List<OrderInsertRequest> objectBody = faker.request().getOrderInsertRequestBot().build(ordersToGen);
        ResponseStub response = new ResponseStub();
        when(request.body()).thenReturn(jsonRequest);
        when(request.contentType()).thenReturn(contentType);
        when(deserializer.toListRequest(eq(jsonRequest), eq(contentType), eq(OrderInsertRequest.class))).thenReturn(objectBody);
        doThrow(new ValidatorException("error", Collections.emptyList())).when(validatorRequest).validate(eq(objectBody.toArray()));

        assertThatThrownBy(() -> newOrderRoute.resolve().handle(request, response))
                .isInstanceOf(ValidatorException.class);

        verify(request).body();
        verify(request).contentType();
        verify(deserializer).toListRequest(eq(jsonRequest), eq(contentType), eq(OrderInsertRequest.class));
        verify(validatorRequest).validate(eq(objectBody.toArray()));
        verify(mapper, times(0)).toDomain(anyList());
        verify(service, times(0)).save(anyList());
        verify(mapper, times(0)).toResponse(anyList());
        verify(serializer, times(0)).toResponse(anyList(), anyString());
    }

}
