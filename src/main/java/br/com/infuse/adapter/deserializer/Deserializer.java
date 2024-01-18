package br.com.infuse.adapter.deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface Deserializer<T> {

    T toRequest(final String body, final String format, final Class<T> clazz) throws JsonProcessingException;

    List<T> toListRequest(final String body, final String format, final Class<T> clazz) throws JsonProcessingException;

}
