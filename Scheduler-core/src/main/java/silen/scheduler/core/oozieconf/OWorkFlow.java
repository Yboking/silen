package silen.scheduler.core.oozieconf;

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

	public void addFork(Fork f) {

		if (fork == null) {

			fork = new Fork[] { f };
		} else {

			Fork[] newforks = new Fork[fork.length + 1];

			for (int i = 0; i < fork.length; i++) {

				newforks[i] = fork[i];

			}
			newforks[newforks.length - 1] = f;

			fork = newforks;
		}

	}

	public void addJoin(Join j) {

		if (join == null) {

			join = new Join[] { j };
		} else {

			Join[] newjoins = new Join[join.length + 1];

			for (int i = 0; i < join.length; i++) {

				newjoins[i] = join[i];

			}
			newjoins[newjoins.length - 1] = j;

			join = newjoins;
		}

	}

	public void addAction(OAction act) {

		if (action == null) {

			action = new OAction[] { act };
		} else {

			OAction[] newactions = new OAction[action.length + 1];

			for (int i = 0; i < action.length; i++) {

				newactions[i] = action[i];

			}
			newactions[newactions.length - 1] = act;

			action = newactions;
		}

	}

}
