package br.com.infuse.adapter.serializer;

import br.com.infuse.adapter.inbound.route.response.ErrorResponse;
import br.com.infuse.util.datafaker.CustomFaker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ISerializerTest {

    private final CustomFaker faker = CustomFaker.getInstance();

    private ISerializer<ErrorResponse> serializer;

    @Mock
    private XmlMapper xmlMapper;
    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        serializer = new SerializerImpl<>(xmlMapper, objectMapper);
    }

    @Test
    void whenSendObjectAndJsonFormat_ThenInteractWithObjectMapper() throws JsonProcessingException {
        ErrorResponse response = faker.response().getErrorResponseBot().build();
        when(objectMapper.writeValueAsString(eq(response))).thenReturn(faker.lorem().word());

        serializer.toResponse(response, "json");

        verify(objectMapper).writeValueAsString(eq(response));
        verify(xmlMapper, times(0)).writeValueAsString(any(ErrorResponse.class));
    }

    @Test
    void whenSendObjectListAndJsonFormat_ThenInteractWithObjectMapper() throws JsonProcessingException {
        List<ErrorResponse> response = faker.response().getErrorResponseBot().build(faker.number().randomDigitNotZero());
        when(objectMapper.writeValueAsString(eq(response))).thenReturn(faker.lorem().word());

        serializer.toResponse(response, "json");

        verify(objectMapper).writeValueAsString(eq(response));
        verify(xmlMapper, times(0)).writeValueAsString(any(ErrorResponse.class));
    }

    @Test
    void whenSendObjectAndXMLFormat_ThenInteractWithObjectMapper() throws JsonProcessingException {
        List<ErrorResponse> response = faker.response().getErrorResponseBot().build(faker.number().randomDigitNotZero());
        when(xmlMapper.writeValueAsString(eq(response))).thenReturn(faker.lorem().word());

        serializer.toResponse(response, "xml");

        verify(xmlMapper).writeValueAsString(eq(response));
        verify(objectMapper, times(0)).writeValueAsString(any(ErrorResponse.class));
    }

}
