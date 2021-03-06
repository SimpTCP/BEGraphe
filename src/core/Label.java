package core;

public class Label implements Comparable<Label>{

	private boolean mark;
	private float cout;
	private Sommet padre;
	private Sommet moi;
	private float coutEstimee;	

	Label(){
		this.mark = false;
		this.cout = -1;
		this.coutEstimee = 0;
	}

	Label(float c, boolean m, Sommet o)
	{
		this.cout = c;
		this.mark = m;
		this.moi = o;
		this.coutEstimee = 0;
	}
	
	Label(Sommet papaoutai, float cout, Sommet bibi){
		this.mark = false;
		this.cout = cout;
		this.padre = papaoutai;
		this.moi = bibi;
		this.coutEstimee = 0;
	}

	
	
	public Label(boolean mark, float cout, Sommet padre, Sommet moi,
			float coutEstimee) {
		super();
		this.mark = mark;
		this.cout = cout;
		this.padre = padre;
		this.moi = moi;
		this.coutEstimee = coutEstimee;
	}

	@Override
	public String toString() {
		return "Label [mark=" + mark + ", cout=" + cout + ", padre=" + padre
				+ ", moi=" + moi + ", coutEstimee=" + coutEstimee + "]";
	}
	
	public float getCoutEstimee() {
		return coutEstimee;
	}

	public void setCoutEstimee(float coutEstimee) {
		this.coutEstimee = coutEstimee;
	}

	public boolean isMark() {
		return mark;
	}
	public void setMark(boolean mark) {
		this.mark = mark;
	}
	
	public float getCout() {
		return cout;
	}

	public void setCout(float cout2) {
		this.cout = cout2;
	}
	
	public Sommet getPadre() {
		return padre;
	}
	public void setPadre(Sommet padre) {
		this.padre = padre;
	}
	
	public float getTotalCout()
	{
		return this.cout + this.coutEstimee;
	}
	
	public int compareTo(Label autre) {
		int ret = Float.compare(this.getTotalCout(), autre.getTotalCout());
		if(ret == 0)
		{
//			System.out.println(this.getTotalCout()+" & "+autre.getTotalCout()+" : "+ret);
//			System.out.println(this.getCoutEstimee()+" vs "+autre.getCoutEstimee());
			ret = Float.compare(this.getCoutEstimee(), autre.getCoutEstimee());
		}
		
		return ret;
	}
	
	public Sommet getMoi() {
		return moi;
	}
	public void setMoi(Sommet moi) {
		this.moi = moi;
	}
	public void addCout(float coutAAjouter){
		this.cout+=coutAAjouter;
	}
	
	
}
