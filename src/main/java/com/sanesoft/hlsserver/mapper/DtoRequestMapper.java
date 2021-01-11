package com.sanesoft.hlsserver.mapper;

/**
 * Maps rest request {@link V} into database entity {@link T}.
 *
 * @param <T> - database entity type
 * @param <V> - type of rest request
 * @author kmirocha
 */
public interface DtoRequestMapper<T, V> {

    /**
     * Maps rest request into database entity.
     *
     * @param v - rest request
     * @return database entity.
     */
    T mapFromRequest(V v);
}
