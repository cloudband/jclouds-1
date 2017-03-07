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
package org.jclouds.openstack.cinder.v2.domain;

import java.beans.ConstructorProperties;

import javax.inject.Named;

import org.jclouds.javax.annotation.Nullable;

import com.google.common.base.Objects;

public class PoolCapability {

    public static Builder<?> builder() {
        return new ConcreteBuilder();
    }

    private static class ConcreteBuilder extends Builder<ConcreteBuilder> {
        @Override
        protected ConcreteBuilder self() {
            return this;
        }
    }

    public abstract static class Builder<T extends Builder<T>> {
        protected abstract T self();

        protected String filterFunction;
        protected String goodnessFunction;
        protected String timestamp;
        protected String volumeBackendName;
        protected double freeCapacityGB;
        protected String driverVersion;
        protected double totalCapacityGB;
        protected float reservedPercentage;
        protected String vendorName;
        protected String storageProtocol;


        public T filterFunction(String filterFunction) {
            this.filterFunction = filterFunction;
            return self();
        }

        public T goodnessFunction(String goodnessFunction) {
            this.goodnessFunction = goodnessFunction;
            return self();
        }

        public T timestamp(String timestamp) {
            this.timestamp = timestamp;
            return self();
        }

        public T volumeBackendName(String volumeBackendName) {
            this.volumeBackendName = volumeBackendName;
            return self();
        }

        public T freeCapacityGB(double freeCapacityGB) {
            this.freeCapacityGB = freeCapacityGB;
            return self();
        }

        public T driverVersion(String driverVersion) {
            this.driverVersion = driverVersion;
            return self();
        }

        public T totalCapacityGB(double totalCapacityGB) {
            this.totalCapacityGB = totalCapacityGB;
            return self();
        }

        public T reservedPercentage(float reservedPercentage) {
            this.reservedPercentage = reservedPercentage;
            return self();
        }

        public T vendorName(String vendorName) {
            this.vendorName = vendorName;
            return self();
        }

        public T storageProtocol(String storageProtocol) {
            this.storageProtocol = storageProtocol;
            return self();
        }

        public T fromPoolCapability(PoolCapability in) {
            return self().filterFunction(in.filterFunction)
                    .goodnessFunction(in.goodnessFunction)
                    .timestamp(in.timestamp)
                    .volumeBackendName(in.volumeBackendName)
                    .freeCapacityGB(in.freeCapacityGB)
                    .driverVersion(in.driverVersion)
                    .totalCapacityGB(in.totalCapacityGB)
                    .reservedPercentage(in.reservedPercentage)
                    .vendorName(in.vendorName)
                    .storageProtocol(in.storageProtocol);
        }

        public PoolCapability build() {
            return new PoolCapability(filterFunction, goodnessFunction, timestamp, volumeBackendName, freeCapacityGB, driverVersion, totalCapacityGB, reservedPercentage, vendorName, storageProtocol);
        }
    }


    @Named("filter_function")
    private final String filterFunction;
    @Named("goodness_function")
    private final String goodnessFunction;
    @Named("timestamp")
    private final String timestamp;
    @Named("volume_backend_name")
    private final String volumeBackendName;
    @Named("free_capacity_gb")
    private final double freeCapacityGB;
    @Named("driver_version")
    private final String driverVersion;
    @Named("total_capacity_gb")
    private final double totalCapacityGB;
    @Named("reserved_percentage")
    private final float reservedPercentage;
    @Named("vendor_name")
    private final String vendorName;
    @Named("storage_protocol")
    private final String storageProtocol;

    @ConstructorProperties({
            "filter_function", "goodness_function", "timestamp", "volume_backend_name", "free_capacity_gb", "driver_version", "total_capacity_gb", "reserved_percentage", "vendor_name", "storage_protocol"
    })
    protected PoolCapability(@Nullable String filterFunction, @Nullable String goodnessFunction, String timestamp, String volumeBackendName, double freeCapacityGB, String driverVersion, double totalCapacityGB, float reservedPercentage, String vendorName, String storageProtocol) {
        this.filterFunction = filterFunction;
        this.goodnessFunction = goodnessFunction;
        this.timestamp = timestamp;
        this.volumeBackendName = volumeBackendName;
        this.freeCapacityGB = freeCapacityGB;
        this.driverVersion = driverVersion;
        this.totalCapacityGB = totalCapacityGB;
        this.reservedPercentage = reservedPercentage;
        this.vendorName = vendorName;
        this.storageProtocol = storageProtocol;
    }

    /**
     * @return the filterFunction
     */
    public String getFilterFunction() {
        return filterFunction;
    }

    /**
     * @return the goodnessFunction
     */
    public String getGoodnessFunction() {
        return goodnessFunction;
    }

    /**
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * @return the volumeBackendName
     */
    public String getVolumeBackendName() {
        return volumeBackendName;
    }

    /**
     * @return the freeCapacityGB
     */
    public double getFreeCapacityGB() {
        return freeCapacityGB;
    }

    /**
     * @return the driverVersion
     */
    public String getDriverVersion() {
        return driverVersion;
    }

    /**
     * @return the totalCapacityGB
     */
    public double getTotalCapacityGB() {
        return totalCapacityGB;
    }

    /**
     * @return the reservedPercentage
     */
    public float getReservedpercentage() {
        return reservedPercentage;
    }

    /**
     * @return the vendorName
     */
    public String getVendorName() {
        return vendorName;
    }

    /**
     * @return the storageProtocol
     */
    public String getStorageProtocol() {
        return storageProtocol;
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(filterFunction, goodnessFunction, timestamp, volumeBackendName, freeCapacityGB, driverVersion, totalCapacityGB, reservedPercentage, vendorName, storageProtocol);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PoolCapability that = PoolCapability.class.cast(obj);
        return Objects.equal(this.filterFunction, that.filterFunction)
                && Objects.equal(this.goodnessFunction, that.goodnessFunction)
                && Objects.equal(this.timestamp, that.timestamp)
                && Objects.equal(this.volumeBackendName, that.volumeBackendName)
                && Objects.equal(this.freeCapacityGB, that.freeCapacityGB)
                && Objects.equal(this.driverVersion, that.driverVersion)
                && Objects.equal(this.totalCapacityGB, that.totalCapacityGB)
                && Objects.equal(this.reservedPercentage, that.reservedPercentage)
                && Objects.equal(this.vendorName, that.vendorName)
                && Objects.equal(this.storageProtocol, that.storageProtocol);

    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("filterFunction", filterFunction)
                .add("goodnessFunction", goodnessFunction)
                .add("timestamp", timestamp)
                .add("volumeBackendName", volumeBackendName)
                .add("freeCapacityGB", freeCapacityGB)
                .add("driverVersion", driverVersion)
                .add("totalCapacityGB", totalCapacityGB)
                .add("goodnessFunction", goodnessFunction)
                .add("reservedPercentage", reservedPercentage)
                .add("vendorName", vendorName)
                .add("storageProtocol", storageProtocol).toString();
    }

    public Builder<?> toBuilder() {
        return new ConcreteBuilder().fromPoolCapability(this);
    }
}
