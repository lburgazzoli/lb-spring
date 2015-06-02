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
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.xml.bind.DatatypeConverter;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/*
 * Based on https://github.com/diwakergupta/jetcd
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EtcdNode {
    private final String key;
    private final Optional<String> value;
    private final Optional<Date> expiration;
    private final long createdIndex;
    private final long modifiedIndex;
    private final long ttl;
    private final boolean dir;
    private final List<EtcdNode> nodes;

    EtcdNode(
        @JsonProperty("key") final String key,
        @JsonProperty("value") final String value,
        @JsonProperty("expiration") final String expiration,
        @JsonProperty("createdIndex") final long createdIndex,
        @JsonProperty("modifiedIndex") final long modifiedIndex,
        @JsonProperty("ttl") final long ttl,
        @JsonProperty("dir") final boolean dir,
        @JsonProperty("nodes") final List<EtcdNode> nodes) {

        this.key = key;
        this.value = Optional.ofNullable(value);
        this.createdIndex = createdIndex;
        this.modifiedIndex = modifiedIndex;
        this.ttl = ttl;
        this.dir = dir;

        this.nodes = nodes != null
            ? Collections.unmodifiableList(nodes)
            : Collections.unmodifiableList(Collections.emptyList());
        this.expiration = expiration != null
            ?  Optional.of(DatatypeConverter.parseDateTime(expiration).getTime())
            : Optional.<Date>empty();

    }

    public String getKey() {
        return this.key;
    }

    public long getCreatedIndex() {
        return this.createdIndex;
    }

    public long getModifiedIndex() {
        return this.modifiedIndex;
    }

    public long getTtl() {
        return this.ttl;
    }

    public boolean isDir() {
        return this.dir;
    }

    public Optional<String> getValue() {
        return this.value;
    }

    public List<EtcdNode> getNodes() {
        return this.nodes;
    }

    public Optional<Date> getExpiration() {
        return this.expiration;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(
            this,
            ToStringStyle.MULTI_LINE_STYLE
        );
    }
}
