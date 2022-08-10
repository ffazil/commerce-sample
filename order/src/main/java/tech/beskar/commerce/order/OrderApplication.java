package tech.beskar.commerce.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.hypermedia.DiscoveredResource;
import org.springframework.cloud.client.hypermedia.RemoteResource;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    @Bean
    RemoteResource productResource() {

        ServiceInstance service = new DefaultServiceInstance("stores", "stores", "localhost", 6060, false);

        return new DiscoveredResource(() -> service, traverson -> traverson.follow("product"));
    }
}
