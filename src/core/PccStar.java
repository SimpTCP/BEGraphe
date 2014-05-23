package core ;

import java.io.PrintStream;

import base.Readarg;

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

    public PccStar(Graphe graphe, PrintStream sortie, Sommet source, Sommet destination)
    {
    	super(graphe, sortie, source, destination);
    }
    
	@Override
    protected Label createLabelAndPut(Sommet who, float cout, Sommet father, boolean mark)
	{
    	float distance = (float) Graphe.distance(who.getLongitude(), who.getLatitude(), this.destination.getLongitude(), this.destination.getLatitude());
    	float coutEstimee = (distance-200) / (150/3.6f); // t = d/v (d en m, v en m/s)
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
