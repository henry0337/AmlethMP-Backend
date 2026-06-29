package dev.sh1on.amlethmp;

import dev.myrlennia237.service.ReactiveHttpClient;
import dev.myrlennia237.service.ReactiveRedisService;
import dev.sh1on.amlethmp.song.repository.SongRepository;
import dev.sh1on.amlethmp.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class AmlethmpApplicationTests {

    @MockitoBean
    UserRepository userRepository;

    @MockitoBean
    SongRepository songRepository;

    @MockitoBean
    ReactiveRedisService reactiveRedisService;

    @MockitoBean
    ReactiveHttpClient reactiveHttpClient;

    @Test
    void contextLoads() {
    }

}
