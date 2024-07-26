package com.example.mytv.adapter.out.jpa.subscribe;

import static org.assertj.core.api.BDDAssertions.then;

import com.example.mytv.adapter.out.jpa.channel.ChannelJpaEntity;
import com.example.mytv.adapter.out.jpa.user.UserJpaEntity;
import com.example.mytv.domain.channel.ChannelFixtures;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class SubscribeJpaRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SubscribeJpaRepository sut;

    @BeforeEach
    void setUp() {
        var user = entityManager.persist(new UserJpaEntity("userId", "name"));
        for (int i = 0; i < 3; i++) {
            var channel = entityManager.persist(ChannelJpaEntity.from(ChannelFixtures.stub("channel" + i)));
            entityManager.persist(new SubscribeJpaEntity(UUID.randomUUID().toString(), channel, user));
        }
    }

    @Test
    void testFindAllChannelByUserId() {
        var result = sut.findAllByUserId("userId");

        then(result)
            .hasSize(3)
            .extracting("user.id").containsOnly("userId");
    }
}