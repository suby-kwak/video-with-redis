package com.example.mytv.adapter.in;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.mytv.adapter.in.api.constant.HeaderAttribute;
import com.example.mytv.adapter.out.jpa.user.UserJpaEntity;
import com.example.mytv.adapter.out.jpa.user.UserJpaRepository;
import com.example.mytv.util.RedisKeyGenerator;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class UserApiControllerIntTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private String authKey = UUID.randomUUID().toString();

    @BeforeEach
    void setUp() {
        userJpaRepository.save(new UserJpaEntity("userId", "user name"));
        stringRedisTemplate.opsForValue().set(RedisKeyGenerator.getUserSessionKey(authKey), "userId");
    }

    @Test
    void testGetUserWithHeader() throws Exception{
        mockMvc
            .perform(
                get("/api/v1/users")
                    .header(HeaderAttribute.X_AUTH_KEY, authKey)
            )
            .andDo(print())
            .andExpectAll(
                status().isOk(),
                jsonPath("$.id").value("userId"),
                jsonPath("$.name").value("user name")
            );
    }

    @Test
    void testGetUserWithoutHeader() throws Exception{
        mockMvc
            .perform(
                get("/api/v1/users")
            )
            .andDo(print())
            .andExpectAll(
                status().isOk(),
                jsonPath("$.id").doesNotExist()
            );
    }
}
