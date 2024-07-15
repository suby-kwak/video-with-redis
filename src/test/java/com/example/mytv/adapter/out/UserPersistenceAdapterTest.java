package com.example.mytv.adapter.out;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.example.mytv.adapter.out.jpa.user.UserJpaEntity;
import com.example.mytv.adapter.out.jpa.user.UserJpaRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserPersistenceAdapterTest {
    private UserPersistenceAdapter sut;

    private final UserJpaRepository userJpaRepository = mock(UserJpaRepository.class);

    @BeforeEach
    void setUp() {
        sut = new UserPersistenceAdapter(userJpaRepository);
    }

    @Test
    void loadUser() {
        // Given
        var userJpaEntity = new UserJpaEntity("userId", "name");
        given(userJpaRepository.findById(any())).willReturn(Optional.of(userJpaEntity));

        // When
        var result = sut.loadUser("userId");

        // Then
        then(result)
            .isPresent()
            .hasValueSatisfying(user -> {
                then(user.getId()).isEqualTo("userId");
                then(user.getName()).isEqualTo("name");
            });
    }
}
