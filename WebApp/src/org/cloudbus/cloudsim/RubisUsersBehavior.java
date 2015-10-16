package org.cloudbus.cloudsim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;

public class RubisUsersBehavior implements UsersBehavior {

	private static Rengine re;

	private List<UserSession> usersSessionList;

	private ArrayList<Integer> timeList;

	private int previousTime;

	private int sessionCounter;

	private int reqCounter;

	private int timeCounter;

	private ArrayList<Integer> userBehavior = new ArrayList<Integer>();

	public RubisUsersBehavior(int usersNumber, int usersArrivalRate,
			String userProfile) {

		this.sessionCounter = 0;
		this.timeCounter = 0;
		this.previousTime = 0;
		this.reqCounter = 0;
		this.usersSessionList = new ArrayList<UserSession>();
		this.timeList = new ArrayList<Integer>();

		for (int i = 0; i < usersNumber; i++) {
			generateUserBehavior(usersArrivalRate, userProfile);
			generateUsersSessionList(i, this.userBehavior);
		}

		generateStartTimeList((ArrayList<UserSession>) this.usersSessionList);

	}
	
	static {
		// new R-engine
		re = new Rengine(new String[] { "--vanilla" }, false, null);
		if (!re.waitForR()) {
			System.out.println("Cannot load R");
			re = null;
		} else {
			re.eval("source(\"rubisGenerateUsersBehavior.R\")");
			re.eval("library('fExtremes')");
		}
	}

	public void generateUserBehavior(int usersArrivalRate, String userProfile) {
		if (re == null) {
			System.out
					.println("Error: Trying to generate user behavior with null Rengine!");
			return;
		}

		String cmd = String.format("generateUsersBehaviour(\"%s\")",
				userProfile);
		REXP ret = null;
		synchronized (re) {
			ret = re.eval(cmd);
		}
		if(ret == null) {
			System.out.println("Unable to call generateUserBehaviour on Rengine.");
			return ;
		}
		double[] arrayBehavior = ret.asDoubleArray();
		for (double behavior : arrayBehavior) {
			this.userBehavior.add((int) behavior);
		}
		// re.end();
	}

	public void generateUsersSessionList(int sessionID,
			List<Integer> cloudletSizes) {

		UserSession userSession = new UserSession(sessionID, cloudletSizes);
		this.usersSessionList.add(userSession);

	}


	public UserSession nextSession(double currentTime) {
		UserSession session = null;
		if (sessionCounter < usersSessionList.size()) {
			session = usersSessionList.get(sessionCounter);
			sessionCounter++;
		}

		return session;
	}
	
	public Requisition nextRequisition(double currentTime, UserSession userSession) {
		Requisition requisition = null;
		if (reqCounter < userSession.getRequisitions().size()) {
			requisition = userSession.getRequisitions().get(reqCounter);
			reqCounter++;
		}

		return requisition;
	}

	public double delayToNextEvent(double currentTime) {
		double time = -1.0;
		if (timeCounter < timeList.size()) {
			time = timeList.get(timeCounter) - previousTime;
			this.previousTime = timeList.get(timeCounter);
			timeCounter++;
		}

		return time;
	}

	public List<UserSession> getUsersSessionList() {
		return usersSessionList;
	}

	@Override
	public void generateStartTimeList(ArrayList<UserSession> userSessionList) {
		for (UserSession userSession : usersSessionList) {

			for (Requisition requisition : userSession.getRequisitions()) {
				if (userSession.getRequisitions().size() > 0) {
					this.timeList.add(requisition.getArrivalTime());
				} else {
					this.previousTime = requisition.getArrivalTime();
				}
			}
		}

		// sort the timeList by requisitions start time
		Collections.sort(this.timeList, new Comparator<Integer>() {
			public int compare(Integer a, Integer b) {
				return a - b;
			}
		});

	}

}
