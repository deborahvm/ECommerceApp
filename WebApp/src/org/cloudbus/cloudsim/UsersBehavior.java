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
