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
		if(this.destinations.size() ==1){
			float distance = (float) Graphe.distance(who.getLongitude(), who.getLatitude(), this.destinations.get(0).getLongitude(), this.destinations.get(0).getLatitude());
			float coutEstimee = (distance) / (this.graphe.getVitesseMax()/3.6f); // t = d/v (d en m, v en m/s)
    	//coutEstimee = 0;
    	/*
    	 * Le coutEstimee est une borne inférieure du coût réel :
    	 * - la distance est plus petite
    	 * - la vitesse est plus grande que celle autorisée
    	 */
			Label label = new Label(mark, cout, father, who, coutEstimee);
			this.labels.put(who, label);
			return label;
		}
		return null;
	}

}
