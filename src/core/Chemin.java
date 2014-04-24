package core;

import java.util.ArrayList;
import java.util.Date;

public class Chemin {

	private int nbrNoeuds;
	private int idCarte;
	private Sommet source;
	private Sommet destination;
	private ArrayList<Sommet> sommets;
	
	public Chemin() {
		this.sommets = new ArrayList<Sommet>();
	}
	
	public long coutChemin(){
		int k, DEBUG;
		long cout;
		Sommet tempSrc = new Sommet();
		Sommet tempDst = new Sommet();
		Arc tempArc = new Arc();
		cout = 0;
		for(k=0; k<sommets.size()-1; k++){
			tempSrc = sommets.get(k);
			tempDst = sommets.get(k+1);
			tempArc = tempSrc.arcToSommet(tempDst);
			cout += tempArc.tempsParcours();
		}
		return cout;
	}
	
	public int getNbrNoeuds() {
		return nbrNoeuds;
	}
	public void setNbrNoeuds(int nbrNoeuds) {
		this.nbrNoeuds = nbrNoeuds;
	}
	public int getIdCarte() {
		return idCarte;
	}
	public void setIdCarte(int idCarte) {
		this.idCarte = idCarte;
	}
	public Sommet getSource() {
		return source;
	}
	public void setSource(Sommet source) {
		this.source = source;
	}
	public Sommet getDestination() {
		return destination;
	}
	public void setDestination(Sommet destination) {
		this.destination = destination;
	}
	public ArrayList<Sommet> getSommets() {
		return sommets;
	}
	public void setSommets(ArrayList<Sommet> sommets) {
		this.sommets = sommets;
	}
	
	public void addSommet(Sommet s)
	{
		this.sommets.add(s);
	}
	
}
