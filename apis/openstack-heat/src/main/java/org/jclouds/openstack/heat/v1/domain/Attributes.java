/**
 * 
 */
package org.jclouds.openstack.heat.v1.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import java.beans.ConstructorProperties;

import javax.inject.Named;

import org.jclouds.openstack.v2_0.domain.Link;
import org.jclouds.openstack.v2_0.services.Image;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;

/**
 * @author deviyada
 *
 */
public class Attributes {

	@Named("id")
	private String id;
	@Named("progress")
	private String progress;
	@Named("status")
	private String status;
	@Named("config_drive")
	private String config_drive;
	@Named("tenant_id")
	private String tenant_id;
	@Named("hostId")
	private String hostId;
	@Named("created")
	private String created;
	@Named("name")
	private String name;
	@Named("user_id")
	private String user_id;
	@Named("links")
	private Link[] links;

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getConfig_drive() {
		return config_drive;
	}

	public void setConfig_drive(String config_drive) {
		this.config_drive = config_drive;
	}

	public String getTenant_id() {
		return tenant_id;
	}

	public void setTenant_id(String tenant_id) {
		this.tenant_id = tenant_id;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public Link[] getLinks() {
		return links;
	}

	public void setLinks(Link[] links) {
		this.links = links;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Attributes build() {
		return new Attributes(id, progress, status, config_drive, tenant_id, hostId, created, user_id, links);
	}

	@ConstructorProperties({ "id", "progress", "status", "config_drive", "tenant_id", "hostId", "created", "user_id",
			"links"})
	protected Attributes(String id, String progress, String status, String config_drive, String tenant_id,
			String hostId, String created, String user_id, Link[] links) {
		this.id = checkNotNull(id, "id");
		this.progress = progress;
		this.status = status;
		this.config_drive = config_drive;
		this.tenant_id = tenant_id;
		this.hostId = hostId;
		this.created = created;
		this.user_id = user_id;
		this.links = links;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id, tenant_id,hostId,status,progress,config_drive,created,user_id,links);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Attributes that = Attributes.class.cast(obj);
		return 	   Objects.equal(this.id, that.id) 
				&& Objects.equal(this.tenant_id, that.tenant_id)
				&& Objects.equal(this.hostId, that.hostId)
				&& Objects.equal(this.status, that.status)
				&& Objects.equal(this.progress, that.progress)
				&& Objects.equal(this.config_drive, that.config_drive)
				&& Objects.equal(this.created, that.created)
				&& Objects.equal(this.user_id, that.user_id)
				&& Objects.equal(this.links, that.links);
	}

	protected ToStringHelper string() {
		return Objects.toStringHelper(this).add("id", id)
										  .add("tenant_id", tenant_id)
										  .add("hostId", hostId)
										  .add("status", status)
										  .add("progress", progress)
										  .add("config_drive", config_drive)
										  .add("created", created)
										  .add("user_id", user_id)
										  .add("links", links);
	}

	@Override
	public String toString() {
		return string().toString();
	}

}
