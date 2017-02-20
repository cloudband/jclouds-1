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

import com.google.common.base.Objects;

import java.beans.ConstructorProperties;

public class AbsoluteLimit {

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

        protected int totalSnapshotsUsed;
        protected int maxTotalBackups;
        protected double maxTotalVolumeGigabytes;
        protected int maxTotalSnapshots;
        protected double maxTotalBackupGigabytes;
        protected double totalBackupGigabytesUsed;
        protected int maxTotalVolumes;
        protected int totalVolumesUsed;
        protected int totalBackupsUsed;
        protected double totalGigabytesUsed;

        protected abstract T self();

        public T totalSnapshotsUsed(int totalSnapshotsUsed) {
            this.totalSnapshotsUsed = totalSnapshotsUsed;
            return self();
        }

        public T maxTotalBackups(int maxTotalBackups) {
            this.maxTotalBackups = maxTotalBackups;
            return self();
        }

        public T maxTotalVolumeGigabytes(double maxTotalVolumeGigabytes) {
            this.maxTotalVolumeGigabytes = maxTotalVolumeGigabytes;
            return self();
        }

        public T maxTotalSnapshots(int maxTotalSnapshots) {
            this.maxTotalSnapshots = maxTotalSnapshots;
            return self();
        }

        public T maxTotalBackupGigabytes(double maxTotalBackupGigabytes) {
            this.maxTotalBackupGigabytes = maxTotalBackupGigabytes;
            return self();
        }

        public T totalBackupGigabytesUsed(double totalBackupGigabytesUsed) {
            this.totalBackupGigabytesUsed = totalBackupGigabytesUsed;
            return self();
        }

        public T maxTotalVolumes(int maxTotalVolumes) {
            this.maxTotalVolumes = maxTotalVolumes;
            return self();
        }

        public T totalVolumesUsed(int totalVolumesUsed) {
            this.totalVolumesUsed = totalVolumesUsed;
            return self();
        }

        public T totalBackupsUsed(int totalBackupsUsed) {
            this.totalBackupsUsed = totalBackupsUsed;
            return self();
        }

        public T totalGigabytesUsed(double totalGigabytesUsed) {
            this.totalGigabytesUsed = totalGigabytesUsed;
            return self();
        }

        public T fromAbsoluteLimit(AbsoluteLimit in) {
            return self().totalSnapshotsUsed(in.totalSnapshotsUsed)
                    .maxTotalBackups(in.maxTotalBackups)
                    .maxTotalVolumeGigabytes(in.maxTotalVolumeGigabytes)
                    .maxTotalSnapshots(in.maxTotalSnapshots)
                    .maxTotalBackupGigabytes(in.maxTotalBackupGigabytes)
                    .totalBackupGigabytesUsed(in.totalBackupGigabytesUsed)
                    .maxTotalVolumes(in.maxTotalVolumes)
                    .totalVolumesUsed(in.totalVolumesUsed)
                    .totalBackupsUsed(in.totalBackupsUsed)
                    .totalGigabytesUsed(in.totalGigabytesUsed);
        }

        public AbsoluteLimit build() {
            return new AbsoluteLimit(totalSnapshotsUsed, maxTotalBackups, maxTotalVolumeGigabytes, maxTotalSnapshots, maxTotalBackupGigabytes,
                    totalBackupGigabytesUsed, maxTotalVolumes, totalVolumesUsed, totalBackupsUsed, totalGigabytesUsed);
        }
    }

    private final int totalSnapshotsUsed;
    private final int maxTotalBackups;
    private final double maxTotalVolumeGigabytes;
    private final int maxTotalSnapshots;
    private final double maxTotalBackupGigabytes;
    private final double totalBackupGigabytesUsed;
    private final int maxTotalVolumes;
    private final int totalVolumesUsed;
    private final int totalBackupsUsed;
    private final double totalGigabytesUsed;

    @ConstructorProperties({
            "totalSnapshotsUsed", "maxTotalBackups", "maxTotalVolumeGigabytes", "maxTotalSnapshots", "maxTotalBackupGigabytes",
            "totalBackupGigabytesUsed", "maxTotalVolumes", "totalVolumesUsed", "totalBackupsUsed", "totalGigabytesUsed"
    })
    public AbsoluteLimit(int totalSnapshotsUsed, int maxTotalBackups, double maxTotalVolumeGigabytes, int maxTotalSnapshots, double maxTotalBackupGigabytes, double totalBackupGigabytesUsed, int maxTotalVolumes, int totalVolumesUsed, int totalBackupsUsed, double totalGigabytesUsed) {
        this.totalSnapshotsUsed = totalSnapshotsUsed;
        this.maxTotalBackups = maxTotalBackups;
        this.maxTotalVolumeGigabytes = maxTotalVolumeGigabytes;
        this.maxTotalSnapshots = maxTotalSnapshots;
        this.maxTotalBackupGigabytes = maxTotalBackupGigabytes;
        this.totalBackupGigabytesUsed = totalBackupGigabytesUsed;
        this.maxTotalVolumes = maxTotalVolumes;
        this.totalVolumesUsed = totalVolumesUsed;
        this.totalBackupsUsed = totalBackupsUsed;
        this.totalGigabytesUsed = totalGigabytesUsed;
    }


    public int getTotalSnapshotsUsed() {
        return totalSnapshotsUsed;
    }

    public int getMaxTotalBackups() {
        return maxTotalBackups;
    }

    public double getMaxTotalVolumeGigabytes() {
        return maxTotalVolumeGigabytes;
    }

    public int getMaxTotalSnapshots() {
        return maxTotalSnapshots;
    }

    public double getMaxTotalBackupGigabytes() {
        return maxTotalBackupGigabytes;
    }

    public double getTotalBackupGigabytesUsed() {
        return totalBackupGigabytesUsed;
    }

    public int getMaxTotalVolumes() {
        return maxTotalVolumes;
    }

    public int getTotalVolumesUsed() {
        return totalVolumesUsed;
    }

    public int getTotalBackupsUsed() {
        return totalBackupsUsed;
    }

    public double getTotalGigabytesUsed() {
        return totalGigabytesUsed;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(totalSnapshotsUsed, maxTotalBackups, maxTotalVolumeGigabytes, maxTotalSnapshots, maxTotalBackupGigabytes,
                totalBackupGigabytesUsed, maxTotalVolumes, totalVolumesUsed, totalBackupsUsed, totalGigabytesUsed);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AbsoluteLimit that = AbsoluteLimit.class.cast(obj);
        return Objects.equal(this.totalSnapshotsUsed, that.totalSnapshotsUsed)
                && Objects.equal(this.maxTotalBackups, that.maxTotalBackups)
                && Objects.equal(this.maxTotalVolumeGigabytes, that.maxTotalVolumeGigabytes)
                && Objects.equal(this.maxTotalSnapshots, that.maxTotalSnapshots)
                && Objects.equal(this.maxTotalBackupGigabytes, that.maxTotalBackupGigabytes)
                && Objects.equal(this.totalBackupGigabytesUsed, that.totalBackupGigabytesUsed)
                && Objects.equal(this.maxTotalVolumes, that.maxTotalVolumes)
                && Objects.equal(this.totalVolumesUsed, that.totalVolumesUsed)
                && Objects.equal(this.totalBackupsUsed, that.totalBackupsUsed)
                && Objects.equal(this.totalGigabytesUsed, that.totalGigabytesUsed);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("totalSnapshotsUsed", totalSnapshotsUsed)
                .add("maxTotalBackups", maxTotalBackups)
                .add("maxTotalVolumeGigabytes", maxTotalVolumeGigabytes)
                .add("maxTotalSnapshots", maxTotalSnapshots)
                .add("maxTotalBackupGigabytes", maxTotalBackupGigabytes)
                .add("totalBackupGigabytesUsed", totalBackupGigabytesUsed)
                .add("maxTotalVolumes", maxTotalVolumes)
                .add("totalVolumesUsed", totalVolumesUsed)
                .add("totalBackupsUsed", totalBackupsUsed)
                .add("totalGigabytesUsed", totalGigabytesUsed).toString();
    }

    public Builder<?> toBuilder() {
        return new ConcreteBuilder().fromAbsoluteLimit(this);
    }

}
