package tech.beskar.commerce.order.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.ProjectingMessageConverter;

@Configuration
public class KafkaConfiguration {

    @Bean
    ProjectingMessageConverter projectingConverter(ObjectMapper mapper) {
        return new ProjectingMessageConverter(mapper);
    }
}
