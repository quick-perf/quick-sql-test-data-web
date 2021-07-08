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
package com.sqltestdataapi.controllers;

import com.sqltestdataapi.WebSqlTestDataGeneratorApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {WebSqlTestDataGeneratorApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "demo")
public class QueryControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_receive_a_query() throws Exception {
        // mockMvc.perform(get("http://localhost:" + port + "/v0/api/request?query=INSERT%20INTO%20GuitarHero%20VALUES%20(1,%20%27Tosin%27,%20%27Abasi%27)"))
        mockMvc.perform(get("http://localhost:" + port + "/v0/api/request?query=SELECT%20*%20FROM%20GuitarHero"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("INSERT")));
    }

}