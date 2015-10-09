package org.cloudbus.cloudsim;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.power.PowerDatacenter;
import org.cloudbus.cloudsim.power.PowerHost;
import org.cloudbus.cloudsim.power.models.PowerModelSpecPower_BAZAR;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;
import org.cloudbus.cloudsim.xml.DvfsDatas;


public class Experiment1 {
	
	private static DvfsDatas ConfigDvfs;
	
	private static ArrayList<Double> freqs = new ArrayList<>(); // frequencies available by the CPU
	
	public static void main(String[] args) {
		

		try {
			Calendar calendar = Calendar.getInstance();
			boolean trace_flag = false;

			int usersNumber = Integer.parseInt(ExperimentProperties.USERS_NUMBER.getProperty());
			CloudSim.init(usersNumber, calendar, trace_flag,0.0005);
			int localHosts = Integer.parseInt(InfrastructureProperties.DATACENTER_HOSTS.getProperty());

			PowerDatacenter datacenter = createDatacenter("Cloud",localHosts);
			String appName = ExperimentProperties.APP_NAME.getProperty();
			String userProfile = ExperimentProperties.USER_PROFILE.getProperty();
			int usersArrivalRate = Integer.parseInt(ExperimentProperties.USERS_ARRIVAL_RATE.getProperty());
			Broker broker = new Broker("Broker",appName,usersNumber,usersArrivalRate, userProfile, datacenter, freqs);

			CloudSim.startSimulation();
			broker.printExecutionSummary();
			
			printSimulationProperties();

			Log.printLine("Experiment finished!");
		} catch (Exception e) {
			Log.printLine("Unwanted errors happen.");
			e.printStackTrace();
		}
	}
	
	private static PowerDatacenter createDatacenter(String name, int hosts) throws Exception {

		// HOST PROPERTIES
		double maxPower = Double.parseDouble(InfrastructureProperties.MAXPOWER_PERHOST.getProperty()); // Watts
		double staticPowerPercent = Double.parseDouble(InfrastructureProperties.STATICPOWERPERCENT_PERHOST.getProperty()); // (%)
		int ram = Integer.parseInt(InfrastructureProperties.MEMORY_PERHOST.getProperty());
		int cores = Integer.parseInt(InfrastructureProperties.CORES_PERHOST.getProperty());
		int mips = Integer.parseInt(InfrastructureProperties.MIPS_PERCORE.getProperty());
		long storage = Long.parseLong(InfrastructureProperties.STORAGE_PERHOST.getProperty());
		int bw = Integer.parseInt(InfrastructureProperties.BANDWIDTH_PERHOST.getProperty());
		
		boolean enableDVFS = true; // is the Dvfs enable on the host
		
		freqs.add(25.00);// frequencies are defined in % , it make free to use Host MIPS like we want.
		freqs.add(50.00);  // frequencies must be in increase order !
		freqs.add(75.00);
		freqs.add(100.0);
		
		HashMap<Integer,String> govs = new HashMap<Integer,String>();  // Define wich governor is used by each CPU
		for (int i = 0; i < cores; i++) {
			govs.put(i,"userspace");  // All CPUs use UserSpace DVFS mode
		}

		List<PowerHost> hostList = new ArrayList<PowerHost>();
		for (int i = 0; i < hosts; i++) {
			
			ConfigDvfs = new DvfsDatas();
			HashMap<String,Integer> tmp_HM_UserSpace = new HashMap<>();
			tmp_HM_UserSpace.put("frequency",3);
			ConfigDvfs.setHashMapUserSpace(tmp_HM_UserSpace);
			
			List<Pe> peList = new ArrayList<Pe>();
			for (int j = 0; j < cores; j++) {
				peList.add(new Pe(j, new PeProvisionerSimple(mips), freqs, govs.get(j), ConfigDvfs));
			}

			hostList.add(new PowerHost(
					i,
					new RamProvisionerSimple(ram),
					new BwProvisionerSimple(bw),
					storage,
					peList,
					new VmSchedulerTimeShared(peList),
					new PowerModelSpecPower_BAZAR(peList),false,
					enableDVFS));
		}

		// DATACENTER PROPERTIES
		String arch = InfrastructureProperties.DATACENTER_ARCH.getProperty();
		String os = InfrastructureProperties.DATACENTER_OS.getProperty();
		String vmm = InfrastructureProperties.VMM_VM.getProperty();
		double time_zone = Double.parseDouble(InfrastructureProperties.DATACENTER_TIMEZONE.getProperty());
		double cost = Double.parseDouble(InfrastructureProperties.DATACENTER_COST.getProperty());
		double costPerMem = Double.parseDouble(InfrastructureProperties.DATACENTER_COSTPERMEM.getProperty());
		double costPerStorage = Double.parseDouble(InfrastructureProperties.DATACENTER_COSTPERSTORAGE.getProperty());
		double costPerBw = Double.parseDouble(InfrastructureProperties.DATACENTER_COSTPERBW.getProperty());
		LinkedList<Storage> storageList = new LinkedList<Storage>();
		double schedulingInterval = Double.parseDouble(InfrastructureProperties.DATACENTER_SCHEDINTERVAL.getProperty());

		DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
				arch,
				os,
				vmm,
				hostList,
				time_zone,
				cost,
				costPerMem,
				costPerStorage,
				costPerBw);

		return new PowerDatacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, schedulingInterval);
	}
	
	private static void printSimulationProperties() {
		Log.printLine("========== Experiment configuration ==========");
		for (ExperimentProperties property: ExperimentProperties.values()){
			Log.printLine("= "+property+": "+property.getProperty());
		}
		Log.printLine("==============================================");
		Log.printLine("");
	}

}
