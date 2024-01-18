package br.com.infuse.adapter.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ISerializer<T> {

    String toResponse(final T response, final String format) throws JsonProcessingException;

    String toResponse(final List<T> response, final String format) throws JsonProcessingException;

}
