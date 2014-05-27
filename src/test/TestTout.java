package test;

import java.io.DataInputStream;
import java.io.PrintStream;
import java.util.Random;

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
	private String[][] cartes = {
//			{"carre", "9", "19"},
//			{"carre-dense", "323144", "183927"},
//			{"carre-dense", "1", "19938"},
//			{"fractal", "859597", "611565"},
//			{"fractal", "611565", "512445"},
//			{"midip", "135285", "95173"},
//			{"midip", "135285", "47243"},
			{"france", "1436267", "450555"},
			{"france", "2258313", "2385597"}};
	
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
		long start, stop;
		
		start = System.currentTimeMillis();
		Chemin hautBasPcc = new Pcc(gr, null, haut, bas).run();
		stop = System.currentTimeMillis();
		this.log("\tPcc time : "+(stop-start)+"ms");
		
		Sommet hautBasRandom = hautBasPcc.getSommets().get(new Random().nextInt(hautBasPcc.getNbrNoeuds()));
		
		start = System.currentTimeMillis();
		Chemin hautRandom = new Pcc(gr, null, haut, hautBasRandom).run();
		stop = System.currentTimeMillis();
		this.log("\tPcc time : "+(stop-start)+"ms");
		
		start = System.currentTimeMillis();
		Chemin randomBas = new Pcc(gr, null, hautBasRandom, bas).run();
		stop = System.currentTimeMillis();
		this.log("\tPcc time : "+(stop-start)+"ms");
		
		this.log("\t x -> y cost : "+ (int) hautBasPcc.coutChemin());
		this.log("\t x -> a cost : "+ (int) hautRandom.coutChemin());
		this.log("\t a -> y cost : "+ (int) randomBas.coutChemin());
		this.log("\t x -> a -> y cost : "+((int) (hautRandom.coutChemin() + randomBas.coutChemin())));
		
		if((int) hautBasPcc.coutChemin() == (int) (hautRandom.coutChemin() + randomBas.coutChemin()))
		{
			ret = true;
		}
		return ret;
	}
	
	private boolean launchTestPccStar(Graphe gr, Sommet haut, Sommet bas)
	{
		boolean ret = false;
		long start, stop;
		
		start = System.currentTimeMillis();
		Chemin hautBasPcc = new PccStar(gr, null, haut, bas).run();
		stop = System.currentTimeMillis();
		this.log("\tPccStar time : "+(stop-start)+"ms");
		
		Sommet hautBasRandom = hautBasPcc.getSommets().get(new Random().nextInt(hautBasPcc.getNbrNoeuds()));
		
		start = System.currentTimeMillis();
		Chemin hautRandom = new PccStar(gr, null, haut, hautBasRandom).run();
		stop = System.currentTimeMillis();
		this.log("\tPccStar time : "+(stop-start)+"ms");
		
		start = System.currentTimeMillis();
		Chemin randomBas = new PccStar(gr, null, hautBasRandom, bas).run();
		stop = System.currentTimeMillis();
		this.log("\tPccStar time : "+(stop-start)+"ms");
		
		this.log("\t x -> y cost : "+ (int) hautBasPcc.coutChemin());
		this.log("\t x -> a cost : "+ (int) hautRandom.coutChemin());
		this.log("\t a -> y cost : "+ (int) randomBas.coutChemin());
		this.log("\t x -> a -> y cost : "+((int) (hautRandom.coutChemin() + randomBas.coutChemin())));
		
		if((int) hautBasPcc.coutChemin() == (int) (hautRandom.coutChemin() + randomBas.coutChemin()))
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
			for(String[] carte : this.cartes) {
				
				this.log("[+] Lancement pour la carte : " + carte[0]);
				
				if(!carte[0].equals(last))
				{
					System.out.println("Loading map ...");
					DataInputStream mapdata = Openfile.open(carte[0]);
					Dessin dessin = (this.display) ? new DessinVisible(800,600) : new DessinInvisible();
					graphe = new Graphe(carte[0], mapdata, dessin);
					last = carte[0];
				}
				
				Sommet haut = graphe.getSommet(Integer.parseInt(carte[1]));
				Sommet bas = graphe.getSommet(Integer.parseInt(carte[2]));
				
				if(this.launchTestPccStar(graphe, haut, bas))
				{
					this.log("Haut - bas sur PCC STAR : OK");
				} else {
					this.log("Haut - bas sur PCC STAR : NON --------------------- ALERTE");
				}
				
				if(this.launchTestPcc(graphe, haut, bas))
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
