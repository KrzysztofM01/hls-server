package com.sanesoft.hlsserver.mapper;

public interface DtoResponseMapper<T, R> {

    R mapToResponse(T entity);
}
