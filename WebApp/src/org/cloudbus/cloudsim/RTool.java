package org.cloudbus.cloudsim;
import java.io.File;
import java.lang.reflect.Field;

import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;


public class RTool {
	private Rengine rEngine;
	
	public RTool() {
		//configRLibOnJava();
		rEngine = new Rengine(new String[] { "--vanilla" }, false, null);		
	}

	private void configRLibOnJava(){
		System.setProperty("java.library.path", "/home/deborah/R/x86_64-pc-linux-gnu-library/2.15/rJava/jri/" );
		 Field fieldSysPath;
		try {
			fieldSysPath = ClassLoader.class.getDeclaredField( "sys_paths" );
			 fieldSysPath.setAccessible( true );
			 fieldSysPath.set( null, null );
		} catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public REXP callScript(String fileName,String company, String location, String networkType, String streamDataDirection){
		//String scriptPath = this.getClass().getResource("/r/"+fileName).getPath();
		REXP rExp  = null;
		//scriptPath=scriptPath.substring(1,scriptPath.length());
		//scriptPath= scriptPath.replace("/", "\\\\");
		
		rExp = rEngine.eval("source(\"networkRandomGenerator.R\")");
		rExp =rEngine.eval("library(\"fExtremes\")");
		rExp =rEngine.eval("rgev(1, xi = -0.86253, mu = 28.825 , beta =3.7849)");
		rExp =rEngine.eval("rlnorm3(1,shape=0.56169,scale=3.2526,thres=42.952)");
		System.out.println(rExp);
		//rExp = rEngine.eval("source(\""+scriptPath+"\")");
		
		//rExp =rEngine.eval("networkRandomGenerator(\""+company+"."+location+ "."+networkType+"."+streamDataDirection+ "\")");
		
		return rExp;
	}
	
	public boolean initRPackage(){
		return rEngine.waitForR();
	}
	
	public REXP executeCommand(String cmd){
		REXP ret = null;
		if(rEngine!=null){
			synchronized (rEngine) {
				ret = rEngine.eval(cmd);
			}
		}
		return ret;
		
	}
	
	public void finalizeRPackageExecution(){
		rEngine.end();
	}
	public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		RTool rTool = new RTool();
		System.out.println("init R package: "+rTool.initRPackage());
			
		System.out.println(rTool.executeCommand("1+1"));
		
		rTool.callScript("networkRandomGenerator.R", "oi", "aero", "3g","up");
		//rTool.testParametersCombination();
		
		//System.out.println(rTool.executeCommand("mi.teo <- rcauchy(1, location = 134.66, scale = 5.3831)"));
		
				//System.out.println(rTool.executeCommand("mi.teo <-rlnorm3(1,shape=0.56169,scale=3.2526,thres=42.952)"));
		
		rTool.finalizeRPackageExecution();
	}
	
	public void testParametersCombination(){
		String companies[] = new String[]{"oi","tim","claro","vivo"};
		String locations[] =  new String[]{"pici","aero"};
		String networkTypes[] = new String[]{"3g","4g"};
		String dataStreamDirections[] = new String[]{"up","down"};
		for (String company : companies) {
			for (String location : locations) {
				for (String networkType : networkTypes) {
					for (String dataStreamDirection : dataStreamDirections) {
						System.out.print("parametros: " + company+" "+location+" " +networkType+" "+dataStreamDirection);
						System.out.println(" - " +callScript("networkRandomGenerator.R",company,location,networkType,dataStreamDirection));
					}
				}
				
			}
		}
		
		
		
	}
}
