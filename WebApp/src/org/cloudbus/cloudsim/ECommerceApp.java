package org.cloudbus.cloudsim;

public class ECommerceApp implements WebApp {
	
	public static final String RUBIS = "Rubis";
	private String appName;
	private int usersNumber;
	private AppProfiles appProfiles;
	private UsersBehavior usersBehavior;
	private String userProfile;
	
	public ECommerceApp(String appName, int usersNumber, int usersArrivalRate, String userProfile){
		this.appName = appName;
		this.usersNumber = usersNumber;
		if (this.appName.equals(RUBIS)){
				this.appProfiles = new RubisProfiles();
				this.usersBehavior = new RubisUsersBehavior(usersNumber, usersArrivalRate, userProfile);
		}
		this.setUserProfile(userProfile);
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public int getUsersNumber() {
		return usersNumber;
	}

	public void setUsersNumber(int usersNumber) {
		this.usersNumber = usersNumber;
	}

	public AppProfiles getAppProfiles() {
		return appProfiles;
	}

	public void setAppProfiles(AppProfiles appProfiles) {
		this.appProfiles = appProfiles;
	}

	public UsersBehavior getUsersBehavior() {
		return usersBehavior;
	}

	public void setUsersBehavior(UsersBehavior usersBehavior) {
		this.usersBehavior = usersBehavior;
	}

	public String getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}
	
	

}
