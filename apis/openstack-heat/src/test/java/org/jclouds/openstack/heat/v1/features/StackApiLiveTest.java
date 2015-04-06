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
package org.jclouds.openstack.heat.v1.features;

import com.google.common.base.Predicate;
import com.google.common.base.Throwables;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import org.jclouds.openstack.heat.v1.domain.Stack;
import org.jclouds.openstack.heat.v1.domain.StackResource;
import org.jclouds.openstack.heat.v1.internal.BaseHeatApiLiveTest;
import org.jclouds.openstack.heat.v1.options.CreateStackOptions;
import org.jclouds.openstack.heat.v1.options.ListStackOptions;
import org.jclouds.openstack.heat.v1.options.UpdateOptions;
import org.jclouds.util.Strings2;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jclouds.util.Predicates2.retry;

/**
 * // * Tests parsing and Guice wiring of StackApi
 * //
 */
@Test(groups = "live", testName = "StackApiLiveTest")
public class StackApiLiveTest extends BaseHeatApiLiveTest {

   public static final String TEMPLATE_URL = "http://10.5.5.121/Installs/cPaaS/YAML/simple_stack.yaml";
   protected String stackName = System.getProperty("user.name").replace('.', '-').toLowerCase();

   public void testList() {
      for (String region : api.getConfiguredRegions()) {
         StackApi stackApi = api.getStackApi(region);

         List<Stack> stacks = stackApi.list().toList();

         assertThat(stacks).isNotNull();
      }
   }

   public void testListGlobalTenant() {
      for (String region : api.getConfiguredRegions()) {
         StackApi stackApi = api.getStackApi(region);
         ListStackOptions listStackOptions = ListStackOptions.builder().isGlobalTenant(true);
         List<Stack> stacks = stackApi.list(listStackOptions).toList();

         assertThat(stacks).isNotNull();
      }
   }


   public void testGetStackWitnNameAndID() {
      for (String region : api.getConfiguredRegions()) {
         StackApi stackApi = api.getStackApi(region);

         List<Stack> stacks = stackApi.list().toList();
         assertThat(stacks).isNotNull();

         Stack stack = stackApi.get(stacks.get(0).getName(), stacks.get(0).getId());
         assertThat(stack).isNotNull();
         assertThat(stack.getId()).isEqualTo(stacks.get(0).getId());
      }
   }

   public void testGetStack() {
      for (String region : api.getConfiguredRegions()) {
         StackApi stackApi = api.getStackApi(region);

         List<Stack> stacks = stackApi.list().toList();
         assertThat(stacks).isNotNull();

         Stack stack = stackApi.get(stacks.get(0).getId());
         assertThat(stack).isNotNull();
         assertThat(stack.getId()).isEqualTo(stacks.get(0).getId());
      }
   }

   public void testCreateWithTempletUrl() {
      for (String region : api.getConfiguredRegions()) {
         StackApi stackApi = api.getStackApi(region);
         CreateStackOptions createStackOptions = CreateStackOptions.Builder.templateUrl(TEMPLATE_URL);
         Stack stack = stackApi.create(getName(), createStackOptions);
         assertThat(stack).isNotNull();
         assertThat(stack.getId()).isNotEmpty();

      }
   }

   public void testDeleteStack() {
      for (String region : api.getConfiguredRegions()) {
         StackApi stackApi = api.getStackApi(region);
         CreateStackOptions createStackOptions = CreateStackOptions.Builder.templateUrl(TEMPLATE_URL);
         String stackName = getName();
         Stack stack = stackApi.create(stackName, createStackOptions);
         assertThat(stack).isNotNull();
         assertThat(stack.getId()).isNotEmpty();
         assertThat(stackApi.delete(stackName, stack.getId())).isTrue();
         Stack stackAfterDelete = stackApi.get(stackName, stack.getId());
         assertThat(stackAfterDelete).isNotNull();
      }
   }

   public void testCreateWithDisableRollback() {
      for (String region : api.getConfiguredRegions()) {
         StackApi stackApi = api.getStackApi(region);
         CreateStackOptions createStackOptions = CreateStackOptions.Builder.templateUrl(TEMPLATE_URL).disableRollback(false);
         Stack stack = stackApi.create(getName(), createStackOptions);
         assertThat(stack).isNotNull();
         assertThat(stack.getId()).isNotEmpty();
         assertThat(stack.isRollbackDisabled()).isFalse();

      }
   }

   public void testCreateWithTemplate() throws ParseException {
      for (String region : api.getConfiguredRegions()) {
         StackApi stackApi = api.getStackApi(region);
         JSONParser parser = new JSONParser();
         Object obj = parser.parse(stringFromResource("/simple_stack.json"));

         JSONObject jsonObject = (JSONObject) obj;

         CreateStackOptions createStackOptions = CreateStackOptions.Builder.template(String.valueOf(jsonObject.get("template")));
         Stack stack = stackApi.create(getName(), createStackOptions);
         assertThat(stack).isNotNull();
         assertThat(stack.getId()).isNotEmpty();
      }
   }

   public void testUpdateStack() throws ParseException {
      for (String region : api.getConfiguredRegions()) {
         final StackApi stackApi = api.getStackApi(region);
         JSONParser parser = new JSONParser();
         Object obj = parser.parse(stringFromResource("/simple_stack.json"));

         JSONObject jsonObject = (JSONObject) obj;

         CreateStackOptions createStackOptions = CreateStackOptions.Builder.template(String.valueOf(jsonObject.get("template")));
         String stackName = getName();
         Stack stack = stackApi.create(stackName, createStackOptions);
         assertThat(stack).isNotNull();
         String stackId = stack.getId();
         assertThat(stackId).isNotEmpty();

         boolean success = retry(new Predicate<String>() {
            public boolean apply(String stackId ) {
               return stackApi.get(stackId).getStatus() == Stack.Status.CREATE_COMPLETE;
            }

         }, 60, 1, SECONDS).apply(stackId);

         if (!success) {
            Assert.fail("Stack didn't get to status CREATE_COMPLETE in 20m.");
         }

         UpdateOptions updateOptions = UpdateOptions.Builder.templateUrl(TEMPLATE_URL);
         assertThat(stackApi.update(stackName, stack.getId(), updateOptions)).isTrue();
         Stack stackAfterUpdate = stackApi.get(stackName, stack.getId());
         assertThat(stackAfterUpdate.getStatus()).isEqualTo(Stack.Status.UPDATE_IN_PROGRESS);

      }
   }

   public void testCreateWithParameters() throws ParseException {
      for (String region : api.getConfiguredRegions()) {
         StackApi stackApi = api.getStackApi(region);
         Map<String, Object> parameters = new HashMap<String, Object>();
         parameters.put("key_name", "myKey");
         parameters.put("image_id", "3b7be1fa-d381-4067-bb81-e835df630564");
         parameters.put("instance_type", "SMALL_1");

         JSONParser parser = new JSONParser();
         Object obj = parser.parse(stringFromResource("/stack_with_parameters.json"));

         JSONObject jsonObject = (JSONObject) obj;

         CreateStackOptions createStackOptions = CreateStackOptions.Builder.template(String.valueOf(jsonObject.get("template")))
               .parameters(parameters);
         Stack stack = stackApi.create(getName(), createStackOptions);
         assertThat(stack).isNotNull();
         assertThat(stack.getId()).isNotEmpty();

         Stack stackFromList = stackApi.get(stack.getId());
         assertThat(stackFromList).isNotNull();
         assertThat(stackFromList.getId()).isEqualTo(stack.getId());
         for (String parmName : parameters.keySet()) {
            assertThat(stackFromList.getParameters().containsKey(parmName)).isTrue();
         }

      }
   }

   public void testCreateWithFilesAndEnvironment() throws IOException, ParseException {
      for (String region : api.getConfiguredRegions()) {
         StackApi stackApi = api.getStackApi(region);
         JSONParser parser = new JSONParser();
         Map<String, String> files = new HashMap<String, String>();
         Object obj = parser.parse(stringFromResource("/stack_with_environment_and_files.json"));
         files.put("LCP-VolumeB.template.yaml", "{\"resources\":{\"ALU-LCP-Block-StorageB\":{\"properties\":{\"description\":\"Used for VM \\/storage partition.\",\"name\":{\"str_replace\":{\"template\":\"$stk-s01c$cardh0\",\"params\":{\"$card\":{\"get_param\":\"cardvB\"},\"$stk\":{\"get_param\":\"deployment_prefix\"}}}},\"size\":{\"get_param\":\"storage_size\"}},\"type\":\"OS::Cinder::Volume\"}},\"heat_template_version\":\"2013-05-23\",\"description\":\"Template to set up volumes for the initial deployment\",\"parameters\":{\"cardvB\":{\"default\":\"00\",\"description\":\"The (2 digit) virtual card number for server attached to volume when on shelf 0\",\"constraints\":[{\"length\":{\"min\":\"2\",\"max\":\"2\"}},{\"allowed_pattern\":\"^[0-9][0-9]$\"}],\"type\":\"string\"},\"deployment_prefix\":{\"description\":\"Name prefix for resources. It should match the system prefix\\nused in the FI worksheet but this is not enforced.\",\"constraints\":[{\"length\":{\"min\":\"5\",\"max\":\"14\"}},{\"allowed_pattern\":\"^[a-zA-Z0-9][a-zA-Z0-9_-]*$\"}],\"type\":\"string\"},\"storage_size\":{\"default\":\"4\",\"description\":\"GBs for block storage\",\"constraints\":[{\"range\":{\"min\":\"4\"}}],\"type\":\"number\"},\"group_index\":{\"default\":[],\"description\":\"List of strings each pointing to one of the other parameters that is steped up for each additional \\nresource of the same type added to the stack (e.g. at growth event)\",\"type\":\"comma_delimited_list\"}},\"outputs\":{\"volumeId\":{\"description\":\"VolumeId\",\"value\":{\"get_resource\":\"ALU-LCP-Block-StorageB\"}}}}");
         JSONObject jsonObject = (JSONObject) obj;

         CreateStackOptions createStackOptions = CreateStackOptions.Builder
               .template(String.valueOf(jsonObject.get("template")))
               .environment(String.valueOf(jsonObject.get("environment")))
               .files(files);

         Stack stack = stackApi.create(getName(), createStackOptions);
         assertThat(stack).isNotNull();
         assertThat(stack.getId()).isNotEmpty();
      }
   }

   public void testListAndGetStackResources() throws ParseException {
      for (String region : api.getConfiguredRegions()) {
         StackApi stackApi = api.getStackApi(region);
         JSONParser parser = new JSONParser();
         Object obj = parser.parse(stringFromResource("/stack_with_parameters.json"));

         JSONObject jsonObject = (JSONObject) obj;
         Map<String, Object> parameters = new HashMap<String, Object>();
         parameters.put("key_name", "myKey");
         parameters.put("image_id", "3b7be1fa-d381-4067-bb81-e835df630564");
         parameters.put("instance_type", "SMALL_1");

         CreateStackOptions createStackOptions = CreateStackOptions.Builder.template(String.valueOf(jsonObject.get("template"))).parameters(parameters);
         String stackName = getName();
         Stack stack = stackApi.create(stackName, createStackOptions);
         assertThat(stack).isNotNull();
         assertThat(stack.getId()).isNotEmpty();
         FluentIterable<StackResource> resources = stackApi.listStackResources(stackName, stack.getId());
         assertThat(resources).isNotNull();
         assertThat(resources).isNotEmpty();
         for (StackResource stackResource : resources) {
            assertThat(stackResource).isNotNull();
            String stackResourceName = stackResource.getName();
            assertThat(stackResourceName).isNotNull();
            assertThat(stackResourceName).isNotEmpty();
            assertThat(stackResource.getStatus()).isNotEqualTo(StackResource.Status.UNRECOGNIZED);
            StackResource resourceFromGet = stackApi.getStackResource(stackName, stack.getId(), stackResourceName);
            assertThat(resourceFromGet).isNotNull();
            assertThat(resourceFromGet.getName()).isEqualTo(stackResourceName);
         }
      }
   }

   public void testGetStackResourceMetadata() throws ParseException {
      for (String region : api.getConfiguredRegions()) {
         StackApi stackApi = api.getStackApi(region);
         JSONParser parser = new JSONParser();
         Object obj = parser.parse(stringFromResource("/simple_stack.json"));

         JSONObject jsonObject = (JSONObject) obj;

         CreateStackOptions createStackOptions = CreateStackOptions.Builder.template(String.valueOf(jsonObject.get("template")));
         String stackName = getName();
         Stack stack = stackApi.create(stackName, createStackOptions);
         assertThat(stack).isNotNull();
         assertThat(stack.getId()).isNotEmpty();
         FluentIterable<StackResource> resources = stackApi.listStackResources(stackName, stack.getId());
         assertThat(resources).isNotNull();
         assertThat(resources).isNotEmpty();
         for (StackResource stackResource : resources) {
            assertThat(stackResource).isNotNull();
            String stackResourceName = stackResource.getName();
            assertThat(stackResourceName).isNotNull();
            assertThat(stackResourceName).isNotEmpty();
            assertThat(stackResource.getStatus()).isNotEqualTo(StackResource.Status.UNRECOGNIZED);
            Map metadata = stackApi.getStackResourceMetadata(stackName, stack.getId(), stackResourceName);
            assertThat(metadata).isNotNull();
            assertThat(metadata).isNotEmpty();
         }
      }
   }


   private String getName() {
      return stackName + "_" + System.currentTimeMillis();
   }

   public String stringFromResource(String resourceName) {
      try {
         return Strings2.toStringAndClose(getClass().getResourceAsStream(resourceName));
      } catch (IOException e) {
         throw Throwables.propagate(e);
      }
   }

}