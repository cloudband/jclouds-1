/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.openstack.heat.v1.features;

import com.google.common.collect.FluentIterable;
import org.jclouds.Fallbacks.EmptyFluentIterableOnNotFoundOr404;
import org.jclouds.Fallbacks.FalseOnNotFoundOr404;
import org.jclouds.Fallbacks.NullOnNotFoundOr404;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.openstack.heat.v1.domain.Stack;
import org.jclouds.openstack.heat.v1.domain.StackResource;
import org.jclouds.openstack.heat.v1.options.CreateStackOptions;
import org.jclouds.openstack.heat.v1.options.ListStackOptions;
import org.jclouds.openstack.heat.v1.options.UpdateOptions;
import org.jclouds.openstack.keystone.v2_0.filters.AuthenticateRequest;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.MapBinder;
import org.jclouds.rest.annotations.PayloadParam;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.SelectJson;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Map;


/**
 * Provides access to the OpenStack Orchestration (Heat) Stack API features.
 */
@RequestFilters(AuthenticateRequest.class)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/stacks")
public interface StackApi {

   @Named("stack:list")
   @GET
   @SelectJson("stacks")
   @Fallback(EmptyFluentIterableOnNotFoundOr404.class)
   FluentIterable<Stack> list();

   @Named("stack:list")
   @GET
   @SelectJson("stacks")
   @Fallback(EmptyFluentIterableOnNotFoundOr404.class)
   FluentIterable<Stack> list(ListStackOptions options);

   @Named("stack:get")
   @GET
   @SelectJson("stack")
   @Path("/{stack_name}/{stack_id}")
   @Fallback(NullOnNotFoundOr404.class)
   @Nullable
   Stack get(@PathParam("stack_name") String name, @PathParam("stack_id") String id);

   @Named("stack:get")
   @GET
   @SelectJson("stack")
   @Path("/{id}")
   @Fallback(NullOnNotFoundOr404.class)
   Stack get(@PathParam("id") String stackId);

   @Named("stack:create")
   @POST
   @SelectJson("stack")
   @MapBinder(CreateStackOptions.class)
   Stack create(@PayloadParam("stack_name") String name, CreateStackOptions... options);

   @Named("stack:delete")
   @DELETE
   @Path("/{stack_name}/{stack_id}")
   @Fallback(FalseOnNotFoundOr404.class)
   @Nullable
   boolean delete(@PathParam("stack_name") String name, @PathParam("stack_id") String id);

   @Named("stack:update")
   @PUT
   @Path("/{stack_name}/{stack_id}")
   @Fallback(FalseOnNotFoundOr404.class)
   @Nullable
   boolean update(@PathParam("stack_name") String name, @PathParam("stack_id") String id, UpdateOptions... options);


   @Named("stack:list_resources_with_nested_depth")
   @GET
   @SelectJson("resources")
   @Path("/{stack_name}/{stack_id}/resources")
   @Fallback(EmptyFluentIterableOnNotFoundOr404.class)
   FluentIterable<StackResource> listStackResources(@PathParam("stack_name") String stackName, @PathParam("stack_id") String stackId, @QueryParam("nested_depth") int nestedDepth);

   @Named("stack:list_resources")
   @GET
   @SelectJson("resources")
   @Path("/{stack_name}/{stack_id}/resources")
   @Fallback(EmptyFluentIterableOnNotFoundOr404.class)
   FluentIterable<StackResource> listStackResources(@PathParam("stack_name") String stackName, @PathParam("stack_id") String stackId);

   @Named("stack:get_resources")
   @GET
   @SelectJson("resource")
   @Path("/{stack_name}/{stack_id}/resources/{resource_name}")
   @Fallback(NullOnNotFoundOr404.class)
   StackResource getStackResource(@PathParam("stack_name") String stackName, @PathParam("stack_id") String stackId, @PathParam("resource_name") String name);

   @Named("stack:get_resources_metadata")
   @GET
   @SelectJson("metadata")
   @Path("/{stack_name}/{stack_id}/resources/{resource_name}/metadata")
   @Fallback(NullOnNotFoundOr404.class)
   Map<String, Object> getStackResourceMetadata(@PathParam("stack_name") String stackName, @PathParam("stack_id") String stackId, @PathParam("resource_name") String name);
}