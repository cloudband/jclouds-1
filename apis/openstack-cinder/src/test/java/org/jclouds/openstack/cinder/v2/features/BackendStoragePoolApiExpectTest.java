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
package org.jclouds.openstack.cinder.v2.features;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.jclouds.http.HttpResponse;
import org.jclouds.openstack.cinder.v2.domain.BackendStoragePool;
import org.jclouds.openstack.cinder.v2.domain.PoolCapability;
import org.jclouds.openstack.cinder.v2.internal.BaseCinderApiExpectTest;
import org.testng.annotations.Test;

import java.net.URI;
import java.util.ArrayList;

import static org.testng.Assert.assertEquals;

@Test(groups = "unit", testName = "BackendStoragePoolApiExpectTest")
public class BackendStoragePoolApiExpectTest extends BaseCinderApiExpectTest {

    public void testGetList() throws Exception {
        URI endpoint = URI
                .create("http://172.16.0.1:8776/v1/50cdb4c60374463198695d9f798fa34d/scheduler-stats/get_pools?detail=true");
        BackendStoragePoolsApi api = requestsSendResponses(
                keystoneAuthWithUsernameAndPasswordAndTenantName,
                responseWithKeystoneAccess,
                authenticatedGET().endpoint(endpoint).build(),
                HttpResponse
                        .builder()
                        .statusCode(200)
                        .payload(
                                payloadFromResource("/v2/BackendStoragePool.json"))
                        .build()).getBackendStoragePoolsApi("RegionOne");

        assertEquals(api.list(true), getTestBlockStoragePool());
    }

    public void testGetListWithoutDetails(){
        URI endpoint = URI
                .create("http://172.16.0.1:8776/v1/50cdb4c60374463198695d9f798fa34d/scheduler-stats/get_pools?detail=false");
        BackendStoragePoolsApi api = requestsSendResponses(
                keystoneAuthWithUsernameAndPasswordAndTenantName,
                responseWithKeystoneAccess,
                authenticatedGET().endpoint(endpoint).build(),
                HttpResponse
                        .builder()
                        .statusCode(200)
                        .payload(
                                payloadFromResource("/v2/BackendStoragePool_withoutDetail.json"))
                        .build()).getBackendStoragePoolsApi("RegionOne");

        assertEquals(api.list(false), createBackendPoolWithoutDetails());
    }

    private FluentIterable<? extends BackendStoragePool> getTestBlockStoragePool() {
        ArrayList<BackendStoragePool> backendStoragePoolList = Lists.newArrayList(createBackendStoragePool(), createBackendStoragePool2());
        return FluentIterable.from(backendStoragePoolList);
    }

    private BackendStoragePool createBackendStoragePool() {
        PoolCapability poolCapability = PoolCapability.builder()
                .timestamp("2016-05-24T18:27:15.881131")
                .volumeBackendName("adv_zone0").freeCapacityGB(2980)
                .driverVersion("1.1.0").totalCapacityGB(3054)
                .reservedPercentage(0).vendorName("Open Source")
                .storageProtocol("ceph").build();
        return new BackendStoragePool("os-glance-01@adv_zone0#adv_zone0",
                poolCapability);
    }


    private BackendStoragePool createBackendStoragePool2() {
        PoolCapability poolCapability = PoolCapability.builder()
                .timestamp("2016-05-24T18:27:29.579027")
                .volumeBackendName("default-rbd").freeCapacityGB(4390.6658935546875)
                .driverVersion("1.1.0").totalCapacityGB(4390.6658935546875)
                .reservedPercentage(0).vendorName("Open Source")
                .storageProtocol("ceph").build();
        return new BackendStoragePool("os-glance-00@default-rbd#default-rbd",
                poolCapability);
    }

    private FluentIterable<BackendStoragePool> createBackendPoolWithoutDetails(){
        ArrayList<BackendStoragePool> backendStoragePoolList = Lists.newArrayList(
               new BackendStoragePool("os-glance-01@adv_zone0#adv_zone0",null),
               new BackendStoragePool("os-glance-00@default-rbd#default-rbd",null),
               new BackendStoragePool("os-glance-01@default-rbd#default-rbd",null),
               new BackendStoragePool("os-glance-00@adv_zone0#adv_zone0",null)
        );
        return FluentIterable.from(backendStoragePoolList);
    }
}
