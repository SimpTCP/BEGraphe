package core ;

import java.awt.Color;
import java.io.* ;
import java.util.HashMap;

import base.Readarg ;

public class PccStar extends Pcc {

    public PccStar(Graphe gr, PrintStream sortie, Readarg readarg) {
    	super(gr, sortie, readarg) ;
    }
	public PccStar(Graphe gr)
	{
		super(gr);
		this.askSommetsClick();
		this.labels = new HashMap<Sommet, Label>();
		this.tas = new BinaryHeap<Label>();
		
	}
	public PccStar(Graphe gr, PrintStream fichierSortie)
	{
		super(gr, fichierSortie);
		this.askSommetsClick();
		this.labels = new HashMap<Sommet, Label>();
		this.tas = new BinaryHeap<Label>();
	}
	public PccStar(Graphe gr, Readarg readarg) {
		super(gr, readarg) ;

		do {
			System.out.println("Un truc qui va qq part pleaaase");
			this.origine = this.graphe.getSommets().get(readarg.lireInt("Numero du sommet d'origine ? "));
		}while(this.origine.getRoutesSortantes().size() == 0);
		do {
			System.out.println("Un truc qui va qq part pleaaase");
			this.destination = this.graphe.getSommets().get(readarg.lireInt("Numero du sommet de destination ? "));
		}while(this.destination.getRoutesSortantes().size() == 0);
		
		this.labels = new HashMap<Sommet, Label>();
		this.tas = new BinaryHeap<Label>();
	}

    public void run() {
    		
    		int nbrSommetMark = 0;
    		
    		long cout;
    		long start = System.currentTimeMillis();
    		Sommet currentSommet;
    		Label currentLabel;
    		Sommet filsSommet;
    		Label filsLabel;
    		
    		labels.put(origine, new Label(0, false, origine, destination, true));
    		labels.put(destination, new Label(-1, false, destination, destination, true));
    		this.labels.get(origine).setPadre(origine);
    		
    		tas.insert(this.labels.get(origine));
    		while(!this.labels.get(destination).isMark() && !tas.isEmpty())
    		{
    			currentLabel = tas.deleteMin();
    			
    			currentSommet = currentLabel.getMoi();
    			currentLabel.setMark(true);
    			nbrSommetMark++;
    			
    			// Pour le sommet courrant, on prend chacun de ses fils
    			for(Arc arc: currentSommet.getRoutesSortantes()){
    				filsSommet = arc.getDestination();
    				filsLabel = this.labels.get(filsSommet);
    				cout = currentLabel.getCout() + arc.tempsParcours();
    				
    				if(arc.getVitesse() == 0)
    				{
    					continue;
    				}
    				
    				if(filsLabel == null)
    				{
    					Label label = new Label(currentSommet, cout, filsSommet, destination, true);
    					filsLabel = label;
    					this.labels.put(filsSommet, filsLabel);
    					this.tas.insert(filsLabel);
    				}
    				else if (!filsLabel.isMark())
    				{
    					Label tmp = new Label(true, destination);
    					tmp.setCout(cout);
    					if(filsLabel.compareTo(tmp) == 1)
    					{
    						filsLabel.setCout(cout);
    						filsLabel.setPadre(currentSommet);
    						
    						if(!tas.contains(filsLabel))
    						{
    							tas.insert(filsLabel);
    						} else {
    							tas.update(filsLabel);
    						}
    						
    					}
    				}
    			}
    			this.graphe.getDessin().setColor(Color.blue);
    			this.graphe.getDessin().setWidth(1);
    			this.graphe.getDessin().drawPoint(currentSommet.getLongitude(), currentSommet.getLatitude(), 2);
    		}
    		
    		long stop = System.currentTimeMillis();
    		
    		cout = labels.get(destination).getCout();
    		if(cout == -1){
    			System.out.println("C'est fini mon gars, on peut pas y aller la ou tu veux aller, essaye la Mongolfiere ou le MH370 !");
    		}
    		else{
    			System.out.println("Ouais c'est cool tiens mon boudin, Cout de "+origine+" à "+destination+" : "+cout);
    			Chemin c = new Chemin();
    			c.setSource(this.origine);
    			c.setDestination(this.destination);
    			c.setIdCarte(this.graphe.getIdCarte());
    			currentSommet = destination;
    			while(currentSommet != origine){
    				c.addSommetDebut(currentSommet);
    				this.log(currentSommet.toString());
    				currentSommet = labels.get(currentSommet).getPadre();
    			}
    			this.log("");
    			this.log(c.toString());
    			c.dessinChemin(this.graphe.getDessin());
    			this.log("");
    			this.log("Executé en : "+(stop-start)+"ms");
    			this.log("Nbr sommet visite : "+this.labels.size());
    			this.log("Nbr sommet mark : "+nbrSommetMark);
    		}
    	
	//System.out.println("Run PCC-Star de " + zoneOrigine + ":" + origine + " vers " + zoneDestination + ":" + destination) ;

	// A vous d'implementer la recherche de plus court chemin A*
    }

}
