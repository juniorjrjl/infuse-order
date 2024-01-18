package br.com.infuse.adapter.inbound.route;

import br.com.infuse.adapter.inbound.route.response.FindAllOrderResponse;
import br.com.infuse.adapter.mapper.route.GetAllOrderMapper;
import br.com.infuse.adapter.serializer.ISerializer;
import br.com.infuse.core.domain.findall.FindAllOrderDomain;
import br.com.infuse.core.domain.findall.FindAllOrderFilterDomain;
import br.com.infuse.core.service.OrderQueryService;
import br.com.infuse.util.QueryParamsMapStub;
import br.com.infuse.util.ResponseStub;
import br.com.infuse.util.datafaker.CustomFaker;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spark.QueryParamsMap;
import spark.Request;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllOrderRouteTest {

    private final CustomFaker faker = CustomFaker.getInstance();

    @Mock
    private GetAllOrderMapper mapper;
    @Mock
    private ISerializer<FindAllOrderResponse> serializer;
    @Mock
    private OrderQueryService queryService;
    @Mock
    private Request request;

    private GetAllOrderRoute getAllOrderRoute;


    @BeforeEach
    void setup(){
        getAllOrderRoute = new GetAllOrderRoute(mapper, serializer, queryService);
    }

    @ParameterizedTest
    @ValueSource(strings = {"application/json", "application/xml"})
    void whenRequestOrders_ThenReturnIt(final String contentType) throws Exception {
        QueryParamsMap queryParamsMap = QueryParamsMapStub.getInstance();
        int amount = faker.number().randomDigitNotZero();
        List<FindAllOrderDomain> domain = faker.domainApp().getFindAllOrderDomainBot().build(amount);
        FindAllOrderFilterDomain filterDomain = faker.domainApp().getFindAllOrderFilterDomainBot().build();
        List<FindAllOrderResponse> body = faker.response().getFindAllOrderResponseBot().build(amount);
        ResponseStub response = new ResponseStub();
        when(request.queryMap()).thenReturn(queryParamsMap);
        when(mapper.toDomain(eq(queryParamsMap))).thenReturn(filterDomain);
        when(queryService.findAll(filterDomain)).thenReturn(domain);
        when(request.contentType()).thenReturn(contentType);
        when(mapper.toResponse(domain)).thenReturn(body);
        when(serializer.toResponse(eq(body), eq(contentType))).thenReturn("{'response': 'success'}");

        getAllOrderRoute.resolve().handle(request, response);

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getHeaders().get("Content-Type")).isEqualTo(contentType);

        verify(request).queryMap();
        verify(mapper).toDomain(eq(queryParamsMap));
        verify(queryService).findAll(filterDomain);
        verify(request, times(2)).contentType();
        verify(mapper).toResponse(domain);
        verify(serializer).toResponse(eq(body), eq(contentType));
    }

    @ParameterizedTest
    @ValueSource(strings = {"application/json", "application/xml"})
    void whenSerializationFail_ThenThrowError(final String contentType) throws Exception {
        QueryParamsMap queryParamsMap = QueryParamsMapStub.getInstance();
        int amount = faker.number().randomDigitNotZero();
        List<FindAllOrderDomain> domain = faker.domainApp().getFindAllOrderDomainBot().build(amount);
        FindAllOrderFilterDomain filterDomain = faker.domainApp().getFindAllOrderFilterDomainBot().build();
        List<FindAllOrderResponse> body = faker.response().getFindAllOrderResponseBot().build(amount);
        ResponseStub response = new ResponseStub();
        when(request.queryMap()).thenReturn(queryParamsMap);
        when(mapper.toDomain(eq(queryParamsMap))).thenReturn(filterDomain);
        when(queryService.findAll(filterDomain)).thenReturn(domain);
        when(request.contentType()).thenReturn(contentType);
        when(mapper.toResponse(domain)).thenReturn(body);
        when(serializer.toResponse(eq(body), eq(contentType))).thenThrow(new JsonProcessingException("error"){});

        assertThatThrownBy(() -> getAllOrderRoute.resolve().handle(request, response))
                .isInstanceOf(JsonProcessingException.class);

        verify(request).queryMap();
        verify(mapper).toDomain(eq(queryParamsMap));
        verify(queryService).findAll(filterDomain);
        verify(request, times(2)).contentType();
        verify(mapper).toResponse(domain);
        verify(serializer).toResponse(eq(body), eq(contentType));
    }

}
