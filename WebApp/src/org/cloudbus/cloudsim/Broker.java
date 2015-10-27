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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.CloudSimTags;
import org.cloudbus.cloudsim.core.SimEntity;
import org.cloudbus.cloudsim.core.SimEvent;
import org.cloudbus.cloudsim.lists.VmList;
import org.cloudbus.cloudsim.power.PowerDatacenter;
import org.cloudbus.cloudsim.power.PowerHost;
import org.cloudbus.cloudsim.power.dvfs.UserSpaceGovernor;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;

public class Broker extends SimEntity {

	private static final int PRINT_VM_TOTAL_CPU_UTILIZATION = 50;
	private static final int CHECK_REQUISITION_SUBMITION = 51;
	int cloudId = -1;
	int clId = 0;
	boolean running = false;

	protected DatacenterCharacteristics characteristics;

	int mips = Integer.parseInt(InfrastructureProperties.MIPS_PERCORE
			.getProperty());

	protected List<Vm> vmsRequestedList;
	protected List<Vm> availableVms;
	protected List<UserSession> submittedSessionsList;
	protected List<UserSession> pendingSessionsList;
	protected List<Integer> newData;

	protected long totalSession;
	protected long pendingRequisitions;
	protected long submittedSessions;
	protected boolean catchingSession;
	protected int vmIndex;
	protected long totalRequisition;

	ECommerceApp eCommerceApp;

	protected String appName;
	protected int usersNumber;
	protected int usersArrivalRate;
	protected String userProfile;
	protected int count;
	protected UserSpaceGovernor governor;
	protected int predictionSampleSize;
	protected PowerDatacenter datacenter;
	protected ArrayList<Double> freqs;

	public Broker(String name, String appName, int usersNumber,
			int usersArrivalRate, String userProfile, PowerDatacenter datacenter, ArrayList<Double> freqs) {
		super(name);
		this.vmsRequestedList = new ArrayList<Vm>();
		this.availableVms = new ArrayList<Vm>();
		this.submittedSessionsList = new ArrayList<UserSession>();
		this.pendingSessionsList = new ArrayList<UserSession>();
		this.newData = new ArrayList<Integer>();

		this.totalSession = 0;
		this.pendingRequisitions = 0;
		this.submittedSessions = 0;
		this.catchingSession = true;
		this.vmIndex = 0;
		this.totalRequisition = 0;
		this.count = 1;

		this.appName = appName;
		this.usersNumber = usersNumber;
		this.usersArrivalRate = usersArrivalRate;
		this.userProfile = userProfile;
		this.datacenter = datacenter;
		this.freqs = freqs;

		eCommerceApp = new ECommerceApp(this.appName, this.usersNumber,
				this.usersArrivalRate, this.userProfile);

	}

	@Override
	public int getId() {
		return super.getId();
	}

	public int getMips() {
		return mips;
	}

	@Override
	public void processEvent(SimEvent ev) {
		switch (ev.getTag()) {
		case CloudSimTags.RESOURCE_CHARACTERISTICS_REQUEST:
			processResourceCharacteristicsRequest(ev);
			break;
		case CloudSimTags.RESOURCE_CHARACTERISTICS:
			processResourceCharacteristics(ev);
			break;
		case CloudSimTags.VM_CREATE_ACK:
			processVmCreate(ev);
			break;
		case CloudSimTags.CLOUDLET_RETURN:
			processCloudletReturn((Cloudlet) ev.getData());
			break;
		case CloudSimTags.VM_BROKER_EVENT:
			processInternalEvent();
			break;
		case CloudSimTags.END_OF_SIMULATION:
			shutdownEntity();
			break;
		case PRINT_VM_TOTAL_CPU_UTILIZATION:
			printVmTotalCpuUtilization();
			break;
		case CHECK_REQUISITION_SUBMITION:
			checkRequistionSubmition();
			break;
		default:
			processOtherEvent(ev);
		}
	}

	private void checkRequistionSubmition() {
		int inputLayerSize = Integer
				.parseInt(ExperimentProperties.INPUT_LAYER_SIZE.getProperty());
		double delay = 1.0;

		predictionSampleSize = Integer
				.parseInt(ExperimentProperties.PREDICTION_SIZE.getProperty());

		Map<Integer, List<Double>> predictedSum = new HashMap<Integer, List<Double>>();
		if (count > inputLayerSize) {
			for (UserSession session : submittedSessionsList) {
				int vmId = session.getRequisitions().get(0).getCloudlet()
						.getVmId();
				List<Integer> requisitionsSize = new ArrayList<Integer>(
						Collections.nCopies(count, 0));
				for (Requisition requisition : session.getRequisitions()) {
					if (requisition.getArrivalTime() <= count) {
						requisitionsSize.set(requisition.getArrivalTime() - 1,
								requisition.getSize());
					}
				}
				List<Double> predicted = predictCPUUtilization(
						requisitionsSize, vmId);
				if (predicted != null) {
					List<Double> predictedSumVm = predictedSum.get(vmId);
					if (predictedSumVm == null) {
						predictedSumVm = new ArrayList<Double>(
								Collections.nCopies(predictionSampleSize, 0.0));
					}
					for (int i = 0; i < predicted.size(); i++) {
						Double sum = predictedSumVm.get(i) + predicted.get(i);
						predictedSumVm.set(i, sum);
					}
					predictedSum.put(vmId, predictedSumVm);
				}
			}
			
			Double maxpredictedValue = 0.0;
			List<Double> predictedValues;
	        Set<Integer> keys = predictedSum.keySet();  
	        for (Integer key : keys)  
	        {  
	        	predictedValues = predictedSum.get(key);
	        	maxpredictedValue = Collections.max(predictedValues); 
	        	
	        	Host host = null;
				for (Vm vm : datacenter.getVmList()) {
						if(vm.getId() == key){
							host = vm.getHost();
							break;
						}
				
				}
	        
				Double currentFreq = (double) host.getPeList().get(0).getIndexFreq();
				
				for (Double freq : freqs) {
					if ((maxpredictedValue <= freq) && (currentFreq != freq)){
						// set the processor frequency according to the percentage of VMs CPU
						// utilization
						for (Pe pe : host.getPeList()) {
							pe.setIndexFreq(0);
						}
						
						// This call the method applyDvfsOnHost on HostDynamicWork, which
						// resizes VMs according to new Pe available mips.
						PowerHost ph = (PowerHost) host;
						ph.isDvfsActivatedOnHost();	
					
						break;
					}
				}
	        } 
			/*
			// set the processor frequency according to the percentage of VMs CPU
			// utilization
				for (Host host : datacenter.getHostList()) {
					for (Pe pe : host.getPeList()) {
						pe.setIndexFreq(0);
						System.out.println("Freq do host: " + pe.getIndexFreq());
					}
					// This call the method applyDvfsOnHost on HostDynamicWork, which
					// resizes VMs according to new Pe available mips.
					PowerHost ph = (PowerHost) host;
					ph.isDvfsActivatedOnHost();
				}	*/
		}
		

		count++;
		if (running) {
			send(getId(), delay, CHECK_REQUISITION_SUBMITION);
		}
	}

	private static int[] toIntArray(List<Integer> list) {
		int[] ret = new int[list.size()];
		for (int i = 0; i < ret.length; i++)
			ret[i] = list.get(i);
		return ret;
	}

	private static List<Double> toDoubleList(double[] arr) {
		List<Double> list = new ArrayList<Double>();
		for (int i = 0; i < arr.length; i++) {
			list.add(arr[i]);
		}
		return list;
	}

	public List<Double> predictCPUUtilization(List<Integer> newData, int vmId) {
		Rengine re = Rengine.getMainEngine();
		if (re == null) {
			System.out
					.println("Error: Trying to generate user behavior with null Rengine!");
			return null;
		}
		re.eval("source(\"predictModelElman.R\")");

		String newDataStr = "newData";
		String cmdFormat = "predictModelElman(%s, \"%s\", %d)";
		String cmd = String.format(cmdFormat, newDataStr, userProfile,
				predictionSampleSize);
		re.assign(newDataStr, toIntArray(newData));
		REXP ret = null;
		synchronized (re) {
			ret = re.eval(cmd);
		}
		if (ret == null) {
			System.out
					.println("Unable to call generateUserBehaviour on Rengine.");
			return null;
		}

		double[] outputPredictedArray = ret.asDoubleArray();
		List<Double> outputPredicted = toDoubleList(outputPredictedArray);

		// convert number of instructions in CPU freq
		for (Host host : this.datacenter.getHostList()) {
			for (Vm vm : host.getVmList()) {
				if (vm.getId() == vmId) {
					for (int i = 0; i < outputPredicted.size(); i++) {
						Double value = (outputPredicted.get(i) * 100.0)
								/ vm.getMaxMips();
						outputPredicted.set(i, value);
					}
				}
			}
		}

		return outputPredicted;
	}

	private void printVmTotalCpuUtilization() {
		for (Vm vm : vmsRequestedList) {
			Log.print(String.format("%9.5f ",
					vm.getTotalUtilizationOfCpu(CloudSim.clock())));
		}
		Log.printLine();
	}

	protected void processOtherEvent(SimEvent ev) {
		Log.printLine(getName() + ".processOtherEvent(): "
				+ "Error - unknown event received. Tag=" + ev.getTag());
	}

	protected void processResourceCharacteristicsRequest(SimEvent ev) {
		cloudId = CloudSim.getCloudResourceList().get(0);
		sendNow(cloudId, CloudSimTags.RESOURCE_CHARACTERISTICS, getId());
	}

	protected void processResourceCharacteristics(SimEvent ev) {
		characteristics = (DatacenterCharacteristics) ev.getData();
		createVmsInDatacenter();
	}

	protected void processVmCreate(SimEvent ev) {
		int[] data = (int[]) ev.getData();
		int datacenterId = data[0];
		int vmId = data[1];
		int result = data[2];

		Vm vm = VmList.getById(this.vmsRequestedList, vmId);

		if (result == CloudSimTags.TRUE) {
			Log.printLine(CloudSim.clock() + ": " + getName() + ": VM #"
					+ vm.getId() + " has been created on Host #"
					+ vm.getHost().getId());
			availableVms.add(vm);
			if (availableVms.size() == vmsRequestedList.size()) {// VMs are
																	// ready;
																	// start
																	// execution
				catchLoad();
				if (totalSession <= eCommerceApp.getUsersBehavior()
						.getUsersSessionList().size()) {
					submitSessions();
				}
			}
		} else {
			Log.printLine(CloudSim.clock() + ": " + getName()
					+ ": Creation of VM #" + vmId + " failed in Datacenter #"
					+ datacenterId);
		}
	}

	protected void processCloudletReturn(Cloudlet cloudlet) {
		this.pendingRequisitions--;
		// If all cloudlets were executed
		if (pendingRequisitions == 0) {
			Log.printLine(CloudSim.clock() + ": " + getName()
					+ ": All requests executed. Finishing...");
			finishExecution();
		}
	}

	protected void createVmsInDatacenter() {
		int mips = Integer.parseInt(InfrastructureProperties.MIPS_PERCORE
				.getProperty());
		int pes = 1;
		int pesPerHost = Integer
				.parseInt(InfrastructureProperties.CORES_PERHOST.getProperty());
		int ramPerHost = Integer
				.parseInt(InfrastructureProperties.MEMORY_PERHOST.getProperty());
		int bwPerHost = Integer
				.parseInt(InfrastructureProperties.BANDWIDTH_PERHOST
						.getProperty());
		int ram = ramPerHost / pesPerHost;
		int bw = bwPerHost / pesPerHost;
		int storage = Integer.parseInt(InfrastructureProperties.STORAGE_PERVM
				.getProperty());
		String vmm = InfrastructureProperties.VMM_VM.getProperty();
		// int vms = characteristics.getNumberOfPes();
		int vms = Integer.parseInt(InfrastructureProperties.VMS_NUMBER
				.getProperty());

		for (int i = 0; i < vms; i++) {
			Vm vm = new Vm(i, this.getId(), mips, pes, ram, bw, storage, vmm,
					new CloudletSchedulerTimeShared());
			this.vmsRequestedList.add(vm);

			sendNow(cloudId, CloudSimTags.VM_CREATE_ACK, vm);
		}

	}

	protected void processInternalEvent() {
		catchLoad();
		if (totalSession <= eCommerceApp.getUsersBehavior()
				.getUsersSessionList().size()) {
			submitSessions();
		}
	}

	protected void catchLoad() {
		if (catchingSession) {
			double currentTime = CloudSim.clock();
			UserSession nextSession = eCommerceApp.getUsersBehavior()
					.nextSession(currentTime);
			if (nextSession == null) {
				return;
			}
			pendingSessionsList.add(nextSession);
			this.totalSession++;
			this.pendingRequisitions += nextSession.getRequisitions().size();

			double delay = eCommerceApp.getUsersBehavior().delayToNextEvent(
					currentTime);
			if (totalSession <= eCommerceApp.getUsersBehavior()
					.getUsersSessionList().size()) {
				send(getId(), delay, CloudSimTags.VM_BROKER_EVENT);
			} else {
				catchingSession = false;
			}
		}
	}

	public void submitSessions() {
		for (UserSession session : pendingSessionsList) {
			Vm vm = null;
			for (Requisition requisition : session.getRequisitions()) {
				// if user didn't bind this cloudlet and it has not been
				// executed yet
				if (requisition.getCloudlet().getVmId() == -1) {
					vm = availableVms.get(vmIndex);
				}

				if (!submittedSessionsList.contains(session)) {
					requisition.getCloudlet().setVmId(vm.getId());
					requisition.getCloudlet().setUserId(getId());
					send(cloudId, requisition.getArrivalTime(),
							CloudSimTags.CLOUDLET_SUBMIT,
							requisition.getCloudlet());
					send(getId(), requisition.getArrivalTime() + 0.1,
							PRINT_VM_TOTAL_CPU_UTILIZATION);
				}
			}
			submittedSessionsList.add(session);
			submittedSessions++;
			if (vmIndex < Integer.parseInt(InfrastructureProperties.VMS_NUMBER
					.getProperty())) {
				vmIndex++;
			}

		}

		// remove submitted sessions from pending list
		for (UserSession session : submittedSessionsList) {
			pendingSessionsList.remove(session);
		}
	}

	public void printExecutionSummary() {
		String header = "SESSION ID | Session Start | REQUISITION ID | REQUISITION STATUS | VM ID | Arrival Time | Size | Submission Time | Finish Time | Runtime";
		String fmt = "%10d | %13d | %14d | %18s | %5d | %12d | %4d | %15.2f | %11.2f | %7.4f";
		Log.printLine(header);

		List<Cloudlet> cloudlets = new ArrayList<Cloudlet>();
		Map<String, Requisition> reqMap = new HashMap<String, Requisition>();
		Map<String, UserSession> sesMap = new HashMap<String, UserSession>();

		for (UserSession session : submittedSessionsList) {
			for (Requisition requisition : session.getRequisitions()) {
				Cloudlet cloudlet = requisition.getCloudlet();

				cloudlets.add(requisition.getCloudlet());
				cloudlet.setUserId(session.getSessionID());
				String cid = String.format("%dx%d", cloudlet.getUserId(),
						cloudlet.getCloudletId());
				reqMap.put(cid, requisition);
				sesMap.put(cid, session);
			}
		}

		Collections.sort(cloudlets, new Comparator<Cloudlet>() {
			public int compare(Cloudlet a, Cloudlet b) {
				double diff = a.getSubmissionTime() - b.getSubmissionTime();
				return (int) (diff * 100);
			}
		});

		for (Cloudlet cloudlet : cloudlets) {
			String cid = String.format("%dx%d", cloudlet.getUserId(),
					cloudlet.getCloudletId());
			Requisition requisition = reqMap.get(cid);
			UserSession session = sesMap.get(cid);
			double serviceTime = cloudlet.getFinishTime()
					- cloudlet.getSubmissionTime();
			Log.printLine(String.format(fmt, session.getSessionID(),
					session.getSessionStartTime(), cloudlet.getCloudletId(),
					Cloudlet.getStatusString(cloudlet.getStatus()),
					cloudlet.getVmId(), requisition.getArrivalTime(),
					requisition.getSize(), cloudlet.getExecStartTime(),
					cloudlet.getFinishTime(), serviceTime));
		}

		Log.printLine();

		Log.printLine("========== BROKER SUMMARY ==========");
		Log.printLine("= Submitted Sessions: " + this.submittedSessions);
		Log.printLine("========== END OF SUMMARY =========");
	}

	protected void finishExecution() {
		sendNow(getId(), CloudSimTags.END_OF_SIMULATION);
	}

	@Override
	public void shutdownEntity() {
		Log.printLine(getName() + " is shutting down...");
		running = false;
	}

	@Override
	public void startEntity() {
		Log.printLine(getName() + " is starting...");
		schedule(getId(), 2, CloudSimTags.RESOURCE_CHARACTERISTICS_REQUEST);
		running = true;
		send(getId(), 0.1, CHECK_REQUISITION_SUBMITION);
	}
}