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

public class UserSession {

	private int userID;

	private int sessionID;

	private int sessionStartTime;

	private int sessionLength;

	/*
	 * List of cloudlets (requisitions) of the session. Each cloudlet
	 * (requisition) has a arrival time and a size in number of instructions.
	 */
	private List<Requisition> requisitions;


	public UserSession(int sessionID, List<Integer> cloudletSizes) {
		this.sessionID = sessionID;
		this.sessionStartTime = 0;
		this.sessionLength = cloudletSizes.size();
		
		requisitions = new ArrayList<Requisition>();
		
		int count = 0;
		for (int i = 0; i < cloudletSizes.size(); i++) {
			if (cloudletSizes.get(i) > 0){
				requisitions.add(new Requisition(count,i, cloudletSizes.get(i)));
				count++;
			}
		}
	}

	public int getUserID() {
		return userID;
	}

	public int getSessionID() {
		return sessionID;
	}

	public int getSessionStartTime() {
		return sessionStartTime;
	}

	public int getSessionLength() {
		return sessionLength;
	}
	
	public List<Requisition> getRequisitions() {
		return requisitions;
	}

	public void setRequisitions(List<Requisition> requisitions) {
		this.requisitions = requisitions;
	}

}
