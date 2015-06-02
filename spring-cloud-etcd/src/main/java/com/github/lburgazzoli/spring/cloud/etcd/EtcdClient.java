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
package com.github.lburgazzoli.spring.cloud.etcd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Optional;

public class EtcdClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(EtcdClient.class);

    private final RestTemplate restTemplate;
    private final EtcdClientProperties properties;

    public EtcdClient(final EtcdClientProperties properties) {
        this.properties = properties;
        this.restTemplate = new RestTemplate();
        this.restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return false;
            }
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
            }
        });
    }

    public Optional<EtcdNode> get(String key) {
        final EtcdResponse response =
            this.restTemplate.getForObject(
                this.properties.getUrl() + Etcd.REST_PATH_KEYS, EtcdResponse.class, key);

        return response.getNode();
    }
}
