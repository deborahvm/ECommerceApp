package org.cloudbus.cloudsim;

import java.util.ArrayList;
import java.util.List;

public interface UsersBehavior {
	
	public void generateUserBehavior(int usersArrivalRate, String userProfile);
	
	public void generateUsersSessionList(int sessionID, List<Integer> cloudletSizes);
		
	public void generateStartTimeList(ArrayList<UserSession> userSessionList);
	
	public UserSession nextSession(double currentTime);
	
	public Requisition nextRequisition(double currentTime, UserSession userSession);
	
	public double delayToNextEvent(double currentTime);
	
	public List<UserSession> getUsersSessionList();

}
