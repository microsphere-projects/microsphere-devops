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
package io.microsphere.devops.service.nacos

import io.microsphere.devops.api.entity.Cluster
import io.microsphere.devops.service.application.ApplicationServiceFacade
import io.microsphere.nacos.client.NacosClientConfig
import io.microsphere.nacos.client.v2.NacosClientV2
import io.microsphere.nacos.client.v2.OpenApiNacosClientV2
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

/**
 * Nacos Service
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Service
 * @since 1.0.0
 */
@Service
class NacosService(
    val applicationServiceFacade: ApplicationServiceFacade
) {
    private val nacosClientsCache: ConcurrentMap<String, NacosClientV2> = ConcurrentHashMap();

    fun initNacosClients(clusters: List<Cluster>) {
        clusters.filter { cluster -> cluster.type == Cluster.Type.NACOS }
            .forEach { cluster ->
                {
                    var config = NacosClientConfig();
                    var client = OpenApiNacosClientV2(config);
                }
            }
    }
}