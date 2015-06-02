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
import com.github.lburgazzoli.spring.cloud.etcd.Etcd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EtcdPropertySourceLocator implements PropertySourceLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(EtcdPropertySourceLocator.class);

    private final EtcdClient etcd;
    private final EtcdConfigProperties properties;

    public EtcdPropertySourceLocator(EtcdClient etcd, EtcdConfigProperties properties) {
        this.etcd = etcd;
        this.properties = properties;
    }

    @Override
    public PropertySource<?> locate(Environment environment) {
        if (environment instanceof ConfigurableEnvironment) {
            final ConfigurableEnvironment env = (ConfigurableEnvironment) environment;
            final String[] profiles = env.getActiveProfiles();
            final List<String> contexts = new ArrayList<>();

            setupContext(
                contexts,
                profiles,
                this.properties.getPrefix(),
                this.properties.getDefaultContext());

            setupContext(
                contexts,
                profiles,
                this.properties.getPrefix(),
                env.getProperty(Etcd.PROPERTY_SPRING_APPLICATION_NAME));

            CompositePropertySource composite = new CompositePropertySource(Etcd.NAME);
            Collections.reverse(contexts);

            for (String context : contexts) {
                EtcdPropertySource propertySource = new EtcdPropertySource(context, etcd);
                propertySource.init();

                composite.addPropertySource(propertySource);
            }

            return composite;
        }

        return null;
    }

    private void setupContext(List<String> contexts, String[] profiles, String prefix, String item) {
        String ctx = prefix + Etcd.PATH_SEPARATOR + item;
        if(ctx.startsWith(Etcd.PATH_SEPARATOR)) {
            ctx = ctx.substring(1);
        }

        contexts.add(ctx);

        for (String profile : profiles) {
            contexts.add(ctx + this.properties.getProfileSeparator() + profile);
        }
    }
}
