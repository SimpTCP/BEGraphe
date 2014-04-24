package core;

import base.Descripteur;

public class Arc {
	private Descripteur descripteur;
	private int longueur;
	private Sommet SommetArrivee;
	private char zoneDeDestination;
	
	
	public Descripteur getDescripteur() {
		return descripteur;
	}
	public void setDescripteur(Descripteur descripteur) {
		this.descripteur = descripteur;
	}
	public int getLongueur() {
		return longueur;
	}
	public void setLongueur(int longueur) {
		this.longueur = longueur;
	}
	public Sommet getSommetArrivee() {
		return SommetArrivee;
	}
	public void setSommetArrivee(Sommet sommetArrivee) {
		SommetArrivee = sommetArrivee;
	}
	public char getZoneDeDestination() {
		return zoneDeDestination;
	}
	public void setZoneDeDestination(char zoneDeDestination) {
		this.zoneDeDestination = zoneDeDestination;
	}

}
