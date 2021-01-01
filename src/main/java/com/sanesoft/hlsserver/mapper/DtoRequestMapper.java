package com.sanesoft.hlsserver.mapper;

public interface DtoRequestMapper<Entity, Request> {

    Entity mapRequest(Request request);
}
