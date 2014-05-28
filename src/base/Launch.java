package base ;

/*
 * Ce programme propose de lancer divers algorithmes sur les graphes
 * a partir d'un menu texte, ou a partir de la ligne de commande (ou des deux).
 *
 * A chaque question posee par le programme (par exemple, le nom de la carte), 
 * la reponse est d'abord cherchee sur la ligne de commande.
 *
 * Pour executer en ligne de commande, ecrire les donnees dans l'ordre. Par exemple
 *   "java base.Launch insa 1 1 /tmp/sortie 0"
 * ce qui signifie : charge la carte "insa", calcule les composantes connexes avec une sortie graphique,
 * ecrit le resultat dans le fichier '/tmp/sortie', puis quitte le programme.
 */

import java.awt.Color;
import java.io.DataInputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import core.Algo;
import core.BinaryHeap;
import core.Chemin;
import core.Connexite;
import core.Graphe;
import core.Label;
import core.Pcc;
import core.PccStar;
import core.Sommet;

public class Launch {

	private final Readarg readarg ;

	public Launch(String[] args) {
		this.readarg = new Readarg(args) ;
	}

	public void afficherMenu () {
		System.out.println () ;
		System.out.println ("MENU") ;
		System.out.println () ;
		System.out.println ("0 - Quitter") ;
		System.out.println ("1 - Composantes Connexes") ;
		System.out.println ("2 - Plus court chemin standard") ;
		System.out.println ("3 - Plus court chemin A-star") ;
		System.out.println ("4 - Cliquer sur la carte pour obtenir un numero de sommet.") ;
		System.out.println ("5 - Charger un fichier de chemin (.path) et le verifier.") ;
		System.out.println("6 - Plus court chemin standard (avec click!)");
		System.out.println("7 - Plus court chemin standard (avec click et logfile)");
		System.out.println("8 - Plus court chemin A-star (avec click!)");
		System.out.println("9 - Plus court chemin A-star (avec click et logfile)");
		System.out.println("10 - Pcc et PccStar sur les mêmes sommets");
		System.out.println("11 - Covoit'! (avec click)");

		System.out.println () ;
	}

	public static void main(String[] args) {
		Launch launch = new Launch(args) ;
		launch.go () ;
	}

	public void go() {

		try {
			System.out.println ("**") ;
			System.out.println ("** Programme de test des algorithmes de graphe.");
			System.out.println ("**") ;
			System.out.println () ;

			// On obtient ici le nom de la carte a utiliser.
			String nomcarte = this.readarg.lireString ("Nom du fichier .map a utiliser ? ") ;
			DataInputStream mapdata = Openfile.open (nomcarte) ;

			boolean display = (1 == this.readarg.lireInt ("Voulez-vous une sortie graphique (0 = non, 1 = oui) ? ")) ;	    
			Dessin dessin = (display) ? new DessinVisible(800,600) : new DessinInvisible() ;

			Graphe graphe = new Graphe(nomcarte, mapdata, dessin) ;

			// Boucle principale : le menu est accessible 
			// jusqu'a ce que l'on quitte.
			boolean continuer = true ;
			int choix ;

			while (continuer){
				this.afficherMenu () ;
				choix = this.readarg.lireInt ("Votre choix ? ") ;

				// Algorithme a executer
				Algo algo = null ;

				// Le choix correspond au numero du menu.
				switch (choix) {
					case 0 : continuer = false ; break ;

					case 1 : algo = new Connexite(graphe, this.fichierSortie (), this.readarg) ; break ;

					case 2 : algo = new Pcc(graphe, this.fichierSortie(), this.readarg) ; break ;

					case 3 : algo = new PccStar(graphe, this.fichierSortie (), this.readarg) ; break ;

					case 4 :
						System.out.println("Cliquez donc...");
						graphe.situerClick(true);
						break ;

					case 5 :
						String nom_chemin = this.readarg.lireString ("Nom du fichier .path contenant le chemin ? ") ;
						graphe.verifierChemin(Openfile.open (nom_chemin), nom_chemin) ;
						break ;

					case 6:
						algo = new Pcc(graphe);
						break;
					
					case 7:
						algo = new Pcc(graphe, this.fichierSortie());
						break;
				
					case 8:
						algo = new PccStar(graphe);
						break;
				
					case 9:
						algo = new PccStar(graphe, this.fichierSortie());
						break;
				
					case 10:
						PrintStream sortie = this.fichierSortie();
						Pcc star = new PccStar(graphe, sortie);
						sortie.println("Lancement de A-Star ...");
						star.run(0,0, null, null);
						Pcc pcc = new Pcc(graphe, sortie, star.getOrigine(), star.getDestinations().get(0));
						sortie.println("Lancement de pcc ...");
						pcc.run(0,0, null, null);
						break;
					
					case 11:
						PrintStream fsortie = new PrintStream("/dev/null");
						System.out.println("Cliquez pour choisir la voiture ...");
						Sommet voiture = graphe.situerClick(true);
						System.out.println("Cliquez pour choisir le pieton ...");
						Sommet pieton = graphe.situerClick(true);
						System.out.println("Cliquez pour choisir l'endroit ou tu vas que ce soit restaurant, station essence, club echangiste, ou je ne sais quoi d'autre, c'est toi qui vois, tant que ça te fais plaisir c'est ça le plus important ...");
						Sommet destination = graphe.situerClick(true);
						//A* de voiture vers pieton
						Pcc voiturePieton = new PccStar(graphe, fsortie, voiture, pieton);//A* de 1 vers 1
						Chemin c = voiturePieton.run(0,0, null, null); //on passe coutMax = 0 (pas de condition d'arret en temps + vitesseParcourt = 0 pas de vitesse max a part celle des routes
						float cout = c.coutChemin(0);
						System.out.println("Cout : "+cout);
						//Djikstra limité de pieton vers tous
						ArrayList<Label> labelArroundPieton = new ArrayList<Label>();
						Sommet nulle = null;
						Pcc cerclePieton= new Pcc(graphe, fsortie, pieton, nulle); //DJISKSTRA de 1 vers tous avec 
						cerclePieton.run(4,  cout, labelArroundPieton, null);//condition d'arret : stop quand cout >coutMax + parcourt a vitesse 4 + stock les sommets true dans labelSommetArroundPieton
						ArrayList<Sommet> sommetsArroundPieton = new ArrayList<Sommet>();
						for(Label lab: labelArroundPieton){
							sommetsArroundPieton.add(lab.getMoi());
						}
						//Djikstra de voiture vers les sommetsArroundPieton
						ArrayList<Label> retourDjikstraVoiture = new ArrayList<Label>();
						Pcc voiturePieton2 = new Pcc(graphe,fsortie, voiture,  sommetsArroundPieton);//1 vers plein : 1 = voiture pleins = sommetsArroundPieton
						voiturePieton2.run(0, 0, retourDjikstraVoiture, null);						
						HashMap<Sommet, Label> tempAddCout = new HashMap<Sommet, Label>();//on s'en sert pour faciliter l'ajout des couts 
						
						for(Label l: retourDjikstraVoiture){
							tempAddCout.put(l.getMoi(), l);
						}
						

						for(Label l: labelArroundPieton){//pour chaque entier dans la sphere ArroundPieton
							l.addCout(tempAddCout.get(l.getMoi()).getCout());//ajoute a l'ancien cout(pieton ->sommet) le nouveau (voiture ->sommet)
						}
						
						//A* de sommetsArroundPieton vers destination
						//pour ce faire: on créé et passe a run une binaryheap de ArroundPieton
						BinaryHeap<Label> tiens = new BinaryHeap<Label>();
						for(Label l: labelArroundPieton){
							l.setMark(true);
							l.setPadre(pieton);
							tiens.insert(l);
						}
						Pcc onYVa = new PccStar(graphe, fsortie, pieton, destination);
						Chemin toc = new Chemin();
						System.out.println("APARTIRDELA");
						toc = onYVa.run(0, 0, null, tiens);
						if(toc !=null){
							dessin.setColor(Color.black);
							dessin.drawPoint(toc.getSommets().get(1).getLongitude(), toc.getSommets().get(1).getLatitude(), 10);
						}
						break;
					
					default:
						System.out.println ("Choix de menu incorrect : " + choix) ;
						System.exit(1) ;
					}
					if (algo != null) { algo.run() ; }
				}

			System.out.println ("Programme terminé.") ;
			System.exit(0) ;


		} catch (Throwable t) {
			t.printStackTrace() ;
			System.exit(1) ;
		}
	}

	// Ouvre un fichier de sortie pour ecrire les reponses
	public PrintStream fichierSortie () {
		PrintStream result = System.out ;

		String nom = this.readarg.lireString ("Nom du fichier de sortie ? ") ;

		if ("".equals(nom)) { nom = "/dev/null" ; }

		try { result = new PrintStream(nom) ; }
		catch (Exception e) {
			System.err.println ("Erreur a l'ouverture du fichier " + nom) ;
			System.exit(1) ;
		}

		return result ;
	}

}
