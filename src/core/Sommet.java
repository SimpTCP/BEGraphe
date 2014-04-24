package core;

import java.util.ArrayList;

public class Sommet {

	private ArrayList<Arc> RoutesSortantes;
	private float latitude;
	private float longitude;
	private char nombreRouteSortante;

	public Sommet() {
		super();
		RoutesSortantes = new ArrayList<Arc>();
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

	public char getNombreRouteSortante() {
		return nombreRouteSortante;
	}

	public void setNombreRouteSortante(char nombreRouteSortante) {
		this.nombreRouteSortante = nombreRouteSortante;
	}

	public void addRouteSortante(Arc arc){
		this.RoutesSortantes.add(arc);
	}

}
