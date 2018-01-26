package silen.scheduler.utils.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import silen.scheduler.core.oozieconf.Join;
import silen.scheduler.core.oozieconf.OAction;
import silen.scheduler.core.oozieconf.OWorkFlow;
import silen.scheduler.core.oozieconf.Spark;
import silen.scheduler.data.job.TaskDesc;



 class CopyOfWorkFlowParser {

	OWorkFlow owf = null;

	public CopyOfWorkFlowParser(OWorkFlow owf) {
		this.owf = owf;
	}

	
//	scala.collection.mutable.HashMap<String, int> nodeMap = 
	
//	Set<String> nodeSet = new HashSet<String>();

	HashMap<String, String> joinPoints;

	public NativeWorkFlow toNativeWorkFlow() {

		OAction[] actions = owf.getAction();

		List<TaskDesc> tasks = new ArrayList<TaskDesc>();

		initJoints();

		for (OAction action : actions) {

			int source = createNodeIndex(action.getName());

			int target = createNodeIndex(action.getOk().getTo());

			Spark spark = action.getSpark();

			String[] actionArgs = spark.getArg();

			TaskDesc taskDesc = new TaskDesc(actionArgs, source, target,
					action.getName());
			tasks.add(taskDesc);

		}

		return new NativeWorkFlow(tasks.toArray(new TaskDesc[] {}));

	}

	private void initJoints() {

		joinPoints = new HashMap<String, String>();

		for (Join join : owf.getJoin()) {

			joinPoints.put(join.getName(), join.getTo());

		}
	}

	private int createNodeIndex(String name) {
		// TODO Auto-generated method stub

		String newname = getJoinPoint(name);

		return 0;
	}

	private String getJoinPoint(String name) {

		String tmpname = null;

		while (joinPoints.get(tmpname) != null) {

			tmpname = joinPoints.get(tmpname);

		}
		return tmpname;
	}
}

