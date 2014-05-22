package core ;

import java.io.* ;

import base.Readarg ;

public class PccStar extends Pcc {

    public PccStar(Graphe gr, PrintStream sortie, Readarg readarg) {
    	super(gr, sortie, readarg) ;
    }
    
    public PccStar(Graphe graphe) {
		super(graphe);
	}

    public PccStar(Graphe graphe, PrintStream fichierSortie) {
		super(graphe, fichierSortie);
	}

	@Override
    protected Label createLabelAndPut(Sommet who, double cout, Sommet father, boolean mark)
	{
    	double coutEstimee = Graphe.distance(who.getLongitude(), who.getLatitude(), this.destination.getLongitude(), this.destination.getLatitude());
    	coutEstimee /= (140/3.6); // t = d/v (d en m, v en m/s)
    	/*
    	 * Le coutEstimee est une borne inférieure du coût réel :
    	 * - la distance est plus petite
    	 * - la vitesse est plus grande que celle autorisée
    	 */
		Label label = new Label(mark, cout, father, who, coutEstimee);
		this.labels.put(who, label);
		return label;
	}

}
