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

import com.google.common.collect.ImmutableMap;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import org.jclouds.openstack.murano.v1.MuranoApi;
import org.jclouds.openstack.murano.v1.domain.Deployment;
import org.jclouds.openstack.murano.v1.domain.Environment;
import org.jclouds.openstack.murano.v1.domain.MuranoPackage;
import org.jclouds.openstack.murano.v1.domain.Session;
import org.jclouds.openstack.murano.v1.internal.BaseMuranoApiMockTest;
import org.jclouds.openstack.murano.v1.options.AddApplicationOptions;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Tests annotation parsing of {@code EnvironmentApi}
 */
@Test(groups = "unit", testName = "EnvironmentApiMockTest")
public class EnvironmentApiMockTest extends BaseMuranoApiMockTest {

   private static final String ENVIRONMENT_NAME = "test-env";
   private static final String TEST_ENVIRONMENT_ID = "e8d4028583f74317bba1f9caaffa53d4";
   private static final String TEST_SESSION_ID = "86accd9359e443fd881c7e91920a7529";
   private static final String TEST_PACKAGE_ID = "0b2ee98650c5457b8270ce0b4ec78eaf";

   public void testEnvironmentList() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(
            new MockResponse().setResponseCode(200).setBody(stringFromResource("/environment_list.json"))));

      try {
         MuranoApi muranoApi = api(server.getUrl("/").toString(), "openstack-murano", overrides);
         EnvironmentApi api = muranoApi.getEnvironmentApi("RegionOne");

         List<Environment> environments = api.list().toList();

         /*
          * Check request
          */
         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "GET", BASE_URI + "/v1/environments");

         /*
          * Check response
          */
         assertThat(environments).isNotEmpty();
         assertThat(environments.size()).isEqualTo(2);

      } finally {
         server.shutdown();
      }
   }

   public void testEnvironmentCreate() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(200).setBody(stringFromResource("/create_environment.json"))));

      try {
         MuranoApi muranoApi = api(server.getUrl("/").toString(), "openstack-murano", overrides);
         EnvironmentApi api = muranoApi.getEnvironmentApi("RegionOne");
         Environment environment = api.create(ENVIRONMENT_NAME);
             /*
              * Check request
              */
         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "POST", BASE_URI + "/v1/environments");

             /*
              * Check response
              */
         assertThat(environment).isNotNull();
         assertThat(environment.getId()).isEqualTo(TEST_ENVIRONMENT_ID);

      } finally {
         server.shutdown();
      }
   }

   public void testGetEnvironment() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(200).setBody(stringFromResource("/environment_get_response.json"))));

      try {
         MuranoApi muranoApi = api(server.getUrl("/").toString(), "openstack-murano", overrides);
         EnvironmentApi api = muranoApi.getEnvironmentApi("RegionOne");
         Environment environment = api.get(TEST_ENVIRONMENT_ID);
         assertThat(environment).isNotNull();

         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "GET", BASE_URI + "/v1/environments/" + TEST_ENVIRONMENT_ID);
         assertThat(environment).isNotNull();
      } finally {
         server.shutdown();
      }
   }

   public void testDeleteEnvironment() throws IOException, InterruptedException {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(200)));

      try {
         MuranoApi muranoApi = api(server.getUrl("/").toString(), "openstack-murano", overrides);
         EnvironmentApi api = muranoApi.getEnvironmentApi("RegionOne");

         boolean result = api.delete(TEST_ENVIRONMENT_ID, false);

         /*
          * Check request
          */
         assertEquals(server.getRequestCount(), 2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "DELETE", BASE_URI + "/v1/environments/" + TEST_ENVIRONMENT_ID + "?abandon=false");

         /*
          * Check response
          */
         assertTrue(result);
      } finally {
         server.shutdown();
      }
   }

   public void testSessionConfigure() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(200).setBody(stringFromResource("/configure_session.json"))));

      try {
         MuranoApi muranoApi = api(server.getUrl("/").toString(), "openstack-murano", overrides);
         EnvironmentApi api = muranoApi.getEnvironmentApi("RegionOne");
         Session session = api.configure(TEST_ENVIRONMENT_ID);
             /*
              * Check request
              */
         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "POST", BASE_URI + "/v1/environments/" + TEST_ENVIRONMENT_ID + "/configure");

             /*
              * Check response
              */
         assertThat(session).isNotNull();
         assertThat(session.getId()).isEqualTo(TEST_SESSION_ID);

      } finally {
         server.shutdown();
      }
   }

   public void testGetSession() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(200).setBody(stringFromResource("/session_get_response.json"))));

      try {
         MuranoApi muranoApi = api(server.getUrl("/").toString(), "openstack-murano", overrides);
         EnvironmentApi api = muranoApi.getEnvironmentApi("RegionOne");
         Session session = api.getSession(TEST_ENVIRONMENT_ID, TEST_SESSION_ID);
         assertThat(session).isNotNull();

         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "GET", BASE_URI + "/v1/environments/" + TEST_ENVIRONMENT_ID + "/sessions/" + TEST_SESSION_ID);
         assertThat(session).isNotNull();
      } finally {
         server.shutdown();
      }
   }

   public void testDeployEnvironment() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(200)));

      try {
         MuranoApi muranoApi = api(server.getUrl("/").toString(), "openstack-murano", overrides);
         EnvironmentApi api = muranoApi.getEnvironmentApi("RegionOne");

         api.deploy(TEST_ENVIRONMENT_ID, TEST_SESSION_ID);

         /*
          * Check request
          */
         assertEquals(server.getRequestCount(), 2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "POST", BASE_URI + "/v1/environments/" + TEST_ENVIRONMENT_ID + "/sessions/" + TEST_SESSION_ID + "/deploy");

         /*
          * Check response
          */
      } finally {
         server.shutdown();
      }

   }

   public void testDeploymentsList() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(200).setBody(stringFromResource("/environment_deployment_list.json"))));

      try {
         MuranoApi muranoApi = api(server.getUrl("/").toString(), "openstack-murano", overrides);
         EnvironmentApi api = muranoApi.getEnvironmentApi("RegionOne");

         List<Deployment> deployments = api.listDeployments(TEST_ENVIRONMENT_ID).toList();

         /*
          * Check request
          */
         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "GET", BASE_URI + "/v1/environments/" + TEST_ENVIRONMENT_ID + "/deployments");

         /*
          * Check response
          */
         assertThat(deployments).isNotEmpty();
         assertThat(deployments.size()).isEqualTo(2);

      } finally {
         server.shutdown();
      }
   }

   public void testAddApplicationByHotEnvironment() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(200).setBody(stringFromResource("/add_application.json"))));

      try {
         MuranoApi muranoApi = api(server.getUrl("/").toString(), "openstack-murano", overrides);
         EnvironmentApi api = muranoApi.getEnvironmentApi("RegionOne");

         MuranoPackage muranoPackage1 = new MuranoPackage(TEST_PACKAGE_ID, "PACKAGE_NAME", null, null, null, new Date(), true, null, new
               Date(), null, true, "io.murano.apps.generated.Vol3", "", "");

         AddApplicationOptions addApplicationOptions = AddApplicationOptions.Builder.hotEnvironment("ENV_NAME").name("APP_NAME")
               .properties(ImmutableMap.<String, Object>of("type", muranoPackage1.getFullyQualifiedName(), "id", muranoPackage1.getId()));
         Object addApplicationBlob = api.addApplication(TEST_ENVIRONMENT_ID, TEST_SESSION_ID, addApplicationOptions);


         // Check request

         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "POST", BASE_URI + "/v1/environments/" + TEST_ENVIRONMENT_ID + "/services");


         // Check response

         assertThat(addApplicationBlob).isNotNull();

      } finally {
         server.shutdown();
      }
   }

   public void testAddApplicationByName() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(200).setBody(stringFromResource("/add_application.json"))));

      try {
         MuranoApi muranoApi = api(server.getUrl("/").toString(), "openstack-murano", overrides);
         EnvironmentApi api = muranoApi.getEnvironmentApi("RegionOne");

         MuranoPackage muranoPackage1 = new MuranoPackage(TEST_PACKAGE_ID, "PACKAGE_NAME", null, null, null, new Date(), true, null, new
               Date(), null, true, "io.murano.apps.generated.Vol3", "", "");

         AddApplicationOptions addApplicationOptions = AddApplicationOptions.Builder.name("APP_NAME").hotEnvironment("ENV_NAME")
               .properties(ImmutableMap.<String, Object>of("type", muranoPackage1.getFullyQualifiedName(), "id", muranoPackage1.getId()));
         Object addApplicationBlob = api.addApplication(TEST_ENVIRONMENT_ID, TEST_SESSION_ID, addApplicationOptions);

         // Check request

         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "POST", BASE_URI + "/v1/environments/" + TEST_ENVIRONMENT_ID + "/services");


         // Check response

         assertThat(addApplicationBlob).isNotNull();

      } finally {
         server.shutdown();
      }
   }

   public void testAddApplicationByProperties() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(200).setBody(stringFromResource("/add_application.json"))));

      try {
         MuranoApi muranoApi = api(server.getUrl("/").toString(), "openstack-murano", overrides);
         EnvironmentApi api = muranoApi.getEnvironmentApi("RegionOne");

         MuranoPackage muranoPackage1 = new MuranoPackage(TEST_PACKAGE_ID, "PACKAGE_NAME", null, null, null, new Date(), true, null, new
               Date(), null, true, "io.murano.apps.generated.Vol3", "", "");

         AddApplicationOptions addApplicationOptions = AddApplicationOptions.Builder
               .properties(ImmutableMap.<String, Object>of("type", muranoPackage1.getFullyQualifiedName(), "id", muranoPackage1.getId()))
               .hotEnvironment("ENV_NAME").name("APP_NAME");
         Object addApplicationBlob = api.addApplication(TEST_ENVIRONMENT_ID, TEST_SESSION_ID, addApplicationOptions);

         // Check request

         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "POST", BASE_URI + "/v1/environments/" + TEST_ENVIRONMENT_ID + "/services");


         // Check response

         assertThat(addApplicationBlob).isNotNull();

      } finally {
         server.shutdown();
      }
   }

   public void testAddApplicationNoHotEnvironment() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(200).setBody(stringFromResource("/add_application_no_hot_environment.json"))));

      try {
         MuranoApi muranoApi = api(server.getUrl("/").toString(), "openstack-murano", overrides);
         EnvironmentApi api = muranoApi.getEnvironmentApi("RegionOne");

         MuranoPackage muranoPackage1 = new MuranoPackage(TEST_PACKAGE_ID, "PACKAGE_NAME", null, null, null, new Date(), true, null, new
               Date(), null, true, "io.murano.apps.generated.Vol3", "", "");

         Map<String,Object> templateParameters = new HashMap<String,Object>();
         templateParameters.put("flavor","SMALL_1");
         templateParameters.put("name","test_vm");

         AddApplicationOptions addApplicationOptions = AddApplicationOptions.Builder.muranoPackage(muranoPackage1, "APP_NAME",templateParameters);
         Object addApplicationBlob = api.addApplication(TEST_ENVIRONMENT_ID, TEST_SESSION_ID, addApplicationOptions);

         // Check request

         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "POST", BASE_URI + "/v1/environments/" + TEST_ENVIRONMENT_ID + "/services");


         // Check response

         assertThat(addApplicationBlob).isNotNull();

      } finally {
         server.shutdown();
      }
   }

   public void testApplicationsList() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(200).setBody(stringFromResource("/application_list.json"))));

      try {
         MuranoApi muranoApi = api(server.getUrl("/").toString(), "openstack-murano", overrides);
         EnvironmentApi api = muranoApi.getEnvironmentApi("RegionOne");

         List<Object> deployments = api.listEnvironmentApplications(TEST_ENVIRONMENT_ID, TEST_SESSION_ID).toList();

         /*
          * Check request
          */
         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "GET", BASE_URI + "/v1/environments/" + TEST_ENVIRONMENT_ID + "/services");

         /*
          * Check response
          */
         assertThat(deployments).isNotEmpty();
         assertThat(deployments.size()).isEqualTo(2);

      } finally {
         server.shutdown();
      }
   }
}


