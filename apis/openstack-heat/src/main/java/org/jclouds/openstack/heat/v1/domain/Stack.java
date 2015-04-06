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
package org.jclouds.openstack.heat.v1.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import java.beans.ConstructorProperties;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Named;

import org.jclouds.openstack.v2_0.domain.Link;
import org.jclouds.openstack.v2_0.domain.Resource;

import com.google.common.base.CaseFormat;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * Representation of an OpenStack Heat Stack.
 */
public class Stack extends Resource {

   private final String description;
   @Named("stack_owner")
   private final String owner;
   private final String project;
   private final Set<String> capabilities;
   //private final List<Capabilities> capabilities;
   private final Map<String, String> parameters;
   //List<Output> outputs;
   private final List<String> outputs;
   @Named("notification_topics")
   private final List<String> notificationTopics;
   //private final List<NotificationTopics> notificationTopics;
   @Named("template_description")
   private final String templateDescription;

   @Named("stack_status")
   private final Status status;
   @Named("stack_status_reason")
   private final String statusReason;

   @Named("creation_time")
   private final Date created;
   @Named("updated_time")
   private final Date updated;

   @Named("timeout_mins")
   private final int timeoutMins; //(int?)
   @Named("disable_rollback")
   private final boolean disableRollback;


   @ConstructorProperties({"id", "stack_name", "links", "description", "owner", "capabilities", "parameters", "outputs",
         "notification_topics", "template_description", "stack_status", "stack_status_reason", "creation_time",
         "updated_time", "timeout_mins", "disable_rollback", "project"})
   public Stack(String id, String name, Set<Link> links, String description, String owner, Set<String> capabilities,
                Map<String, String> parameters, List<String> outputs, List<String> notificationTopics,
                String templateDescription, Stack.Status status, String statusReason, Date created, Date updated, int timeoutMins,
                boolean disableRollback, String project) {
      super(id, name, links);
      this.description = description;
      this.owner = owner;
      this.capabilities = capabilities == null ? ImmutableSet.<String>of() : ImmutableSet.copyOf(capabilities);
      this.parameters = parameters == null ? ImmutableMap.<String, String>of() : ImmutableMap.copyOf(parameters);
      this.outputs = outputs == null ? ImmutableList.<String>of() : ImmutableList.copyOf(outputs);
      this.notificationTopics = notificationTopics == null ? ImmutableList.<String>of() : ImmutableList.copyOf(notificationTopics);
      this.templateDescription = templateDescription;
      this.status = status;
      this.statusReason = statusReason;
      this.created = created;
      this.updated = updated;
      this.timeoutMins = timeoutMins;
      this.disableRollback = disableRollback;
      this.project = project;
   }

   /**
    * @return the description of this Stack.
    */
   public String getDescription() {
      return description;
   }

   /**
    * @return the owner of this Stack.
    */
   public String getOwner() {
      return owner;
   }

   /**
    * @return the capabilities of this Stack.
    */
   public Set<String> getCapabilities() {
      return capabilities;
   }

   /**
    * @return the parameters of this Stack.
    */
   public Map<String, String> getParameters() {
      return parameters;
   }

   /**
    * @return the outputs of this Stack.
    */
   public List<String> getOutputs() {
      return outputs;
   }

   /**
    * @return the notification topics of this Stack.
    */
   public List<String> getNotificationTopics() {
      return notificationTopics;
   }

   /**
    * @return the template description of this Stack.
    */
   public String getTemplateDescription() {
      return templateDescription;
   }

   /**
    * @return the status of this Stack.
    */
   public Status getStatus() {
      return status;
   }

   /**
    * @return the status reason for this Stack.
    */
   public String getStatusReason() {
      return statusReason;
   }

   /**
    * @return the project ID of the stack
    */
   public String getProject() {
      return project;
   }

   /**
    * @return the date this Stack was created.
    */
   public Date getCreated() {
      return created;
   }

   /**
    * @return the date this Stack was last updated.
    */
   public Date getUpdated() {
      return updated;
   }

   /**
    * @return the timeout of this Stack (in minutes).
    */
   public int getTimeoutMins() {
      return timeoutMins;
   }

   /**
    * @return true if rollback is disabled for this Stack, false otherwise.
    */
   public boolean isRollbackDisabled() {
      return disableRollback;
   }

   public enum Action {
      SUSPEND, RESUME,
      UNRECOGNIZED;

      public String value() {
         return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, name());
      }

      @Override
      public String toString() {
         return value();
      }

      /**
       * This provides GSON enum support in jclouds.
       * @param name The string representation of this enum value.
       * @return The corresponding enum value.
       */

      public static Action fromValue(String action) {
         try {
            return valueOf(checkNotNull(action, "action"));
         } catch (IllegalArgumentException e) {
            return UNRECOGNIZED;
         }
      }
   }

   public enum Status {
      CREATE_IN_PROGRESS, CREATE_COMPLETE, CREATE_FAILED,
      UPDATE_IN_PROGRESS, UPDATE_COMPLETE, UPDATE_FAILED,
      DELETE_IN_PROGRESS, DELETE_COMPLETE, DELETE_FAILED,
      ROLLBACK_IN_PROGRESS, ROLLBACK_COMPLETE, ROLLBACK_FAILED,
      SUSPEND_IN_PROGRESS, SUSPEND_COMPLETE, SUSPEND_FAILED,
      RESUME_IN_PROGRESS, RESUME_COMPLETE, RESUME_FAILED,
      UNRECOGNIZED;

      public String value() {
         return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, name());
      }

      @Override
      public String toString() {
         return value();
      }

      /**
       * This provides GSON enum support in jclouds.
       * @param name The string representation of this enum value.
       * @return The corresponding enum value.
       */

      public static Status fromValue(String status) {
         try {
            return valueOf(checkNotNull(status, "status"));
         } catch (IllegalArgumentException e) {
            return UNRECOGNIZED;
         }
      }
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(super.hashCode(), description, owner, capabilities, parameters, outputs, notificationTopics,
            templateDescription, status, statusReason, created, updated, timeoutMins, disableRollback);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      Stack that = Stack.class.cast(obj);
      return super.equals(that) &&
            Objects.equal(this.description, that.description) &&
            Objects.equal(this.owner, that.owner) &&
            Objects.equal(this.capabilities, that.capabilities) &&
            Objects.equal(this.parameters, that.parameters) &&
            Objects.equal(this.outputs, that.outputs) &&
            Objects.equal(this.notificationTopics, that.notificationTopics) &&
            Objects.equal(this.templateDescription, that.templateDescription) &&
            Objects.equal(this.status, that.status) &&
            Objects.equal(this.statusReason, that.statusReason) &&
            Objects.equal(this.created, that.created) &&
            Objects.equal(this.updated, that.updated) &&
            Objects.equal(this.timeoutMins, that.timeoutMins) &&
            Objects.equal(this.disableRollback, that.disableRollback) &&
            Objects.equal(this.project, that.project);
   }

   protected Objects.ToStringHelper string() {
      return super.string()
            .add("description", description)
            .add("owner", owner)
            .add("capabilities", capabilities)
            .add("parameters", parameters)
            .add("outputs", outputs)
            .add("notificationTopics", notificationTopics)
            .add("templateDescription", templateDescription)
            .add("status", status)
            .add("statusReason", statusReason)
            .add("created", created)
            .add("updated", updated)
            .add("timeoutMins", timeoutMins)
            .add("disableRollback", disableRollback)
            .add("project", project);
   }

   @Override
   public String toString() {
      return string().toString();
   }

   public static Builder<?> builder() {
      return new ConcreteBuilder();
   }

   public Builder<?> toBuilder() {
      return new ConcreteBuilder().fromStack(this);
   }

   public abstract static class Builder<T extends Builder<T>> extends Resource.Builder<T> {

      protected String description;
      protected String owner;
      protected String project;
      protected Set<String> capabilities;
      protected Map<String, String> parameters;  // StackParameter
      protected List<String> outputs;
      protected List<String> notificationTopics;
      protected String templateDescription;
      protected Status status;
      protected String statusReason;
      protected Date created;
      protected Date updated;
      protected int timeoutMins;
      protected boolean disableRollback;

      /**
       * @param name The description of this Stack.
       * @return The builder object.
       * @see Stack#getDescription()
       */
      public T description(String description) {
         this.description = description;
         return self();
      }

      /**
       * @param owner The owner of this Stack.
       * @return The builder object.
       * @see org.jclouds.openstack.heat.v1.domain.Stack#getOwner() ()
       */
      public T owner(String owner) {
         this.owner = owner;
         return self();
      }

      /**
       * @param project The owner of this Stack.
       * @return The builder object.
       * @see org.jclouds.openstack.heat.v1.domain.Stack#getProject() ()
       */
      public T project(String project) {
         this.project = project;
         return self();
      }

      /**
       * @param capabilities The capabilities of this Stack.
       * @return The builder object.
       * @see Stack#getCapabilities()
       */
      public T capabilities(Set<String> capabilities) {
         this.capabilities = capabilities;
         return self();
      }

      /**
       * @param parameters The parameters of this Stack.
       * @return The builder object.
       * @see Stack#getParameters()
       */
//      public Builder parameters(Set<StackParameter> parameters) {
      public T parameters(Map<String, String> parameters) {
         this.parameters = parameters;
         return self();
      }

      /**
       * @param outputs The outputs of this Stack.
       * @return The builder object.
       * @see Stack#getOutputs()
       */
      public T outputs(List<String> outputs) {
         this.outputs = outputs;
         return self();
      }

      /**
       * @param notificationTopics The notification topics for this Stack.
       * @return The builder object.
       * @see Stack#getNotificationTopics()
       */
      public T notificationTopics(List<String> notificationTopics) {
         this.notificationTopics = notificationTopics;
         return self();
      }

      /**
       * @param templateDescription The status reason for this Resource.
       * @return The builder object.
       * @see Stack#getTemplateDescription()
       */
      public T templateDescription(String templateDescription) {
         this.templateDescription = templateDescription;
         return self();
      }

      /**
       * @param status The status of this Stack.
       * @return The builder object.
       * @see Stack#getStatus()
       */
      public T status(Status status) {
         this.status = status;
         return self();
      }

      /**
       * @param statusReason The status reason of this Stack.
       * @return The builder object.
       * @see Stack#getStatusReason()
       */
      public T statusReason(String statusReason) {
         this.statusReason = statusReason;
         return self();
      }

      /**
       * @param created The created date of this Stack.
       * @return The builder object.
       * @see Stack#getCreated()
       */
      public T created(Date created) {
         this.created = created;
         return self();
      }

      /**
       * @param updated The updated date of this Stack.
       * @return The builder object.
       * @see Stack#getUpdated()
       */
      public T updated(Date updated) {
         this.updated = updated;
         return self();
      }

      /**
       * @param timeoutMins The Stack creation timeout (in minutes).
       * @return The builder object.
       * @see Stack#getTimeoutMins()
       */
      public T timeoutMins(int timeoutMins) {
         this.timeoutMins = timeoutMins;
         return self();
      }

      /**
       * @param disableRollback Controls whether a failure during stack creation causes deletion of all previously-
       *                        created resources in that stack.
       * @return The builder object.
       * @see Stack#isRollbackDisabled()
       */
      public T disableRollback(boolean disableRollback) {
         this.disableRollback = disableRollback;
         return self();
      }

      /**
       * @return A new Stack object.
       */
      public Stack build() {
         return new Stack(id, name, links, description, owner, capabilities, parameters, outputs, notificationTopics,
               templateDescription, status, statusReason, created, updated, timeoutMins, disableRollback, project);
      }

      public T fromStack(Stack in) {
         return this.fromResource(in)
               .description(in.getDescription())
               .owner(in.getOwner())
               .capabilities(in.getCapabilities())
               .parameters(in.getParameters())
               .outputs(in.getOutputs())
               .notificationTopics(in.getNotificationTopics())
               .templateDescription(in.getTemplateDescription())
               .status(in.getStatus())
               .statusReason(in.getStatusReason())
               .created(in.getCreated())
               .updated(in.getUpdated())
               .timeoutMins(in.getTimeoutMins())
               .disableRollback(in.isRollbackDisabled())
               .project(in.getProject());
      }

   }

   private static class ConcreteBuilder extends Builder<ConcreteBuilder> {
      @Override
      protected ConcreteBuilder self() {
         return this;
      }
   }

}