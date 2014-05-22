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

    @Override
    protected Label createLabelAndPut(Sommet who, double cout, Sommet father, boolean mark)
	{
    	double coutEstimee = Graphe.distance(who.getLongitude(), who.getLatitude(), this.destination.getLongitude(), this.destination.getLatitude());
    	coutEstimee /= (130/3.6); // t = d/v (d en m, v en m/s)
		Label label = new Label(father, cout, who, mark, coutEstimee);
		this.labels.put(who, label);
		return label;
	}

}
