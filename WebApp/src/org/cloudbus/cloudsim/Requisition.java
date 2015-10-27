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

public class Requisition {
	private int arrivalTime;
	private int size;
	private Cloudlet cloudlet;

	public Requisition(int requisitionID, int arrivalTime, int size) {
		this.arrivalTime = arrivalTime;
		this.size = size;
		this.cloudlet = createCloudlet(requisitionID, size);
	}

	private Cloudlet createCloudlet(int requisitionID, int size) {

		long fileSize = Long
				.parseLong(InfrastructureProperties.FILESIZE_CLOUDLET
						.getProperty());
		long outputSize = Long
				.parseLong(InfrastructureProperties.OUTPUTSIZE_CLOUDLET
						.getProperty());
		int pesNumber = Integer
				.parseInt(InfrastructureProperties.PENUMBER_CLOUDLET
						.getProperty());
		String modelName = InfrastructureProperties.UTILMODEL_CLOUDLET
				.getProperty();

		Cloudlet _cloudlet = null;
		try {
			Class<?> modelClass = Class.forName(modelName, true,
					UtilizationModel.class.getClassLoader());
			UtilizationModel utilizationModel = (UtilizationModel) modelClass
					.newInstance();
			_cloudlet = new Cloudlet(requisitionID, size, pesNumber,
					fileSize, outputSize, utilizationModel, utilizationModel,
					utilizationModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return _cloudlet;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Cloudlet getCloudlet() {
		return cloudlet;
	}

	public void setCloudlet(Cloudlet cloudlet) {
		this.cloudlet = cloudlet;
	}

}
