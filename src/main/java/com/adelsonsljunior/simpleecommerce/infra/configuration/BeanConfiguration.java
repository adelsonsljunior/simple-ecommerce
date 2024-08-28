package com.adelsonsljunior.simpleecommerce.infra.configuration;

import com.adelsonsljunior.simpleecommerce.core.domain.adapters.services.ProductServiceImp;
import com.adelsonsljunior.simpleecommerce.core.domain.ports.repositories.ProductRepositoryPort;
import com.adelsonsljunior.simpleecommerce.core.domain.ports.services.ProductServicePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    ProductServicePort productService(ProductRepositoryPort productRepositoryPort) {
        return new ProductServiceImp(productRepositoryPort);
    }
}
