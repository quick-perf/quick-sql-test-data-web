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

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Profile("demo")
@Component
public class DemoSqlDataSet implements CommandLineRunner {

    private final DataSource dataSource;

    public DemoSqlDataSet(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        executeSql("CREATE TABLE GuitarHero\n" +
                "(\n" +
                "    id         INT AUTO_INCREMENT PRIMARY KEY,\n" +
                "    first_name VARCHAR(250) NOT NULL,\n" +
                "    last_name  VARCHAR(250) NOT NULL\n" +
                ")");
        executeSql("INSERT INTO GUITARHERO VALUES (1, 'Tosin', 'Abasi')");
    }

    void executeSql(String sql) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to execute " + sql, e);
        }
    }

}
