package com.sanesoft.hlsserver.database.repository;

import com.sanesoft.hlsserver.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JpaRepository responsible for persisting and retrieving {@link User} data from database.
 *
 * @author kmirocha
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Searches for {@link User} with given name.
     *
     * @param name - name of the user
     * @return optional with possible {@link User} if it was found.
     */
    Optional<User> findByName(String name);

    /**
     * Deletes {@link User} with given name.
     *
     * @param name - name of the user
     * @return number of {@link User}s deleted from database.
     */
    long deleteByName(String name);
}
