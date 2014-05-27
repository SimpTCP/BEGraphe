package core ;

import java.io.PrintStream;
import java.util.ArrayList;

import base.Readarg;

/**
 * Classe abstraite representant un algorithme (connexite, plus court chemin, etc.)
 */
public abstract class Algo {

    protected PrintStream sortie ;
    protected Graphe graphe ;
    
    protected Algo(Graphe gr, PrintStream fichierSortie, Readarg readarg) {
    	this.graphe = gr ;
    	this.sortie = fichierSortie ;	
    }
    
    protected Algo(Graphe gr, Readarg readarg) {
    	this.graphe = gr ;
    }
    
    protected Algo(Graphe gr)
    {
    	this.graphe = gr;
    }
    
    protected Algo(Graphe gr, PrintStream fichierSortie)
    {
    	this.graphe = gr;
    	this.sortie = fichierSortie;
    }
    
    protected Algo(Graphe graphe, PrintStream sortie, Sommet source, Sommet destination)
    {
    	this.graphe = graphe;
    	this.sortie = sortie;
    }
    
    public abstract Chemin run(int vitesse, float coutMax, ArrayList<Label> s, BinaryHeap<Label> b );

}
