package com.sanesoft.hlsserver.database.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

/**
 * Entity which represents basic application user which can upload and download his audio files.
 *
 * @author kmirocha
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = User.TABLE_NAME)
@ToString(exclude = "audioFileInfos")
@EqualsAndHashCode(exclude = {"audioFileInfos", "createdOn", "id"})
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