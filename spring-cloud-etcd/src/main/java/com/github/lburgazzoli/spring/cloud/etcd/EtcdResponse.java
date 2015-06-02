/*
 * Copyright 2015 Luca Burgazzoli
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.lburgazzoli.spring.cloud.etcd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

/*
 * Based on https://github.com/diwakergupta/jetcd
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class EtcdResponse {
    private final String action;
    private final Optional<EtcdNode> node;
    private final Optional<EtcdNode> prevNode;

    EtcdResponse(
        @JsonProperty("action") final String action,
        @JsonProperty("node") final EtcdNode node,
        @JsonProperty("prevNode") final EtcdNode prevNode) {
        this.action = action;
        this.node = Optional.ofNullable(node);
        this.prevNode = Optional.ofNullable(prevNode);
    }

    public String getAction() {
        return this.action;
    }

    public Optional<EtcdNode> getNode() {
        return this.node;
    }

    public Optional<EtcdNode> getPrevNode() {
        return this.prevNode;
    }
}
