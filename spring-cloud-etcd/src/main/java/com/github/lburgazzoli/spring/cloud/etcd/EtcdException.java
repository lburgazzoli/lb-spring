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

/*
 * Based on https://github.com/diwakergupta/jetcd
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class EtcdException extends Exception {
    private final int errorCode;
    private final String cause;
    private final String message;
    private final long index;

    public EtcdException(
            @JsonProperty("errorCode") final int errorCode,
            @JsonProperty("cause") final String cause,
            @JsonProperty("message") final String message,
            @JsonProperty("index") final long index) {

        super(message);

        this.errorCode = errorCode;
        this.cause = cause;
        this.message = message;
        this.index = index;

    }

    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return cause;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public long getIndex() {
        return index;
    }
}