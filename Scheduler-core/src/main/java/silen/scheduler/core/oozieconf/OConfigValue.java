package silen.scheduler.core.oozieconf;

import java.util.HashMap;
import java.util.Map;

public class OConfigValue {

	private String jobname;

	private Property[] property;

	public Property[] getProperty() {
		return property;
	}

	public void setProperty(Property[] property) {
		this.property = property;
	}

	public String getJobname() {
		return jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	private Map<String, String> config;

	public String getArgValue(String arg) throws Exception {

		config = new HashMap<String, String>();

		for (Property pro : property) {

			config.put(pro.getName(), pro.getValue());
		}

		String name = arg;
		if (name.matches("\\$\\{.+\\}")) {
			name = name.substring(2, name.length() - 1);
		}

		name = config.get(name);

		return name;

	}
}
