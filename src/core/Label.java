package core;

public class Label implements Comparable<Label>{

	private boolean mark;
	private double cout;
	private Sommet padre;
	private Sommet moi;
	private double coutEstimee;
	
	@Override
	public String toString() {
		return "Label [mark=" + mark + ", cout=" + cout + ", padre=" + padre
				+ ", moi=" + moi + "]";
	}

	Label(){
		this.mark = false;
		this.cout = -1;
		this.coutEstimee = 0;
	}
	
	Label(double c, boolean m, Sommet o)
	{
		this.cout = c;
		this.mark = m;
		this.moi = o;
		this.coutEstimee = 0;
	}
	
	Label(Sommet papaoutai, double cout, Sommet bibi){
		this.mark = false;
		this.cout = cout;
		this.padre = papaoutai;
		this.moi = bibi;
		this.coutEstimee = 0;
	}

	public Label(Sommet padre, double cout, Sommet moi, boolean mark, double coutEstimee) {
		super();
		this.mark = mark;
		this.cout = cout;
		this.padre = padre;
		this.moi = moi;
		this.coutEstimee = coutEstimee;
	}

	public double getCoutEstimee() {
		return coutEstimee;
	}

	public void setCoutEstimee(double coutEstimee) {
		this.coutEstimee = coutEstimee;
	}

	public boolean isMark() {
		return mark;
	}
	public void setMark(boolean mark) {
		this.mark = mark;
	}
	public double getCout() {
		return cout;
	}
	public void setCout(double cout) {
		this.cout = cout;
	}
	public Sommet getPadre() {
		return padre;
	}
	public void setPadre(Sommet padre) {
		this.padre = padre;
	}
	
	public double getTotalCout()
	{
		return this.cout + this.coutEstimee;
	}
	
	public int compareTo(Label autre) {
		/*
		 * La fonction retourne :
		 * - 0 si egal
		 * - -1 si moi < autre OU monCout == -1
		 * - 1 si moi > autre
		 */
		int ret;
		
		if(autre.getTotalCout() == this.getTotalCout())
		{
			ret = 0;
		} else if (this.getCout() != -1 && this.getTotalCout() < autre.getTotalCout())
		{
			ret = -1;
		} else {
			ret = 1;
		}
		return ret;
	}
	public Sommet getMoi() {
		return moi;
	}
	public void setMoi(Sommet moi) {
		this.moi = moi;
	}
	
	
	
}
