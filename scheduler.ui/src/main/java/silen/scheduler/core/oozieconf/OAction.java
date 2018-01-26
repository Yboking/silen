package silen.scheduler.core.oozieconf;



public class OAction {

	private String name;
    private OK ok;
    private Error error;
    
    private Spark spark;
    public Spark getSpark() {
		return spark;
	}

	public void setSpark(Spark spark) {
		this.spark = spark;
	}

	public OK getOk() {
		return ok;
	}

	public void setOk(OK ok) {
		this.ok = ok;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error err) {
		this.error = err;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}




class Error {

	private String to;

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
}