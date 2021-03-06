package test;

import java.io.DataInputStream;
import java.io.PrintStream;

import base.Dessin;
import base.DessinInvisible;
import base.DessinVisible;
import base.Openfile;
import base.Readarg;
import core.Chemin;
import core.Graphe;
import core.Pcc;
import core.PccStar;
import core.Sommet;

public class TestTout {
	
	private boolean display = true;
	private final Readarg readarg;
	private PrintStream logFile;
	// format : nomCarte, point, point
//	private String[][] cartes = {
//			{"carre", "9", "19"},
//			{"carre-dense", "323144", "183927"},
//			{"carre-dense", "1", "19938"},
//			{"fractal", "611565", "512445"},
//			{"midip", "135285", "95173"},
//			{"midip", "135285", "47243"},
//			{"france", "1436267", "450555"},
//			{"france", "2258313", "2385597"}};
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
	
	public TestTout(String[] args) {
		this.readarg = new Readarg(args);
	}
	
	public static void main(String[] args) {
		TestTout testTout = new TestTout(args);
		testTout.go();
	}


	private boolean launchTestPcc(Graphe gr, Sommet haut, Sommet bas)
	{
		boolean ret = false;
		
		Chemin hautBasPcc = new Pcc(gr, null, haut, bas).run();
		Sommet middle = hautBasPcc.getSommets().get((int) (hautBasPcc.getSommets().size()/2));
		Chemin hautMiddle = new Pcc(gr, null, haut, middle).run();
		Chemin middleBas = new Pcc(gr, null, middle, bas).run();
		
		int hHB = (int) (hautBasPcc.coutChemin()/3600);
		int mHB = (int) ((hautBasPcc.coutChemin()/3600 - hHB)*60);
		int hHM = (int) (hautMiddle.coutChemin()/3600);
		int mHM = (int) ((hautMiddle.coutChemin()/3600 - hHM)*60);
		int hMB = (int) (middleBas.coutChemin()/3600);
		int mMB = (int) ((middleBas.coutChemin()/3600 - hMB)*60);
		int hTotal = (int) ((hautMiddle.coutChemin() + middleBas.coutChemin())/3600);
		int mTotal = (int) ((((hautMiddle.coutChemin() + middleBas.coutChemin())/3600) - hTotal)*60);
		
		this.log(haut.getEntierSommet()+" & "+bas.getEntierSommet()+" & "+middle.getEntierSommet()+" & "+
				hHM+"h"+mHM+"m & "+hMB+"h"+mMB+"m & "+hTotal+"h"+mTotal+"m &"+hHB+"h"+mHB+"m");
		
		if((int) hautBasPcc.coutChemin() == (int) (hautMiddle.coutChemin() + middleBas.coutChemin()))
		{
			ret = true;
		}
		return ret;
	}
	
	private boolean launchTestPccStar(Graphe gr, Sommet haut, Sommet bas)
	{
		boolean ret = false;
		
		Chemin hautBasPcc = new PccStar(gr, null, haut, bas).run();
		Sommet middle = hautBasPcc.getSommets().get((int) (hautBasPcc.getSommets().size()/2));
		Chemin hautMiddle = new PccStar(gr, null, haut, middle).run();
		Chemin middleBas = new PccStar(gr, null, middle, bas).run();
		
		int hHB = (int) (hautBasPcc.coutChemin()/3600);
		int mHB = (int) ((hautBasPcc.coutChemin()/3600 - hHB)*60);
		int hHM = (int) (hautMiddle.coutChemin()/3600);
		int mHM = (int) ((hautMiddle.coutChemin()/3600 - hHM)*60);
		int hMB = (int) (middleBas.coutChemin()/3600);
		int mMB = (int) ((middleBas.coutChemin()/3600 - hMB)*60);
		int hTotal = (int) ((hautMiddle.coutChemin() + middleBas.coutChemin())/3600);
		int mTotal = (int) ((((hautMiddle.coutChemin() + middleBas.coutChemin())/3600) - hTotal)*60);
		
		this.log(haut.getEntierSommet()+" & "+bas.getEntierSommet()+" & "+middle.getEntierSommet()+" & "+
				hHM+"h"+mHM+"m & "+hMB+"h"+mMB+"m & "+hTotal+"h"+mTotal+"m &"+hHB+"h"+mHB+"m");
		
		if((int) hautBasPcc.coutChemin() == (int) (hautMiddle.coutChemin() + middleBas.coutChemin()))
		{
			ret = true;
		}
		return ret;
	}
	
	private void go() {
		try {
			this.askSortie();
			String last = "";
			Graphe graphe = null;
			Dessin dessin = null;
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
				
				Sommet haut = graphe.getSommet(Integer.parseInt(carte[1]));
				Sommet bas = graphe.getSommet(Integer.parseInt(carte[2]));
				
//				if(this.launchTestPccStar(graphe, haut, bas))
//				{
//					this.log("Haut - bas sur PCC STAR : OK");
//				} else {
//					this.log("Haut - bas sur PCC STAR : NON --------------------- ALERTE");
//				}
				
				if(this.launchTestPccStar(graphe, haut, bas))
				{
					this.log("Haut - bas sur PCC : OK");
				} else {
					this.log("Haut - bas sur PCC : NON --------------------- ALERTE");
				}
				
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
