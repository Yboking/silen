package silen.scheduler.ui.oozieconf;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Spark {
	private String xmlns;
	@JsonProperty(value = "job-tracker")
	private String jobTracker;

	@JsonProperty(value = "name-node")
	private String nameNode;

	
	private Conf configuration;
	public Conf getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Conf configuration) {
		this.configuration = configuration;
	}

	private String master;
	private String name;
	@JsonProperty(value = "class")
	private String classname;

	public String getXmlns() {
		return xmlns;
	}

	public void setXmlns(String xmlns) {
		this.xmlns = xmlns;
	}

	public String getJobTracker() {
		return jobTracker;
	}

	public void setJobTracker(String jobTracker) {
		this.jobTracker = jobTracker;
	}

	public String getNameNode() {
		return nameNode;
	}

	public void setNameNode(String nameNode) {
		this.nameNode = nameNode;
	}


	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getJar() {
		return jar;
	}

	public void setJar(String jar) {
		this.jar = jar;
	}

	public String getSparkOpts() {
		return sparkOpts;
	}

	public void setSparkOpts(String sparkOpts) {
		this.sparkOpts = sparkOpts;
	}

	public String[] getArg() {
		return arg;
	}

	public void setArg(String[] args) {
		this.arg = args;
	}

	private String jar;
	@JsonProperty(value = "spark-opts")
	private String sparkOpts;
	private String[] arg;

}

class Conf{
	
	Property[] property;

	public Property[] getProperty() {
		return property;
	}

	public void setProperty(Property[] properties) {
		this.property = properties;
	}
			
}