package com.sanesoft.hlsserver.mapper;

/**
 * Maps database entity {@link T} into rest response {@link R}.
 *
 * @param <T> - database entity type
 * @param <R> - type of rest response
 * @author kmirocha
 */
public interface DtoResponseMapper<T, R> {

    /**
     * Maps database entity into rest request.
     *
     * @param entity - database entity
     * @return rest response.
     */
    R mapToResponse(T entity);
}
