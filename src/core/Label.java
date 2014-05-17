package core;

public class Label implements Comparable<Label>{

	private boolean mark;
	private long cout;
	private Sommet padre;
	private Sommet moi;
	
	
	
	@Override
	public String toString() {
		return "Label [mark=" + mark + ", cout=" + cout + ", padre=" + padre
				+ ", moi=" + moi + "]";
	}

	Label(){
		this.mark = false;
		this.cout = -1;
	}
	
	Label(long c, boolean m, Sommet o)
	{
		this.cout = c;
		this.mark = m;
		this.moi = o;
	}
	
	Label(Sommet papaoutai, long cout, Sommet bibi){
		this.mark = false;
		this.cout = cout;
		this.padre = papaoutai;
		this.moi = bibi;
	}
	
	public boolean isMark() {
		return mark;
	}
	public void setMark(boolean mark) {
		this.mark = mark;
	}
	public long getCout() {
		return cout;
	}
	public void setCout(long cout) {
		this.cout = cout;
	}
	public Sommet getPadre() {
		return padre;
	}
	public void setPadre(Sommet padre) {
		this.padre = padre;
	}
	
	public int compareTo(Label autre) {
		int ret;
		
		if(autre.getCout() == this.getCout())
		{
			ret = 0;
		} else if (this.getCout() != -1 && this.getCout() < autre.getCout())
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
