package core ;

import java.io.* ;
import base.Readarg ;

public class Connexite extends Algo {

    public Connexite(Graphe gr, PrintStream sortie, Readarg readarg) {
    	super(gr, sortie, readarg) ;
    }

    public Chemin run() {
    	return new Chemin();
    }

}
