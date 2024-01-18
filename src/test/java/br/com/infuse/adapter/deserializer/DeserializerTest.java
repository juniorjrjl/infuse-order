package br.com.infuse.adapter.deserializer;

import br.com.infuse.adapter.inbound.route.response.ErrorResponse;
import br.com.infuse.util.datafaker.CustomFaker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeserializerTest {

    private final CustomFaker faker = CustomFaker.getInstance();

    private Deserializer<ErrorResponse> deserializer;

    @Mock
    private XmlMapper xmlMapper;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private TypeFactory typeFactory;

    @BeforeEach
    void setup(){
        deserializer = new DeserializerImpl<>(xmlMapper, objectMapper);
    }

    @Test
    void whenSendJsonFormatAndClass_ThenInteractWithObjectMapper() throws JsonProcessingException {
        String json = faker.lorem().word();
        ErrorResponse response = faker.response().getErrorResponseBot().build();
        when(objectMapper.readValue(eq(json), eq(ErrorResponse.class))).thenReturn(response);

        deserializer.toRequest(json, "json", ErrorResponse.class);

        verify(objectMapper).readValue(eq(json), eq(ErrorResponse.class));
        verify(xmlMapper, times(0)).readValue(anyString(), eq(ErrorResponse.class));
    }

    @Test
    void whenSendJsonListFormatAndClass_ThenInteractWithObjectMapper() throws JsonProcessingException {
        String json = faker.lorem().word();
        List<ErrorResponse> response = faker.response().getErrorResponseBot().build(faker.number().randomDigitNotZero());
        when(objectMapper.readValue(eq(json), any(CollectionType.class))).thenReturn(response);
        when(typeFactory.constructCollectionType(eq(ArrayList.class), eq(ErrorResponse.class)))
                .thenReturn(CollectionType.construct(ArrayList.class, SimpleType.constructUnsafe(ErrorResponse.class)));
        when(objectMapper.getTypeFactory()).thenReturn(typeFactory);

        deserializer.toListRequest(json, "json", ErrorResponse.class);

        verify(objectMapper).readValue(eq(json), any(CollectionType.class));
        verify(xmlMapper, times(0)).readValue(anyString(), eq(ErrorResponse.class));
    }

    @Test
    void whenSendXMLFormatAndClass_ThenInteractWithObjectMapper() throws JsonProcessingException {
        String xml = faker.lorem().word();
        ErrorResponse response = faker.response().getErrorResponseBot().build();
        when(xmlMapper.readValue(eq(xml), eq(ErrorResponse.class))).thenReturn(response);

        deserializer.toRequest(xml, "xml", ErrorResponse.class);

        verify(xmlMapper).readValue(eq(xml), eq(ErrorResponse.class));
        verify(objectMapper, times(0)).readValue(anyString(), eq(ErrorResponse.class));
    }

    @Test
    void whenSendXMLListFormatAndClass_ThenInteractWithObjectMapper() throws JsonProcessingException {
        String xml = faker.lorem().word();
        List<ErrorResponse> response = faker.response().getErrorResponseBot().build(faker.number().randomDigitNotZero());
        when(xmlMapper.readValue(eq(xml), any(CollectionType.class))).thenReturn(response);
        when(typeFactory.constructCollectionType(eq(ArrayList.class), eq(ErrorResponse.class)))
                .thenReturn(CollectionType.construct(ArrayList.class, SimpleType.constructUnsafe(ErrorResponse.class)));
        when(objectMapper.getTypeFactory()).thenReturn(typeFactory);

        deserializer.toListRequest(xml, "xml", ErrorResponse.class);

        verify(xmlMapper).readValue(eq(xml), any(CollectionType.class));
        verify(objectMapper, times(0)).readValue(anyString(), eq(ErrorResponse.class));
    }

}
