package sejong.team.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import sejong.team.global.kafka.CommonJsonDeserializer;
import sejong.team.global.kafka.CommonJsonSerializer;
import sejong.team.service.req.ApplyTeamRequestDto;
import sejong.team.service.req.ConfirmApplicationRequestDto;

import java.util.Map;

import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;

@Configuration
@EnableKafka
public class KafkaConfig {
    @Value("${kafka.BOOTSTRAP_SERVERS_CONFIG}")
    private String BOOTSTRAP_SERVERS_CONFIG;

    @Value("${spring.kafka.consumer.group-id}")
    private String GROUP_ID_CONFIG;

    // <--
    @Bean
    public Map<String, Object> ConsumerConfigs() {
        return CommonJsonDeserializer.getStringObjectMap(BOOTSTRAP_SERVERS_CONFIG, GROUP_ID_CONFIG);
    }

    @Bean
    public ConsumerFactory<String, Void> deleteUserSquadConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(ConsumerConfigs());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Void> deleteUserSquadListener() {
        ConcurrentKafkaListenerContainerFactory<String, Void> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(deleteUserSquadConsumerFactory());
        return factory;
    }
    // <-- Consumer Config

    // <--
    @Bean
    public Map<String, Object> TeamProducerConfig() {
        return CommonJsonSerializer.getStringObjectMap(BOOTSTRAP_SERVERS_CONFIG);
    }

    @Bean
    public ProducerFactory<String, ApplyTeamRequestDto> applyTeamRequestDtoProducerFactory() {
        return new DefaultKafkaProducerFactory<>(TeamProducerConfig());
    }
    @Bean
    public ProducerFactory<String, ConfirmApplicationRequestDto> confirmApplicationRequestDtoProducerFactory() {
        return new DefaultKafkaProducerFactory<>(TeamProducerConfig());
    }

    @Bean
    public KafkaTemplate<String, ApplyTeamRequestDto> applyTeamkafkaTemplate() {
        return new KafkaTemplate<>(applyTeamRequestDtoProducerFactory());
    }
    @Bean
    public KafkaTemplate<String, ConfirmApplicationRequestDto> confirmApplicantkafkaTemplate() {
        return new KafkaTemplate<>(confirmApplicationRequestDtoProducerFactory());
    }
    // <-- Producer Config
}
