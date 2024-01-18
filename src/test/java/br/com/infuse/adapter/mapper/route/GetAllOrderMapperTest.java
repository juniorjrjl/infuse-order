package br.com.infuse.adapter.mapper.route;

import br.com.infuse.adapter.inbound.route.response.FindAllOrderResponse;
import br.com.infuse.core.domain.findall.FindAllOrderDomain;
import br.com.infuse.core.domain.findall.FindAllOrderFilterDomain;
import br.com.infuse.util.QueryParamsMapStub;
import br.com.infuse.util.datafaker.CustomFaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import spark.QueryParamsMap;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class GetAllOrderMapperTest {

    private static final CustomFaker faker =  CustomFaker.getInstance();

    private GetAllOrderMapper getAllOrderMapper;

    @BeforeEach
    void setup(){
        getAllOrderMapper = new GetAllOrderMapperImpl();
    }

    @Test
    void whenReceiveQueryParamsMap_thenReturnDomain(){
        QueryParamsMap queryParamsMap = faker.request().getQueryParamsMapStubBot().build();

        FindAllOrderFilterDomain domain = getAllOrderMapper.toDomain(queryParamsMap);
        assertThat(domain).hasNoNullFieldsOrProperties();
    }

    private static Stream<QueryParamsMap> whenReceiveEmptyQueryParamsMap_thenReturnEmptyDomain(){
        return Stream.of(
                QueryParamsMapStub.getInstance(new HashMap<>()),
                faker.request().getQueryParamsMapStubBot()
                        .withControlId(() -> null)
                        .withProductName(() -> null)
                        .withMinValue(() -> null)
                        .withMaxValue(() -> null)
                        .withMinAmount(() -> null)
                        .withMaxAmount(() -> null)
                        .withClientId(() -> null)
                        .withMinCreatedAt(() -> null)
                        .withMaxCreatedAt(() -> null)
                        .build()
        );
    }

    @ParameterizedTest
    @MethodSource
    void whenReceiveEmptyQueryParamsMap_thenReturnEmptyDomain(final QueryParamsMap queryParamsMap){
        FindAllOrderFilterDomain domain = getAllOrderMapper.toDomain(queryParamsMap);
        assertThat(domain).hasAllNullFieldsOrProperties();
    }

    @Test
    void whenReceiveGetAllDomain_thenReturnGetAllResponse(){
        List<FindAllOrderDomain> domains = faker.domainApp().getFindAllOrderDomainBot().build(1);

        List<FindAllOrderResponse> responses = getAllOrderMapper.toResponse(domains);

        assertThat(responses).hasSize(domains.size());
        FindAllOrderResponse response = responses.get(0);
        FindAllOrderDomain domain = domains.get(0);
        assertThat(response).usingRecursiveComparison()
                .ignoringFields("discount", "total", "subTotal")
                .isEqualTo(domain);
        assertThat(response.getTotal()).usingComparator(BigDecimal::compareTo)
                .isEqualTo(domain.getTotal());
        assertThat(response.getSubTotal()).usingComparator(BigDecimal::compareTo)
                .isEqualTo(domain.getSubTotal());
        assertThat(response.getDiscount()).usingComparator(BigDecimal::compareTo)
                .isEqualTo(domain.getDiscount());

    }

}
