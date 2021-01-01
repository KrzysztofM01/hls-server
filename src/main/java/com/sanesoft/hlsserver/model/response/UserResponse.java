package com.sanesoft.hlsserver.model.response;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@Builder
public class UserResponse {

    @NonNull
    String name;
    @NonNull
    @Builder.Default
    List<AudioFileInfoResponse> audioFileInfos = new ArrayList<>();
}
