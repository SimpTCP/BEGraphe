package test;

public class Stats {

	private long timeRun;
	private int visite;
	private int marque;
	
	public Stats(long timeRun, int visite, int marque) {
		super();
		this.timeRun = timeRun;
		this.visite = visite;
		this.marque = marque;
	}
	
	public long getTimeRun() {
		return timeRun;
	}
	public void setTimeRun(long timeRun) {
		this.timeRun = timeRun;
	}
	public int getVisite() {
		return visite;
	}
	public void setVisite(int visite) {
		this.visite = visite;
	}
	public int getMarque() {
		return marque;
	}
	public void setMarque(int marque) {
		this.marque = marque;
	}
	
	
	
}
