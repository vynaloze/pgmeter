package com.vynaloze.pgmeter.dao;

import com.zaxxer.hikari.HikariDataSource;
import io.r2dbc.client.R2dbc;
import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DbConfig {
    @Value("${spring.jdbc.url}")
    private String jdbcUrl;
    @Value("${db.file.location}")
    private String fileLocation;
    @Value("${spring.r2dbc.username}")
    private String username;
    @Value("${spring.r2dbc.password}")
    private String password;

    @Bean
    public ConnectionFactory connectionFactory() {
        final var h2ConnectionConfiguration =
                H2ConnectionConfiguration.builder()
                        .file(fileLocation)
                        .username(username)
                        .password(password)
                        .build();
        return new H2ConnectionFactory(h2ConnectionConfiguration);
    }

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .url(jdbcUrl)
                .username(username)
                .password(password)
                .driverClassName("org.h2.Driver")
                .build();
    }

    @Bean
    public R2dbc client() {
        return new R2dbc(connectionFactory());
    }
}
