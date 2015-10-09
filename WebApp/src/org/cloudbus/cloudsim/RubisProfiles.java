package org.cloudbus.cloudsim;

import java.util.ArrayList;
import java.util.List;

public class RubisProfiles implements AppProfiles {
	
	private String appName;
	
	private List<UserProfile> profileList = new ArrayList<UserProfile>();
	
	public RubisProfiles() {
		this.appName = "Rubis";
		
		UserProfile userProfile1 = new UserProfile("Browsing");
		userProfile1.setOperationPercentage("browsing", 100.0);
		userProfile1.setOperationPercentage("bidding", 0.0);
		UserProfile userProfile2 = new UserProfile("Bidding");
		userProfile2.setOperationPercentage("browsing", 85.0);
		userProfile1.setOperationPercentage("bidding", 15.0);
		
		profileList.add(userProfile1);
		profileList.add(userProfile2);
	}

	@Override
	public String getAppName() {
		return appName;
	}

	@Override
	public List<UserProfile> getProfileList() {
		return profileList;
	}

}
