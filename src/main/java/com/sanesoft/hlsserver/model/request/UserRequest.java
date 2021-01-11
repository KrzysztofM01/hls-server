package com.sanesoft.hlsserver.model.request;

import com.sanesoft.hlsserver.database.entity.User;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

/**
 * POJO representation of request to add new {@link User}.
 *
 * @author kmirocha
 */
@Value
@Builder
@AllArgsConstructor
@Jacksonized
public class UserRequest {

    @NonNull
    String name;
}
