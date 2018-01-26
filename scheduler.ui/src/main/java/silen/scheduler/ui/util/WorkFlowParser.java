package silen.scheduler.ui.util;

import java.util.HashSet;
import java.util.Set;

import silen.scheduler.core.oozieconf.OAction;
import silen.scheduler.core.oozieconf.OWorkFlow;
import silen.scheduler.core.oozieconf.Spark;

public class WorkFlowParser {

	
	OWorkFlow owf = null;
	
	
	Set<String> nodeSet = new HashSet<String>();
	public NativeWorkFlow toNativeWorkFlow(OWorkFlow owf){
		NativeWorkFlow nwf = null;
		
		OAction[] actions = owf.getAction();
		
		for(OAction action : actions){
			
			
			int source = createNodeIndex(action.getName());
			
			int target = createNodeIndex(action.getOk().getTo());
			
			Spark spark = action.getSpark(); 
			
			
			String[] actionArgs = spark.getArg();
			
			
			
		}
		
		return nwf;
		
		
		
		
	}
	private int createNodeIndex(String name) {
		// TODO Auto-generated method stub
		
		
		return 0;
	}
}



class NativeWorkFlow{
	
	
	
	
}
