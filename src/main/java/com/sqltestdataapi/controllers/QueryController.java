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

import com.sqltestdataapi.services.TestDataGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequestMapping("v0/api/")
@RestController("queryController")
public class QueryController {

    @Autowired
    private TestDataGeneratorService service;

    @RequestMapping(value = "/request", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRequest(@RequestParam(required = false) String query) {
        String[] formattedQuery = query.split("%20");
        String test = Stream.of(formattedQuery)
                .map(s -> s.split("%20"))
                .flatMap(Stream::of)
                .collect(Collectors.joining(" "));
        String fixture = this.service.generateTestQuery(test);

        return new ResponseEntity<>(fixture, HttpStatus.OK);
    }
}