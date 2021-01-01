package com.sanesoft.hlsserver.mapper;

public interface DtoMapper<Entity, Request, Response>
        extends DtoRequestMapper<Entity, Request>, DtoResponseMapper<Entity, Response> {
}
