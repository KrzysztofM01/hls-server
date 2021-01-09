package com.sanesoft.hlsserver.database.repository;

import com.sanesoft.hlsserver.database.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static com.sanesoft.hlsserver.database.DatabaseTestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class UserRepositoryIT {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByName_returnsUser() {
        // given
        User user = persistTestUser(entityManager);

        // when
        Optional<User> result = userRepository.findByName(user.getName());

        // then
        assert result.isPresent();
        assertEquals(user, result.get());
    }

    @Test
    void deleteByName_returnsNumberOfDeletedUsers() {
        // given
        String name = "test";
        User user = persistTestUser(name, entityManager);

        // when
        Optional<User> optional = userRepository.findByName(user.getName());

        // then
        assert optional.isPresent();
        assertEquals(optional.get(), user);

        // when
        long result = userRepository.deleteByName(name);
        assertEquals(1, result);
        assert userRepository.findById(user.getId()).isEmpty();
    }
}