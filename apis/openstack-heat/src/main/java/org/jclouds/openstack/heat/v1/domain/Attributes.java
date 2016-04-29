/**
 * 
 */
package org.jclouds.openstack.heat.v1.domain;

import java.beans.ConstructorProperties;
import java.util.Set;

import javax.inject.Named;

import org.jclouds.openstack.v2_0.domain.Link;
import org.jclouds.openstack.v2_0.domain.Resource;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;

/**
 * @author deviyada
 * 
 */
public class Attributes extends Resource {

	private String progress;
	private String status;
	@Named("config_drive")
	private String configDrive;
	@Named("tenant_id")
	private String tenantId;
	@Named("host_id")
	private String hostId;
	private String created;
	@Named("user_id")
	private String userId;

	@ConstructorProperties({ "id", "name","links","progress", "status", "config_drive",
			"tenant_id", "host_id", "created", "user_id"})
	protected Attributes(String id,String name, Set<Link> links, String progress, String status,
			String configDrive, String tenantId, String hostId,String created, String userId) {
		super(id, name, links);
		this.progress = progress;
		this.status = status;
		this.configDrive = configDrive;
		this.tenantId = tenantId;
		this.hostId = hostId;
		this.created = created;
		this.userId = userId;
	}

	public String getProgress() {
		return progress;
	}

	public String getStatus() {
		return status;
	}

	public String getConfigDrive() {
		return configDrive;
	}

	public String getTenantId() {
		return tenantId;
	}

	public String getHostId() {
		return hostId;
	}

	public String getCreated() {
		return created;
	}

	public String getUserId() {
		return userId;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(super.hashCode(), progress, status, configDrive, tenantId,
				hostId, created,userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Attributes that = Attributes.class.cast(obj);
		return super.equals(that) 
				&&  Objects.equal(this.progress, that.progress)
				&& Objects.equal(this.status, that.status)
				&& Objects.equal(this.configDrive, that.configDrive)
				&& Objects.equal(this.tenantId, that.tenantId)
				&& Objects.equal(this.hostId, that.hostId)
				&& Objects.equal(this.created, that.created)
				&& Objects.equal(this.userId, that.userId);
	}

	protected ToStringHelper string() {
		return	super.string()
				.add("progress", progress).add("status", status)
				.add("config_drive", configDrive).add("tenant_id", tenantId)
				.add("host_id", hostId).add("created", created)
				.add("user_id", userId);
	}

	 @Override
	   public String toString() {
	      return string().toString();
	   }

	   public static Builder<?> builder() {
	      return new ConcreteBuilder();
	   }

	   public Builder<?> toBuilder() {
	      return new ConcreteBuilder().fromAttributes(this);
	   }
	
	public abstract static class Builder<T extends Builder<T>> extends Resource.Builder<T> 
	{
		protected String progress;
		protected String status;
		protected String configDrive;
		protected String tenantId;
		protected String hostId;
		protected String created;
		protected String userId;
		
		public T progress(String progress) {
			this.progress = progress;
			return self();
		}
		
		public T status(String status) {
			this.status = status;
			return self();
		}

		public T configDrive(String configDrive) {
			this.configDrive = configDrive;
			return self();
		}

		public T tenantId(String tenantId) {
			this.tenantId = tenantId;
			return self();
		}

		public T hostId(String hostId) {
			this.hostId = hostId;
			return self();
		}

		public T created(String created) {
			this.created = created;
			return self();
		}

		public T userId(String userId) {
			this.userId = userId;
			return self();
		}

		
		public Attributes build() {
			return new Attributes(id,name,links, progress, status, configDrive, tenantId,
					hostId, created,userId);
		}
		
		 public T fromAttributes(Attributes in) {
	         return this.fromResource(in)
	        	   .progress(in.getProgress())
	               .status(in.getStatus())
	               .configDrive(in.getConfigDrive())
	               .tenantId(in.getTenantId())
	               .hostId(in.getHostId())
	               .created(in.getCreated())
	               .userId(in.getUserId());
	      }
	}
	
	private static class ConcreteBuilder extends Builder<ConcreteBuilder> {
	      @Override
		protected ConcreteBuilder self() {
			return this;
		}
	}

}
