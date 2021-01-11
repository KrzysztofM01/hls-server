package com.sanesoft.hlsserver.model.response;

import com.sanesoft.hlsserver.database.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * POJO representation of response containing info from {@link User}.
 *
 * @author kmirocha
 */
@Value
@Builder
@AllArgsConstructor
public class UserResponse {

    @NonNull
    String name;
    @NonNull
    @Builder.Default
    List<AudioFileInfoResponse> audioFileInfos = new ArrayList<>();
}
