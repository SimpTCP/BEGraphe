package core;

import base.Descripteur;

public class Arc {
	
	private Descripteur descripteur;
	private float longueur;
	private Sommet destination;
	private char zoneDeDestination;

// Log : Chemin [cout=15045, nbrNoeuds=551, idCarte=1024, source=Sommet 39983 [RoutesSortantes=[Arc [longueur=71, tempsParcours()=8.52, getVitesse()=30], Arc [longueur=32, tempsParcours()=3.84, getVitesse()=30], Arc [longueur=1728, tempsParcours()=207.35999999999999, getVitesse()=30]], latitude=43.267796, longitude=0.076559, nombreRouteSortante=3], destination=Sommet 106171 [RoutesSortantes=[Arc [longueur=61, tempsParcours()=7.319999999999999, getVitesse()=30]], latitude=44.877304, longitude=2.72019, nombreRouteSortante=1]]

	@Override
	public String toString() {
		return "Arc [longueur=" + longueur + ", tempsParcours()="
				+ this.tempsParcours(0) + "secondes, getVitesse()=" + getVitesse() + "]";
	}

	public float tempsParcours()
	{
		return this.tempsParcours(0);
	}
	
	// renvoie le temps de parcours en secondes
	public float tempsParcours(int vitesse){
		float retour;
		if(vitesse ==0){
			retour = (this.longueur)/(this.getVitesse()/3.6f);
		}
		else{
			retour = (this.longueur)/(vitesse/3.6f);
		}
		return retour;
	}
	
	public Descripteur getDescripteur() {
		return descripteur;
	}

	public void setDescripteur(Descripteur descripteur) {
		this.descripteur = descripteur;
	}

	public float getLongueur() {
		return longueur;
	}

	public void setLongueur(float longueur) {
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

	public int getVitesse()
	{
		return this.descripteur.vitesseMax();
	}
	
}
