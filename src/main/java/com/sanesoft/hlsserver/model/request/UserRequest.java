package com.sanesoft.hlsserver.model.request;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@AllArgsConstructor
@Jacksonized
public class UserRequest {

    @NonNull
    String name;
}
