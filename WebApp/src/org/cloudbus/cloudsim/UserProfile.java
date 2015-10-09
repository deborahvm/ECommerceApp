package org.cloudbus.cloudsim;

import java.util.HashMap;

public class UserProfile {

	private String profileName;
	
	private HashMap<String, Double> mix = new HashMap<String, Double>();
	
	
	public UserProfile(String profileName) {
		this.profileName = profileName;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public void setOperationPercentage(String operation, Double percentage) {
		this.mix.put(operation, percentage);
	}

	public Double getOperationPercentage(String operation) {
		return this.mix.get(operation);
	}
}
