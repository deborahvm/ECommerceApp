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
