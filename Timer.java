public class Timer{
	private long t;
	private boolean started;
	public Timer(){
		this.t=0;
		this.started=false;
	}
	public void start(){
		this.t = System.currentTimeMillis();
		this.started=true;
	}
	public double getTime(Boolean reset){
		double dt;
		if(this.started==false) dt=0;
		else dt = (System.currentTimeMillis() - this.t)/1000.0;
		if(reset || this.started==false)
			this.start();
		return dt;

	}
	public double getTime(){
		return this.getTime(true);
	}
	public void reset(){
		this.t = System.currentTimeMillis();
	}
	public void stop(){
		this.t = 0;
	}
}