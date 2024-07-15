package com.example.mytv.adapter.in;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.mytv.adapter.in.api.constant.HeaderAttribute;
import com.example.mytv.adapter.out.jpa.user.UserJpaEntity;
import com.example.mytv.adapter.out.jpa.user.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class UserApiControllerIntTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @BeforeEach
    void setUp() {
        userJpaRepository.save(new UserJpaEntity("user1", "user name1"));
    }

    @Test
    void testGetUserWithHeader() throws Exception{
        mockMvc
            .perform(
                get("/api/v1/users")
                    .header(HeaderAttribute.X_AUTH_KEY, "user1")
            )
            .andDo(print())
            .andExpectAll(
                status().isOk(),
                jsonPath("$.id").value("user1"),
                jsonPath("$.name").value("user name1")
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
