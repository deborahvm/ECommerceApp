package org.cloudbus.cloudsim;

import java.util.ArrayList;
import java.util.List;

public class TCPWProfiles implements AppProfiles {

	private String appName;
	
	private List<UserProfile> profileList = new ArrayList<UserProfile>();
	
	public TCPWProfiles() {
		this.appName = "TCP-W";
		
		UserProfile userProfile1 = new UserProfile("Browsing");
		userProfile1.setOperationPercentage("browsing", 95.0);
		userProfile1.setOperationPercentage("ordering", 5.0);
		UserProfile userProfile2 = new UserProfile("Shopping");
		userProfile2.setOperationPercentage("browsing", 80.0);
		userProfile1.setOperationPercentage("ordering", 20.0);
		
		this.profileList.add(userProfile1);
		this.profileList.add(userProfile2);
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
