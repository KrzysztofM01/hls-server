package com.sanesoft.hlsserver.mapper;

/**
 * Joins together {@link DtoResponseMapper} and {@link DtoRequestMapper} for convenience.
 *
 * @param <T> - database entity type
 * @param <V> - type of rest request
 * @param <R> - type of rest response
 * @author kmirocha
 */
public interface DtoMapper<T, V, R> extends DtoRequestMapper<T, V>, DtoResponseMapper<T, R> {
}
