package com.sanesoft.hlsserver.database.repository;

import com.sanesoft.hlsserver.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);

    long deleteByName(String name);
}
