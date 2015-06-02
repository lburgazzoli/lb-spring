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
package com.github.lburgazzoli.spring.cloud.etcd.config;

import com.github.lburgazzoli.etcd.EtcdClient;
import com.github.lburgazzoli.spring.cloud.etcd.EtcdClientAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(EtcdClientAutoConfiguration.class)
@EnableConfigurationProperties
@ConditionalOnProperty(name = "spring.cloud.etcd.enabled", matchIfMissing = true)
public class EtcdConfigBootstrapConfiguration {

    @Autowired
    private EtcdClient etcd;

    @Bean
    public EtcdConfigProperties etcdConfigProperties() {
        return new EtcdConfigProperties();
    }

    @Bean
    public EtcdPropertySourceLocator consulPropertySourceLocator() {
        return new EtcdPropertySourceLocator(etcd, etcdConfigProperties());
    }
}
