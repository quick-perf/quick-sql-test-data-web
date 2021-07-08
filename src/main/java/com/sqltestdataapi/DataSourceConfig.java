/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright ${project.inceptionYear}-2021 the original author or authors.
 */
package com.sqltestdataapi;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class DataSourceConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfig.class);

    @Bean
    public DataSource dataSource(DatabaseConfig databaseConfig) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException, MalformedURLException {

        try {
            configDriver(databaseConfig);

            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(databaseConfig.getDatasourceUrl());
            hikariConfig.setUsername(databaseConfig.getUser());
            hikariConfig.setPassword(databaseConfig.getPassword());
            hikariConfig.setDriverClassName(DriverShim.class.getCanonicalName());
            return new HikariDataSource(hikariConfig);
        } catch (Exception e) {
            LOGGER.error("Database configuration error");
            throw e;
        }
    }

    private void configDriver(DatabaseConfig databaseConfig) throws ClassNotFoundException, SQLException, MalformedURLException, IllegalAccessException, InstantiationException {
        String driverPath = databaseConfig.getDriverPath();
        String urlSpecification = "jar:file:/" + driverPath + "!/";
        URL url = new URL(urlSpecification);
        // dynamic load of the db driver
        String className = databaseConfig.getDriverClassName();
        URLClassLoader ucl = URLClassLoader.newInstance(new URL[]{url});
        Driver driver = (Driver) Class.forName(className, true, ucl).newInstance();
        DriverManager.registerDriver(new DriverShim(driver));
    }

}