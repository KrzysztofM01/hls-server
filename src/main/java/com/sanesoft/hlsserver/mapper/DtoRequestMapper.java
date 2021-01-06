package com.sanesoft.hlsserver.mapper;

public interface DtoRequestMapper<T, V> {

    T mapFromRequest(V v);
}
