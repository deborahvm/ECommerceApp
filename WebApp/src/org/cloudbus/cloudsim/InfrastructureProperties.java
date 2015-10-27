/*******************************************************************************
 *
 *  *  * Copyright 2015 Deborah Maria Vieira Magalhães
 *  *  * 
 *  *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  *  * you may not use this file except in compliance with the License.
 *  *  * You may obtain a copy of the License at
 *  *  * 
 *  *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *  * 
 *  *  * Unless required by applicable law or agreed to in writing, software
 *  *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  * See the License for the specific language governing permissions and
 *  *  * limitations under the License.
 *******************************************************************************/

package org.cloudbus.cloudsim;

public enum InfrastructureProperties {
	DATACENTER_HOSTS("datacenter.hosts"),
	DATACENTER_ARCH("datacenter.arch"),
	DATACENTER_OS("datacenter.os"),
	DATACENTER_TIMEZONE("datacenter.time_zone"),
	DATACENTER_COST("datacenter.cost"),
	DATACENTER_COSTPERMEM("datacenter.costPerMem"),
	DATACENTER_COSTPERSTORAGE("datacenter.costPerStorage"),
	DATACENTER_COSTPERBW("datacenter.costPerBw"),
	DATACENTER_SCHEDINTERVAL("datacenter.schedulingInterval"),
	CORES_PERHOST("host.cores"),
	MIPS_PERCORE("host.core.mips"),
	MEMORY_PERHOST("host.memory"),
	STORAGE_PERHOST("host.storage"),
	BANDWIDTH_PERHOST("host.bandwidth"),
	MAXPOWER_PERHOST("host.maxPower"),
	STATICPOWERPERCENT_PERHOST("host.staticPowerPercent"),
	VMS_NUMBER("vms.number"),
	STORAGE_PERVM("vm.storage"),
	VMM_VM("vm.vmm"),
	FILESIZE_CLOUDLET("cloudlet.fileSize"),
	OUTPUTSIZE_CLOUDLET("cloudlet.outputSize"),
	UTILMODEL_CLOUDLET("cloudlet.utilizationModel"),
	PENUMBER_CLOUDLET("cloudlet.pesNumber");
	
	private String key;
	private final InfrastructureConfiguration configuration = InfrastructureConfiguration.INSTANCE;

	InfrastructureProperties(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}

	public String getProperty() {
		return configuration.getProperty(this.key);
	}

	public void setProperty(String value) {
		configuration.setProperty(this.key, value);
	}
}
