package core;

import base.Dessin;
import base.Descripteur;

public class Arc {
	
	private Descripteur descripteur;
	private int longueur;
	private Sommet destination;
	private char zoneDeDestination;


	public String toString(){
		return "longueur : "+longueur+" vitessemax : "+descripteur.vitesseMax()+"";
	}
	public long tempsParcours(){
		float pitou;
		pitou = ((float)longueur)/((float)(this.descripteur.vitesseMax())*1000);
		pitou*= 3600000;
		return ((long)pitou);
	}
	
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

	public char getZoneDeDestination() {
		return zoneDeDestination;
	}

	public void setZoneDeDestination(char zoneDeDestination) {
		this.zoneDeDestination = zoneDeDestination;
	}

	public Sommet getDestination() {
		return destination;
	}

	public void setDestination(Sommet destination) {
		this.destination = destination;
	}

}
