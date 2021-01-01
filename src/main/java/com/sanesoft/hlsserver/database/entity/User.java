package com.sanesoft.hlsserver.database.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = User.TABLE_NAME)
@ToString(exclude = "audioFileInfos")
public class User {

    public static final String TABLE_NAME = "user";

    @Id
    @GeneratedValue
    Long id;
    @CreationTimestamp
    Instant createdOn;
    String name;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<AudioFileInfo> audioFileInfos;
}