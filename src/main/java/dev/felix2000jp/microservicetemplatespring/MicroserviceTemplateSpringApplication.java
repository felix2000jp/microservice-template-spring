package dev.felix2000jp.microservicetemplatespring;

import dev.felix2000jp.microservicetemplatespring.config.auth.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class MicroserviceTemplateSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceTemplateSpringApplication.class, args);
    }

}
