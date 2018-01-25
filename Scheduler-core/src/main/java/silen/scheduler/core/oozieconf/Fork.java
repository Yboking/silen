package silen.scheduler.core.oozieconf;

public class Fork {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	Path[] path;

	public Path[] getPath() {
		return path;
	}

	public void setPath(Path[] paths) {
		this.path = paths;
	}

}

class Path {

	private String start;

	public Path() {
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}
}
