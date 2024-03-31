package org.example.core.common;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "org.example.core.domain")
@EntityScan(basePackages = "org.example.core.domain")
public class JpaConfig {

}
