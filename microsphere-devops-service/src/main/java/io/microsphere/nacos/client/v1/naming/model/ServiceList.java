/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.microsphere.nacos.client.v1.naming.model;

import io.microsphere.nacos.client.common.model.Model;
import io.microsphere.nacos.client.v1.naming.ServiceClient;

import java.util.List;

/**
 * The model of <a href="https://nacos.io/en/docs/v1/open-api/#2.15">Service List</a>
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ServiceClient
 * @since 1.0.0
 */
public class ServiceList implements Model {

    private int count;

    private List<String> doms;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<String> getDoms() {
        return doms;
    }

    public void setDoms(List<String> doms) {
        this.doms = doms;
    }
}
