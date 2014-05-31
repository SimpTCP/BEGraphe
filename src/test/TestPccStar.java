package test;

import java.io.DataInputStream;
import java.io.PrintStream;
import java.util.Scanner;

import base.Dessin;
import base.DessinInvisible;
import base.DessinVisible;
import base.Openfile;
import base.Readarg;
import core.Chemin;
import core.Graphe;
import core.PccStar;
import core.Sommet;

public class TestPccStar {

	private boolean display = true;
	private final Readarg readarg;
	private PrintStream logFile;
	// format : nomCarte, point, point
	private String[][] cartes = {
			{"france", "310602", "660872"},
			{"france", "230675", "844703"},
			{"france", "1879296", "207843"},
			{"france", "310581", "1371076"},
			{"france", "1493086", "1544868"},
			{"france", "94147", "133605"},
			{"france", "1934855", "475429"},
			{"france", "1236634", "1595238"},
			{"france", "802097", "2203629"}};
	
	public TestPccStar(String[] args) {
		this.readarg = new Readarg(args);
	}
	
	public static void main(String[] args) {
		TestPccStar testPccStar = new TestPccStar(args);
		testPccStar.go();
	}
	
	private void go() {
		try {
			this.askSortie();
			String last = "";
			Graphe graphe = null;
			Dessin dessin = null;
			Scanner sc = new Scanner(System.in);
			for(String[] carte : this.cartes) {
				
				this.log("[+] Lancement pour la carte : " + carte[0]);
				
				if(!carte[0].equals(last))
				{
					System.out.println("Loading map ...");
					DataInputStream mapdata = Openfile.open(carte[0]);
					dessin = (this.display) ? new DessinVisible(800,600) : new DessinInvisible();
					graphe = new Graphe(carte[0], mapdata, dessin);
					last = carte[0];
				}
				System.out.println("Wait buuurrrning...");
				sc.nextLine();
				Sommet origine = graphe.getSommet(Integer.parseInt(carte[1]));
				Sommet destination = graphe.getSommet(Integer.parseInt(carte[2]));
				
				Chemin c = new PccStar(graphe, null, origine, destination).run();
				int distKm = (int) (c.getDistance()/1000);
				int heures = (int) (c.coutChemin()/3600);
				int min = (int) ((c.coutChemin()/3600 - heures)*60);
				this.log(origine.getEntierSommet()+" & "+destination.getEntierSommet() + " & a & a & "+
						distKm+"km & "+heures+"h"+min+"m & "+c.getStats().getTimeRun()+"ms & "+c.getStats().getVisite()+" & "+c.getStats().getMarque());
				
				this.log(origine.getLatitude()+", "+origine.getLongitude());
				this.log(destination.getLatitude()+", "+destination.getLongitude());
			}
			
		} catch(Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}
	
	private void log(String str)
	{
		System.out.println(str);
		this.logFile.println(str);
	}
	
	private void askSortie() {
		PrintStream result = System.out;
		String nom = this.readarg.lireString("Nom du fichier de sortie : ");
		
		if("".equals(nom)) {
			nom = "/dev/null";
		}
		
		try {
			result = new PrintStream(nom);
		}
		catch (Exception e) {
			System.err.println ("Erreur a l'ouverture du fichier " + nom) ;
			System.exit(1) ;
		}
		
		this.logFile = result;
		
	}

}
