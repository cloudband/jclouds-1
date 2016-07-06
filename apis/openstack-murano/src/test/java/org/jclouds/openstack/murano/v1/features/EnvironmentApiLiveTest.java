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
package org.jclouds.openstack.murano.v1.features;

import com.google.common.collect.ImmutableList;
import org.jclouds.openstack.murano.v1.domain.Environment;
import org.jclouds.openstack.murano.v1.domain.MuranoPackage;
import org.jclouds.openstack.murano.v1.domain.Session;
import org.jclouds.openstack.murano.v1.internal.BaseMuranoApiLiveTest;
import org.jclouds.openstack.murano.v1.options.AddApplicationOptions;
import org.jclouds.openstack.murano.v1.options.CreatePackageOptions;
import org.jclouds.rest.ResourceAlreadyExistsException;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@Test(groups = "live", testName = "EnvironmentApiLiveTest")
public class EnvironmentApiLiveTest extends BaseMuranoApiLiveTest {

   public final String ENV_NAME = "test-env";
   public final String APP_NAME = "test-app";
   public final String DEPLOYING = "deploying";
   public final String PACKAGE_NO_TAGS = "/package_no_tags.zip";

   public void testCreateEnvironment() {
      for (String region : api.getConfiguredRegions()) {
         EnvironmentApi environmentApi = api.getEnvironmentApi(region);
         Environment environment = environmentApi.create(ENV_NAME);
         assertThat(environment).isNotNull();
         assertThat(environment.getName()).isEqualTo(ENV_NAME);
         ImmutableList<Environment> environments = environmentApi.list().toList();
         assertThat(environments).isNotNull();
         assertThat(environments.contains(environment));
         try {
            environmentApi.create(ENV_NAME);
            fail("environment creation should have failed");
         } catch (ResourceAlreadyExistsException e) {
            assertThat(e.getMessage().contains("already exist"));
         }
         environmentApi.delete(environment.getId(), false);
         waitForEnvironmentToDelete(environment.getId(), region);
      }
   }

   public void testListEnvironments() {
      for (String region : api.getConfiguredRegions()) {
         EnvironmentApi environmentApi = api.getEnvironmentApi(region);
         ImmutableList<Environment> environments = environmentApi.list().toList();
         assertThat(environments).isNotNull();
      }
   }

   public void testGetEnvironment() {
      for (String region : api.getConfiguredRegions()) {
         EnvironmentApi environmentApi = api.getEnvironmentApi(region);
         Environment createdEnvironment = environmentApi.create(ENV_NAME);
         Environment environment = environmentApi.get(createdEnvironment.getId());
         assertThat(environment).isEqualTo(createdEnvironment);
         environmentApi.delete(environment.getId());
         waitForEnvironmentToDelete(environment.getId(), region);
      }
   }

   public void testDeleteEnvironment() {
      for (String region : api.getConfiguredRegions()) {
         EnvironmentApi environmentApi = api.getEnvironmentApi(region);
         Environment environment = environmentApi.create(ENV_NAME);
         ImmutableList<Environment> environments = environmentApi.list().toList();
         assertThat(environments).isNotNull();
         int listSize = environments.size();
         environmentApi.delete(environment.getId());
         waitForEnvironmentToDelete(environment.getId(), region);
         assertThat(environmentApi.list().toList().size()).isEqualTo(listSize - 1);

      }
   }

   public void testConfigureSession() {
      for (String region : api.getConfiguredRegions()) {
         EnvironmentApi environmentApi = api.getEnvironmentApi(region);
         Environment environment = environmentApi.create(ENV_NAME);
         Session configure = environmentApi.configure(environment.getId());
         assertThat(configure).isNotNull();
         assertThat(configure.getEnvironmentId()).isEqualTo(environment.getId());
         environmentApi.deleteSession(environment.getId(), configure.getId());
         environmentApi.delete(environment.getId(), false);
         waitForEnvironmentToDelete(environment.getId(), region);
      }
   }


   public void testGetSession() {
      for (String region : api.getConfiguredRegions()) {
         EnvironmentApi environmentApi = api.getEnvironmentApi(region);
         Environment environment = environmentApi.create(ENV_NAME);
         Session configure = environmentApi.configure(environment.getId());
         Session session = environmentApi.getSession(environment.getId(), configure.getId());
         assertThat(session).isEqualTo(configure);
         environmentApi.deleteSession(environment.getId(), session.getId());
         environmentApi.delete(environment.getId(), false);
         waitForEnvironmentToDelete(environment.getId(), region);

      }
   }

   public void testAddApplicationDeployEnvironmentFromSessionAndListDeployments() throws IOException {
      for (String region : api.getConfiguredRegions()) {
         EnvironmentApi environmentApi = api.getEnvironmentApi(region);
         Environment createdEnvironment = environmentApi.create(ENV_NAME);
         MuranoPackageApi muranoPackageApi = api.getPackageApi(region);
         String path = getClass().getResource(PACKAGE_NO_TAGS).getFile();
         File file = new File(path);
         CreatePackageOptions createPackageOptions = CreatePackageOptions.Builder
               .enabled(true)
               .isPublic(true);
         MuranoPackage muranoPackage = muranoPackageApi.create(createPackageOptions, Files.readAllBytes(file.toPath()));
         AddApplicationOptions addApplicationOptions = AddApplicationOptions.Builder.muranoPackage(muranoPackage,APP_NAME,null, ENV_NAME);
         Session configure = environmentApi.configure(createdEnvironment.getId());

         Object addedApplication = environmentApi.addApplication(createdEnvironment.getId(), configure.getId(), addApplicationOptions);
         assertThat(addedApplication).isNotNull();

         environmentApi.deploy(createdEnvironment.getId(), configure.getId());

         Environment environment = environmentApi.get(createdEnvironment.getId());
         //wait until environment finishes deploying, environment can't be deleted while in "deploying" status.
         while (DEPLOYING.equals(environment.getStatus())) {
            environment = environmentApi.get(createdEnvironment.getId());
         }

         assertThat(environmentApi.listDeployments(createdEnvironment.getId()).size()).isEqualTo(1);

         environmentApi.delete(environment.getId());
         waitForEnvironmentToDelete(environment.getId(), region);
         muranoPackageApi.delete(muranoPackage.getId());
      }
   }

   public void testDeployEnvironmentWithoutPackagesFromSessionAndListDeployments() {
      for (String region : api.getConfiguredRegions()) {
         EnvironmentApi environmentApi = api.getEnvironmentApi(region);
         Environment createdEnvironment = environmentApi.create(ENV_NAME);
         Session configure = environmentApi.configure(createdEnvironment.getId());

         environmentApi.deploy(createdEnvironment.getId(), configure.getId());

         Environment environment = environmentApi.get(createdEnvironment.getId());
         //wait until environment finishes deploying, environment can't be deleted while in "deploying" status.
         while (DEPLOYING.equals(environment.getStatus())) {
            environment = environmentApi.get(createdEnvironment.getId());
         }

         assertThat(environmentApi.listDeployments(createdEnvironment.getId()).size()).isEqualTo(1);

         environmentApi.delete(environment.getId(), true);
         waitForEnvironmentToDelete(environment.getId(), region);
      }
   }

   private void waitForEnvironmentToDelete(String envId, String region) {
      EnvironmentApi environmentApi = api.getEnvironmentApi(region);
      Environment environment = environmentApi.get(envId);
      while (environment != null  && !(environment.getStatus().equals("delete failure"))) {
         environment = environmentApi.get(envId);
      }
   }


}
