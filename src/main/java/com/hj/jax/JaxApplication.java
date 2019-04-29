package com.hj.jax;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@ComponentScan(basePackages = { "com.hj.jax.core.dal", "com.hj.jax.core.service","com.hj.jax.core.common","com.hj.jax.web"})
@MapperScan(basePackages = {"com.hj.jax.core.dal.dao"})
@EnableSwagger2Doc
@SpringBootApplication(exclude = {HibernateJpaAutoConfiguration.class },scanBasePackages = "com.hj.jax")

public class JaxApplication {
    private final static Logger logger = LoggerFactory.getLogger(JaxApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(JaxApplication.class, args);
        logger.info("--- Jax started ---> http://localhost:8077/api/to/login");
        logger.info("--- Swagger is online ---> http://localhost:8077/swagger-ui.html");
    }

}
