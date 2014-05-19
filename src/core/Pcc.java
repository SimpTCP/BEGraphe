package core ;

import java.awt.Color;
import java.io.PrintStream;
import java.util.HashMap;

import base.Readarg;

public class Pcc extends Algo {

	protected Sommet origine;
	protected Sommet destination;
	private BinaryHeap<Label> tas;
	private HashMap<Sommet, Label> labels;

	public Pcc(Graphe gr, PrintStream sortie, Readarg readarg) {
		super(gr, sortie, readarg) ;

		do {
			System.out.println("Un truc qui va qq part pleaaase");
			this.origine = this.graphe.getSommets().get(readarg.lireInt("Numero du sommet d'origine ? "));
		}while(this.origine.getNombreRouteSortante() == 0);
		do {
			System.out.println("Un truc qui va qq part pleaaase");
			this.destination = this.graphe.getSommets().get(readarg.lireInt("Numero du sommet de destination ? "));
		}while(this.destination.getNombreRouteSortante() == 0);
		
		this.labels = new HashMap<Sommet, Label>();
		this.tas = new BinaryHeap<Label>();
	}

	public void run() {
		
		long cout;
		Sommet currentSommet;
		Label currentLabel;
		Sommet filsSommet;
		Label filsLabel;
		
		labels.put(origine, new Label(0, false, origine));
		labels.put(destination, new Label(-1, false, destination));
		this.labels.get(origine).setPadre(origine);
		
		tas.insert(this.labels.get(origine));
		while(!this.labels.get(destination).isMark() && !tas.isEmpty())
		{
			currentLabel = tas.deleteMin();
			currentSommet = currentLabel.getMoi();
			currentLabel.setMark(true);
			
			// Pour le sommet courrant, on prend chacun de ses fils
			for(Arc arc: currentSommet.getRoutesSortantes()){
				filsSommet = arc.getDestination();
				filsLabel = this.labels.get(filsSommet);
				cout = currentLabel.getCout() + arc.tempsParcours();
				
				if(filsLabel == null)
				{
					Label label = new Label(currentSommet, cout, filsSommet);
					filsLabel = label;
					this.labels.put(filsSommet, filsLabel);
					this.tas.insert(filsLabel);
				}
				else if (!filsLabel.isMark())
				{
					Label tmp = new Label();
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
			this.graphe.getDessin().drawLine(currentSommet.getLongitude(), currentSommet.getLatitude(), currentLabel.getPadre().getLongitude(), currentLabel.getPadre().getLatitude());
		}
		
		cout = labels.get(destination).getCout();
		if(cout == -1){
			System.out.println("C'est fini mon gars, on peut pas y aller la ou tu veux aller, essaye la Mongolfiere ou le MH370 !");
		}
		else{
			System.out.println("Ouais c'est cool tiens mon boudin, Cout de "+origine+" à "+destination+" : "+cout);
			currentSommet = destination;
			while(currentSommet!=origine){
				System.out.println(currentSommet);
				currentSommet = labels.get(currentSommet).getPadre();
			}
		}
	}

}
