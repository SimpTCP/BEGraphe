package core ;


import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

import base.Couleur;
import base.Descripteur;
import base.Dessin;
import base.Utils;

public class Graphe {

	// Nom de la carte utilisee pour construire ce graphe
	private final String nomCarte ;

	// Fenetre graphique
	private final Dessin dessin ;

	// Version du format MAP utilisé.
	private static final int version_map = 4 ;
	private static final int magic_number_map = 0xbacaff ;

	// Version du format PATH.
	private static final int version_path = 1 ;
	private static final int magic_number_path = 0xdecafe ;

	// Identifiant de la carte
	private int idcarte ;

	// Numero de zone de la carte
	private int numzone ;

	/*
	 * Ces attributs constituent une structure ad-hoc pour stocker les informations du graphe.
	 * Vous devez modifier et ameliorer ce choix de conception simpliste.
	 */
	// ArrayList contenant tous nos sommets
	private ArrayList<Sommet> sommets;
	// ArrayList contenant tous nos descripteurs de path
	private ArrayList<Descripteur> descripteurs;
	private ArrayList<Chemin> chemins;
	private int nombreSommets;
	private int nombreDescripteurs;
	private int vitesseMax = 0;

	public Dessin getDessin() {
		return dessin;
	}

	public int getZone() {
		return numzone ;
	}

	public int getVitesseMax()
	{
		return this.vitesseMax;
	}
	
	public String toString()
	{
		String ret = "";
		ret += "Graphe avec :\n";
		ret += "\t- sommets :" + this.sommets.size();
		ret += "\n\t- descripteurs : " + this.descripteurs.size();
		return ret;
	}

	// Le constructeur cree le graphe en lisant les donnees depuis le DataInputStream
	public Graphe (String nomCarte, DataInputStream dis, Dessin dessin) {

		this.nomCarte = nomCarte ;
		this.dessin = dessin ;
		Utils.calibrer(nomCarte, dessin) ;

		//init des arraylist
		this.sommets = new ArrayList<Sommet>();
		this.descripteurs = new ArrayList<Descripteur>();
		this.chemins = new ArrayList<Chemin>();
		// Lecture du fichier MAP. 
		// Voir le fichier "FORMAT" pour le detail du format binaire.
		try {

			// Nombre d'aretes
			int edges = 0 ;

			// Verification du magic number et de la version du format du fichier .map
			int magic = dis.readInt () ;
			int version = dis.readInt () ;
			Utils.checkVersion(magic, magic_number_map, version, version_map, nomCarte, ".map") ;

			// Lecture de l'identifiant de carte et du numero de zone, 
			this.idcarte = dis.readInt () ;
			this.numzone = dis.readInt () ;

			// Lecture du nombre de descripteurs, nombre de noeuds.
			int nb_descripteurs = dis.readInt () ;
			int nb_nodes = dis.readInt () ;

			// ---------------------------------------------------------------
			//Fixe nombre sommets et nombres de descripteurs
			this.nombreSommets = nb_nodes;
			this.nombreDescripteurs = nb_descripteurs;

			// Lecture des noeuds
			for (int num_node = 0 ; num_node < nb_nodes ; num_node++) {
				Sommet s = new Sommet();
				s.setLongitude(((float)dis.readInt ()) / 1E6f) ;
				s.setLatitude(((float)dis.readInt ()) / 1E6f) ;
				s.setNombreRouteSortante((char) dis.readUnsignedByte()) ;
				s.setEntierSommet(num_node);
				this.sommets.add(s);
			}

			Utils.checkByte(255, dis) ;

			// Lecture des descripteurs
			for (int num_descr = 0 ; num_descr < nb_descripteurs ; num_descr++) {
				Descripteur d = new Descripteur(dis);
				this.descripteurs.add(d);
				if(d.vitesseMax() > this.vitesseMax)
				{
					this.vitesseMax = d.vitesseMax();
				}
			}

			Utils.checkByte(254, dis) ;

			for(Sommet s : this.sommets)
			{
				for(int num_succ = 0; num_succ < s.getNombreRouteSortante(); num_succ++)
				{
					Arc a = new Arc();
					a.setZoneDeDestination((char) dis.readUnsignedByte());
					a.setDestination(this.sommets.get(Utils.read24bits(dis)));
					a.setDescripteur(this.descripteurs.get(Utils.read24bits(dis)));
					a.setLongueur(dis.readUnsignedShort());
					edges++;

					s.addRouteSortante(a);

					// Création du symétrique si la route est à double sens
					if(!a.getDescripteur().isSensUnique())
					{
						Arc b = new Arc();
						b.setDescripteur(a.getDescripteur());
						b.setLongueur(a.getLongueur());
						b.setZoneDeDestination(a.getZoneDeDestination());
						b.setDestination(s);
						a.getDestination().addRouteSortante(b);
						edges++;
					}

					// lecture des segments
					int nb_segm = dis.readUnsignedShort();
					Couleur.set(dessin, a.getDescripteur().getType());
					float current_long = s.getLongitude();
					float current_lat = s.getLatitude();
					for(int i=0; i<nb_segm; i++)
					{
						float delta_lon = (dis.readShort())/2.0E5f;
						float delta_lat = (dis.readShort())/2.0E5f;
						dessin.drawLine(current_long, current_lat, (current_long+delta_lon), (current_lat+delta_lat));
						current_long += delta_lon;
						current_lat += delta_lat;
					}

					dessin.drawLine(current_long, current_lat, a.getDestination().getLongitude(), a.getDestination().getLatitude());

				}
			}

			Utils.checkByte(253, dis) ;

			System.out.println("Fichier lu ("+this.nomCarte+"): " + nb_nodes + " sommets, " + edges + " arc, " 
					+ nb_descripteurs + " descripteurs.");
			System.out.println(this);

		} catch (IOException e) {
			e.printStackTrace() ;
			System.exit(1) ;
		}

	}

	// Rayon de la terrlonge en metres
	private static final double rayonTerre = 6378137.0 ;

	/**
	 *  Calcule de la distance orthodromique - plus court chemin entre deux points �� la surface d'une sph��re
	 *  @param long1 longitude du premier point.
	 *  @param lat1 latitude du premier point.
	 *  @param long2 longitude du second point.
	 *  @param lat2 latitude du second point.
	 *  @return la distance entre les deux points en metres.
	 *  Methode ����crite par Thomas Thiebaud, mai 2013
	 */
//	public static double distance(double long1, double lat1, double long2, double lat2) {
//		double sinLat = Math.sin(Math.toRadians(lat1))*Math.sin(Math.toRadians(lat2));
//		double cosLat = Math.cos(Math.toRadians(lat1))*Math.cos(Math.toRadians(lat2));
//		double cosLong = Math.cos(Math.toRadians(long2-long1));
//		return rayon_terre*Math.acos(sinLat+cosLat*cosLong);
//	}

	public static double distance(double long1, double lat1, double long2,
			double lat2) {
		double sinLat = Math.sin(Math.toRadians(lat1))
				* Math.sin(Math.toRadians(lat2));
		double cosLat = Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2));
		double cosLong = Math.cos(Math.toRadians(long2 - long1));
		return rayonTerre * Math.acos(sinLat + cosLat * cosLong);
	}
	
	/**
	 *  Attend un clic sur la carte et affiche le numero de sommet le plus proche du clic.
	 *  A n'utiliser que pour faire du debug ou des tests ponctuels.
	 *  Ne pas utiliser automatiquement a chaque invocation des algorithmes.
	 */
	public Sommet situerClick(boolean display) {

		Sommet ret = null;

		if (dessin.waitClick()) {
			float lon = dessin.getClickLon() ;
			float lat = dessin.getClickLat() ;

			System.out.println("Clic aux coordonnees lon = " + lon + "  lat = " + lat) ;

			// On cherche le noeud le plus proche. O(n)
			float minDist = Float.MAX_VALUE ;
			Sommet noeud = this.sommets.get(0);

			for(Sommet s : this.sommets)
			{
				float londiff = (s.getLongitude() - lon);
				float latdiff = (s.getLatitude() - lat);
				float dist = londiff*londiff + latdiff*latdiff ;
				if (dist < minDist) {
					noeud = s ;
					minDist = dist ;
				}
			}

			ret = noeud;
			if(display)
			{
				System.out.println("Noeud le plus proche : " + noeud) ;
				System.out.println() ;
				dessin.setColor(java.awt.Color.green) ;
				dessin.drawPoint(noeud.getLongitude(), noeud.getLatitude(), 5);
			}
		}
		
		return ret;
	}

	/**
	 *  Charge un chemin depuis un fichier .path (voir le fichier FORMAT_PATH qui decrit le format)
	 *  Verifie que le chemin est empruntable et calcule le temps de trajet.
	 */
	public void verifierChemin(DataInputStream dis, String nom_chemin) {

		try {

			// Verification du magic number et de la version du format du fichier .path
			int magic = dis.readInt();
			int version = dis.readInt();
			Utils.checkVersion(magic, magic_number_path, version, version_path, nom_chemin, ".path") ;

			Chemin chemin = new Chemin();
			
			// Lecture de l'identifiant de carte
			chemin.setIdCarte(dis.readInt());

			if (chemin.getIdCarte() != this.idcarte) {
				System.out.println("Le chemin du fichier " + nom_chemin + " n'appartient pas a la carte actuellement chargee." );
				System.exit(1);
			}

			int nbrNoeud = dis.readInt();

			// Origine du chemin
			dis.readUnsignedByte();
			int first_node = Utils.read24bits(dis);
			chemin.setSource(this.sommets.get(first_node));

			// Destination du chemin
			dis.readUnsignedByte(); // int last_zone  = 
			int last_node = Utils.read24bits(dis);
			chemin.setDestination(this.sommets.get(last_node));

			System.out.println("Chemin de ("+nbrNoeud+" sommets):" + chemin.getSource() + " vers " + chemin.getDestination());

			int current_zone = 0;
			int current_node = 0;
			Sommet s = new Sommet();
			// Tous les noeuds du chemin
			for (int i = 0 ; i < nbrNoeud ; i++) {
				current_zone = dis.readUnsignedByte();
				current_node = Utils.read24bits(dis);
				s = this.sommets.get(current_node);
				chemin.addSommet(s);
			}

			//if ((current_zone != last_zone) || (current_node != last_node)) {
			if (current_node != last_node) {
				System.out.println("Le chemin " + nom_chemin + " ne termine pas sur le bon noeud.") ;
				System.exit(1) ;
			}
			
			this.chemins.add(chemin);
			System.out.println("Le chemin dure " + chemin.coutChemin()/60F + "mins");
			chemin.dessinChemin(dessin);
			System.out.println(chemin);

		} catch (IOException e) {
			e.printStackTrace() ;
			System.exit(1) ;
		}

	}
	public ArrayList<Sommet> getSommets() {
		return this.sommets;
	}
	public void setSommets(ArrayList<Sommet> sommets) {
		this.sommets = sommets;
	}
	public ArrayList<Descripteur> getDescripteurs() {
		return this.descripteurs;
	}
	public void setDescripteurs(ArrayList<Descripteur> descripteurs) {
		this.descripteurs = descripteurs;
	}
	public int getNombreSommets() {
		return nombreSommets;
	}
	public void setNombreSommets(int nombreSommets) {
		this.nombreSommets = nombreSommets;
	}
	public int getNombreDescripteurs() {
		return nombreDescripteurs;
	}
	public void setNombreDescripteurs(int nombreDescripteurs) {
		this.nombreDescripteurs = nombreDescripteurs;
	}
	public int getIdCarte()
	{
		return this.idcarte;
	}

}
