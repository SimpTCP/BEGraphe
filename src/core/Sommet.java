package core;

import java.util.ArrayList;

import base.Dessin;

public class Sommet {

	private ArrayList<Arc> RoutesSortantes;
	private float latitude;
	private float longitude;
	private int nombreRouteSortante;
	private int entierSommet;

	public int getEntierSommet() {
		return entierSommet;
	}

	public void setEntierSommet(int entierSommet) {
		this.entierSommet = entierSommet;
	}

	public Sommet() {
		super();
		RoutesSortantes = new ArrayList<Arc>();
	}

	@Override
	public String toString() {
		return "Sommet "+entierSommet+" [RoutesSortantes=" + RoutesSortantes + ", latitude="
				+ latitude + ", longitude=" + longitude
				+ ", nombreRouteSortante=" + this.RoutesSortantes.size() + "]";
	}
	
	public Arc arcToSommet(Sommet s){
		Arc arc = this.RoutesSortantes.get(0);
		for(Arc a : this.RoutesSortantes){
			if(a.getDestination() == s){
				arc = a;
				break;
			}
		}
		return arc;
	}
	
	public void drawSommet(int taille, Dessin dessin){
		dessin.drawPoint(longitude,latitude,taille);
	}

	
	public ArrayList<Arc> getRoutesSortantes() {
		return RoutesSortantes;
	}

	public void setRoutesSortantes(ArrayList<Arc> routesSortantes) {
		RoutesSortantes = routesSortantes;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public void addRouteSortante(Arc arc){
		this.RoutesSortantes.add(arc);
	}

	public int getNombreRouteSortante() {
		return nombreRouteSortante;
	}

	public void setNombreRouteSortante(int nombreRouteSortante) {
		this.nombreRouteSortante = nombreRouteSortante;
	}
	
}
