package sejong.team.common.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MockUserApiConfig {
    @Bean
    public WireMockServer mockUserApi(){
        return new WireMockServer(0);
    }
}
