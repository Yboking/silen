package silen.scheduler.ui.oozieconf;

public class OWorkFlow {

	private String name;

	private StartPoint start;

	private Fork[] fork;

	private Join[] join;

	private OAction[] action;

	private Kill kill;

	private EndPoint end;

	public Kill getKill() {
		return kill;
	}

	public void setKill(Kill kill) {
		this.kill = kill;
	}

	public EndPoint getEnd() {
		return end;
	}

	public void setEnd(EndPoint end) {
		this.end = end;
	}

	public StartPoint getStart() {
		return start;
	}

	public void setStart(StartPoint start) {
		this.start = start;
	}

	public Fork[] getFork() {
		return fork;
	}

	public void setFork(Fork[] fork) {
		this.fork = fork;
	}

	public Join[] getJoin() {
		return join;
	}

	public void setJoin(Join[] join) {
		this.join = join;
	}

	public OAction[] getAction() {
		return action;
	}

	public void setAction(OAction[] action) {
		this.action = action;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

class StartPoint {

	private String to;

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
}

class Kill {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private String message;
}

class EndPoint {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
