package br.com.infuse.adapter.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.inject.Inject;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor(onConstructor = @__({@Inject}))
public class SerializerImpl<T> implements ISerializer<T> {

    private final XmlMapper xmlMapper;
    private final ObjectMapper objectMapper;

    @Override
    public String toResponse(final T response, final String format) throws JsonProcessingException {
        return format.toUpperCase().contains("JSON")?
                objectMapper.writeValueAsString(response) :
                xmlMapper.writeValueAsString(response);
    }

    @Override
    public String toResponse(final List<T> response, final String format) throws JsonProcessingException {
        return format.toUpperCase().contains("JSON")?
                objectMapper.writeValueAsString(response) :
                xmlMapper.writeValueAsString(response);
    }

}
