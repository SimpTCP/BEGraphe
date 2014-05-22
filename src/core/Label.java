package core;

public class Label implements Comparable<Label>{

	private boolean mark;
	private long cout;
	private Sommet padre;
	private Sommet moi;
	private Sommet destination;
	public Sommet getDestination() {
		return destination;
	}

	public void setDestination(Sommet destination) {
		this.destination = destination;
	}
	boolean isStar;
	
	public boolean isStar() {
		return isStar;
	}

	public void setStar(boolean isStar) {
		this.isStar = isStar;
	}

	@Override
	public String toString() {
		return "Label [mark=" + mark + ", cout=" + cout + ", padre=" + padre
				+ ", moi=" + moi + "isStar : "+isStar+"]";
	}

	Label(){
		this.mark = false;
		this.cout = -1;
	}
	Label(boolean mark, Sommet dest){
		this.mark = false;
		this.cout = -1;
		this.mark = mark;
		this.destination = dest;
	}
	
	Label(long c, boolean m, Sommet o, Sommet dest)
	{
		this.cout = c;
		this.mark = m;
		this.moi = o;
		this.destination = dest;
	}
	
	Label(Sommet papaoutai, long cout, Sommet bibi, Sommet dest){
		this.mark = false;
		this.cout = cout;
		this.padre = papaoutai;
		this.moi = bibi;
		this.destination = dest;
	}
	Label(long c, boolean m, Sommet o, Sommet dest, boolean is)
	{
		this.cout = c;
		this.mark = m;
		this.moi = o;
		this.destination = dest;
		this.isStar = is;
	}
	
	Label(Sommet papaoutai, long cout, Sommet bibi, Sommet dest, boolean is){
		this.mark = false;
		this.cout = cout;
		this.padre = papaoutai;
		this.moi = bibi;
		this.destination = dest;
		this.isStar = is;
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
	public long coutTotal() {
		long k = 1; // coefficient pour le cout estimé entre 0 et 100
		long ret = cout;
		if(isStar()){
			ret = cout + (long)(((float)this.distancePapa()/(float)(130*1000))*3600000); 
		}
		return ret;
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
	public long distancePapa(){
		return (long) Graphe.distance((double)this.getMoi().getLongitude(), (double)this.getMoi().getLatitude(), (double)this.getDestination().getLongitude(), (double)this.getDestination().getLatitude());
	}
	public int compareTo(Label autre) {
		int ret;
		if(autre.coutTotal() == this.coutTotal())
		{
			ret = 0;
		} else if (this.coutTotal() != -1 && this.coutTotal() < autre.coutTotal())
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
