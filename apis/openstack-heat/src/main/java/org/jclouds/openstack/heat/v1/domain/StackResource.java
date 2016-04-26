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
import java.util.Set;

import javax.inject.Named;

import org.jclouds.openstack.v2_0.domain.Link;
import org.jclouds.openstack.v2_0.domain.Resource;

import com.google.common.base.CaseFormat;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;

/**
 * Representation of an OpenStack Heat Stack Resources.
 */
public class StackResource extends Resource {

	@Named("logical_resource_id")
	private final String logicalResourceId;
	@Named("resource_status_reason")
	private final String statusReason;
	@Named("resource_status")
	private final Status status;
	@Named("physical_resource_id")
	private final String physicalResourceId;
	@Named("required_by")
	private Set<String> requiredBy = ImmutableSet.of();
	@Named("resource_type")
	private final String resourceType;
	@Named("updated_time")
	private final Date updated;
	@Named("attributes")
	private Attributes attributes;

	@ConstructorProperties({ "id", "resource_name", "links", "logical_resource_id", "resource_status_reason",
			"updated_time", "required_by", "resource_status", "physical_resource_id", "resource_type", "attributes" })
	public StackResource(String id, String name, Set<Link> links, String logicalResourceId, String statusReason,
			Date updated, Set<String> requiredBy, Status status, String physicalResourceId, String resourceType,
			Attributes attributes) {
		super(id, name, links);
		this.logicalResourceId = logicalResourceId;
		this.statusReason = statusReason;
		this.requiredBy = logicalResourceId == null ? ImmutableSet.<String> of() : ImmutableSet.copyOf(requiredBy);
		this.physicalResourceId = physicalResourceId;
		this.status = status;
		this.updated = updated;
		this.resourceType = resourceType;
		this.attributes = attributes;
	}

	public String getLogicalResourceId() {
		return logicalResourceId;
	}

	public String getStatusReason() {
		return statusReason;
	}

	public Status getStatus() {
		return status;
	}

	public String getPhysicalResourceId() {
		return physicalResourceId;
	}

	public Set<String> getRequiredBy() {
		return requiredBy;
	}

	public String getResourceType() {
		return resourceType;
	}

	public Date getUpdated() {
		return updated;
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public enum Status {
		INIT_COMPLETE, CREATE_IN_PROGRESS, CREATE_COMPLETE, CREATE_FAILED, UPDATE_IN_PROGRESS, UPDATE_COMPLETE, UPDATE_FAILED, DELETE_IN_PROGRESS, DELETE_COMPLETE, DELETE_FAILED, ROLLBACK_IN_PROGRESS, ROLLBACK_COMPLETE, ROLLBACK_FAILED, SUSPEND_IN_PROGRESS, SUSPEND_COMPLETE, SUSPEND_FAILED, RESUME_IN_PROGRESS, RESUME_COMPLETE, RESUME_FAILED, CHECK_IN_PROGRESS, CHECK_COMPLETE, CHECK_FAILED, UNRECOGNIZED;

		public String value() {
			return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, name());
		}

		@Override
		public String toString() {
			return value();
		}

		/**
		 * This provides GSON enum support in jclouds.
		 *
		 * @param name
		 *            The string representation of this enum value.
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
		return Objects.hashCode(super.hashCode(), logicalResourceId, physicalResourceId, requiredBy, resourceType,
				status, statusReason, updated,attributes);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		StackResource that = StackResource.class.cast(obj);
		return super.equals(that) && Objects.equal(this.logicalResourceId, that.logicalResourceId)
				&& Objects.equal(this.physicalResourceId, that.physicalResourceId)
				&& Objects.equal(this.requiredBy, that.requiredBy)
				&& Objects.equal(this.resourceType, that.resourceType) && Objects.equal(this.status, that.status)
				&& Objects.equal(this.statusReason, that.statusReason) && Objects.equal(this.updated, that.updated)
				&& Objects.equal(this.attributes, that.attributes);
	}

	protected Objects.ToStringHelper string() {
		return super.string().add("logicalResourceId", logicalResourceId).add("physicalResourceId", physicalResourceId)
				.add("requiredBy", requiredBy).add("resourceType", resourceType).add("status", status)
				.add("logicalResourceId", logicalResourceId).add("statusReason", statusReason).add("status", status)
				.add("statusReason", statusReason).add("updated", updated).add("attributes", attributes);
	}

	@Override
	public String toString() {
		return string().toString();
	}

	public static Builder<?> builder() {
		return new ConcreteBuilder();
	}

	public Builder<?> toBuilder() {
		return new ConcreteBuilder().fromStackResource(this);
	}

	public abstract static class Builder<T extends Builder<T>> extends Resource.Builder<T> {

		protected String logicalResourceId;
		protected String statusReason;
		protected Status status;
		protected String physicalResourceId;
		protected Set<String> requiredBy = ImmutableSet.of();
		protected String resourceType;
		protected Date updated;
		protected Attributes attributes;

		public T logicalResourceId(String logicalResourceId) {
			this.logicalResourceId = logicalResourceId;
			return self();
		}

		public T statusReason(String statusReason) {
			this.statusReason = statusReason;
			return self();
		}

		public T status(Status status) {
			this.status = status;
			return self();
		}

		public T physicalResourceId(String physicalResourceId) {
			this.physicalResourceId = physicalResourceId;
			return self();
		}

		public T requiredBy(Set<String> requiredBy) {
			this.requiredBy = requiredBy;
			return self();
		}

		public T resourceType(String resourceType) {
			this.resourceType = resourceType;
			return self();
		}

		public T updated(Date updated) {
			this.updated = updated;
			return self();
		}

		public T attributes(Attributes attributes) {
			this.attributes = attributes;
			return self();
		}

		/**
		 * @return A new Stack object.
		 */
		public StackResource build() {
			return new StackResource(id, name, links, logicalResourceId, statusReason, updated, requiredBy, status,
					physicalResourceId, resourceType, attributes);
		}

		public T fromStackResource(StackResource in) {
			return this.fromResource(in).logicalResourceId(in.getLogicalResourceId()).statusReason(in.getStatusReason())
					.updated(in.getUpdated()).requiredBy(in.getRequiredBy()).status(in.getStatus())
					.physicalResourceId(in.getPhysicalResourceId()).resourceType(in.getResourceType())
					.attributes(in.getAttributes());
		}

	}

	private static class ConcreteBuilder extends Builder<ConcreteBuilder> {
		@Override
		protected ConcreteBuilder self() {
			return this;
		}
	}

}