package core ;

import java.io.PrintStream;
import java.util.ArrayList;

import base.Readarg;

public class Connexite extends Algo {

    public Connexite(Graphe gr, PrintStream sortie, Readarg readarg) {
    	super(gr, sortie, readarg) ;
    }

    public Chemin run()
    {
    	return new Chemin();
    }
    
    public Chemin run(int vitesse, float cout, ArrayList<Label>s, BinaryHeap<Label> c) {
    	return this.run();
    }

}
