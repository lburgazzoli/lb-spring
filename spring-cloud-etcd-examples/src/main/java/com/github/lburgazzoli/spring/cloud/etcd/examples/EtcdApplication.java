/*
 * Copyright (c) 2015 Luca Burgazzoli
 *
 * https://github.com/lburgazzoli
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.lburgazzoli.spring.cloud.etcd.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@EnableAutoConfiguration
@SpringBootApplication
public class EtcdApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(EtcdApplication.class);

    public static void main(String[] args) {
        Environment env = SpringApplication.run(EtcdApplication.class, args).getEnvironment();
        LOGGER.info(">> {}", env.getProperty("com.github.lburgazzoli.etcd.key1"));
        LOGGER.info(">> {}", env.getProperty("com.github.lburgazzoli.etcd.key2"));
        LOGGER.info(">> {}", env.getProperty("com.github.lburgazzoli.etcd.subkey.key3"));
    }
}

