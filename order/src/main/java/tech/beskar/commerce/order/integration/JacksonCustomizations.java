package tech.beskar.commerce.order.integration;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.beskar.commerce.order.ProductInfo;

@Configuration
public class JacksonCustomizations {

    @Bean
    SimpleModule ordersModule() {

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.setMixInAnnotation(ProductInfo.ProductNumber.class, ProductNumberMixin.class);

        return simpleModule;
    }

    @JsonSerialize(using = ToStringSerializer.class)
    interface ProductNumberMixin {
    }
}
