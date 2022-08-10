package tech.beskar.commerce.broker;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

@Configuration
@RequiredArgsConstructor
class MessagingConfiguration {

    @Bean
    EmbeddedKafkaBroker kafka() {

        EmbeddedKafkaBroker embedded = new EmbeddedKafkaBroker(1);
        embedded.kafkaPorts(9092);
        return embedded;
    }
}
