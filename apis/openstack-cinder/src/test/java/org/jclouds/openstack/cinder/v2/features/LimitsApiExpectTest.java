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

import org.jclouds.http.HttpResponse;
import org.jclouds.openstack.cinder.v2.domain.AbsoluteLimit;
import org.jclouds.openstack.cinder.v2.domain.Limits;
import org.jclouds.openstack.cinder.v2.internal.BaseCinderApiExpectTest;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;

import static org.testng.Assert.assertEquals;

@Test(groups = "unit", testName = "LimitsApiExpectTest")
public class LimitsApiExpectTest extends BaseCinderApiExpectTest {

    public void testGetLimits() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        URI endpoint = URI
                .create("http://172.16.0.1:8776/v1/50cdb4c60374463198695d9f798fa34d/limits");
        LimitsApi api = requestsSendResponses(
                keystoneAuthWithUsernameAndPasswordAndTenantName,
                responseWithKeystoneAccess,
                authenticatedGET().endpoint(endpoint).build(),
                HttpResponse
                        .builder()
                        .statusCode(200)
                        .payload(payloadFromResource("/v2/Limits.json"))
                        .build()).getLimitsApi("RegionOne");

        assertEquals(api.getLimits(), createLimit());
    }

    private Limits createLimit() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return Limits.builder().absoluteLimit(createAbsoluteLimit()).build();

    }

    private AbsoluteLimit createAbsoluteLimit() {
        return AbsoluteLimit.builder().totalSnapshotsUsed(0).maxTotalBackups(10).maxTotalVolumeGigabytes(4390.6658935546875).maxTotalSnapshots(50)
                .maxTotalBackupGigabytes(4390.6658935546875).totalBackupGigabytesUsed(0).
                        maxTotalVolumes(100).totalVolumesUsed(2).totalBackupsUsed(0).totalGigabytesUsed(100).build();

    }
}
