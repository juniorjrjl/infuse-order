package br.com.infuse.adapter.deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.inject.Inject;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(onConstructor = @__({@Inject}))
public class DeserializerImpl<T> implements Deserializer<T> {

    private final XmlMapper xmlMapper;
    private final ObjectMapper objectMapper;

    @Override
    public T toRequest(final String body, final String format, final Class<T> clazz) throws JsonProcessingException {
        return format.toUpperCase().contains("JSON")?
                objectMapper.readValue(body, clazz):
                xmlMapper.readValue(body, clazz);
    }

    @Override
    public List<T> toListRequest(final String body, final String format, final Class<T> clazz) throws JsonProcessingException {
        CollectionType type = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
        return format.toUpperCase().contains("JSON")?
                objectMapper.readValue(body, type):
                xmlMapper.readValue(body, type);
    }

}
