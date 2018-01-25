package silen.scheduler.utils.runtime;

import silen.scheduler.data.job.TaskDesc;

public class NativeWorkFlow {

	private TaskDesc[] tasks = null;

	public NativeWorkFlow(TaskDesc[] tasks) {
		this.tasks = tasks;
	}

	public TaskDesc[] getTasks() {
		return tasks;
	}

	public void setTasks(TaskDesc[] tasks) {
		this.tasks = tasks;
	}
}
