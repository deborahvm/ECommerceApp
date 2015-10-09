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
