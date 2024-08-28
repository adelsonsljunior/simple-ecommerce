package com.adelsonsljunior.simpleecommerce.infra.configs;

import com.adelsonsljunior.simpleecommerce.core.domain.adapters.services.ProductServiceImp;
import com.adelsonsljunior.simpleecommerce.core.domain.adapters.services.UserServiceImp;
import com.adelsonsljunior.simpleecommerce.core.domain.ports.repositories.ProductRepositoryPort;
import com.adelsonsljunior.simpleecommerce.core.domain.ports.repositories.UserRepositoryPort;
import com.adelsonsljunior.simpleecommerce.core.domain.ports.services.ProductServicePort;
import com.adelsonsljunior.simpleecommerce.core.domain.ports.services.UserServicePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfig {

    @Bean
    ProductServicePort productService(ProductRepositoryPort productRepositoryPort) {
        return new ProductServiceImp(productRepositoryPort);
    }

    @Bean
    UserServicePort userService(UserRepositoryPort userRepositoryPort) {
        return new UserServiceImp(userRepositoryPort);
    }
}
