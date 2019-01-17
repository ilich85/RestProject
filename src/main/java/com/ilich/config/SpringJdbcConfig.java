package com.ilich.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;


@Configuration
@ComponentScan("com.ilich")
@EnableWebMvc
public class SpringJdbcConfig {

    @Bean
    public DataSource mysqlDataSource() throws IOException {
        URL resource = getClass().getClassLoader().getResource("db.properties");
        Properties property = new Properties();

        try (FileInputStream fis = new FileInputStream(resource.getFile())) {
            property.load(fis);
        }

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(property.getProperty("db.data.source.class"));
        dataSource.setUrl(property.getProperty("db.url"));
        dataSource.setUsername(property.getProperty("db.login"));
        dataSource.setPassword(property.getProperty("db.pass"));
        return dataSource;
    }
}
