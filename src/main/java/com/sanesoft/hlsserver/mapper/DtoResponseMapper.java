package com.sanesoft.hlsserver.mapper;

public interface DtoResponseMapper<Entity, Response> {
    Response mapResponse(Entity entity);
}
