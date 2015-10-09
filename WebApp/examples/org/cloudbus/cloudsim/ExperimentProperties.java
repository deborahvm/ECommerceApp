package org.cloudbus.cloudsim;

public enum ExperimentProperties {
	
	EXPERIMENT_ROUNDS("experiment.rounds"),
	APP_NAME("aplication.name"),
	USERS_NUMBER("users.number"),
	USERS_ARRIVAL_RATE("users.arrival.rate"),
	USER_PROFILE("user.profile"),
	INPUT_LAYER_SIZE("input.layer.size"),
	PREDICTION_SIZE("prediction.size");

	private String key;
	private final ExperimentConfiguration configuration = ExperimentConfiguration.INSTANCE;

	ExperimentProperties(String key) {
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
