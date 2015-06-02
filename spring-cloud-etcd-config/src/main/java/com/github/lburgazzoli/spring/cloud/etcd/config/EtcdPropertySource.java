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
import com.github.lburgazzoli.etcd.EtcdException;
import com.github.lburgazzoli.etcd.EtcdNode;
import com.github.lburgazzoli.spring.cloud.etcd.EtcdConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.EnumerablePropertySource;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EtcdPropertySource extends EnumerablePropertySource<EtcdClient> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EtcdPropertySource.class);

    private final Map<String, String> properties;
    private final String prefix;

    public EtcdPropertySource(String root, EtcdClient source) {
        super(root, source);
        this.properties = new HashMap<>();
        this.prefix = root.startsWith(EtcdConstants.PATH_SEPARATOR)
            ? root + EtcdConstants.PATH_SEPARATOR
            : EtcdConstants.PATH_SEPARATOR + root +  EtcdConstants.PATH_SEPARATOR;
    }

    public void init() {
        try {
            final Optional<EtcdNode> node = getSource().getNode(getName(), true);
            if (node.isPresent()) {
                process(node.get());
            }

        } catch(EtcdException e) {
            //LOGGER.warn("", e);
        }
    }

    @Override
    public String[] getPropertyNames() {
        return properties.keySet().toArray(new String[0]);
    }

    @Override
    public Object getProperty(String name) {
        return properties.get(name);
    }

    // *************************************************************************
    //
    // *************************************************************************

    private void process(final EtcdNode root) {
        //LOGGER.info("Process ==> {}", root);
        if(root.getNodes().isEmpty() && root.getValue().isPresent()) {
            final String key = root.getKey().substring(this.prefix.length());

            addProperty(
                    key.replace(EtcdConstants.PATH_SEPARATOR, EtcdConstants.PROPERTIES_SEPARATOR),
                    root.getValue().get()
            );
        }

        root.getNodes().forEach(this::process);
    }

    private void addProperty(String key, String val) {
        LOGGER.info("Add property: {} ==> {}", key, val);
        properties.put(key, val);
    }
}

