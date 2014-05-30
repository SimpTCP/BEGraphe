package core ;

import java.awt.Color;
import java.io.PrintStream;
import java.util.ArrayList;

import test.Stats;
import base.Readarg;

public class Pcc extends Algo {

	protected Sommet origine;
	protected ArrayList<Sommet> destinations = null;
	private BinaryHeap<Label> tas;
	protected LabelList<Sommet, Label> labels;

	public Pcc(Graphe gr)
	{
		super(gr);
		this.askSommetsClick();
		this.labels = new LabelList<Sommet, Label>();
		this.tas = new BinaryHeap<Label>();
		
	}
	
	public Pcc(Graphe gr, PrintStream fichierSortie)
	{
		super(gr, fichierSortie);
		this.askSommetsClick();
		this.labels = new LabelList<Sommet, Label>();
		this.tas = new BinaryHeap<Label>();
	}
	
	public Pcc(Graphe gr, Readarg readarg) {
		super(gr, readarg) ;
		destinations = new ArrayList<Sommet>();
		do {
			System.out.println("Un truc qui va qq part pleaaase");
			this.origine = this.graphe.getSommets().get(readarg.lireInt("Numero du sommet d'origine ? "));
		}while(this.origine.getRoutesSortantes().size() == 0);
		do {
			System.out.println("Un truc qui va qq part pleaaase");
			this.destinations.add( this.graphe.getSommets().get(readarg.lireInt("Numero du sommet de destination ? ")));
		}while(this.destinations.get(0).getRoutesSortantes().size() == 0);
		
		this.labels = new LabelList<Sommet, Label>();
		this.tas = new BinaryHeap<Label>();
	}

	public Pcc(Graphe gr, PrintStream fichierSortie, Readarg readarg) {
		this(gr, readarg);
		this.sortie = fichierSortie;
		this.labels = new LabelList<Sommet, Label>();
	}
	
	public Pcc(Graphe graphe, PrintStream sortie, Sommet source, Sommet destination) {//Constructeur de Djikstra de 1 vers 1 ou de 1 vers tous si destination = null
		super(graphe, sortie);
		if(destination != null){
			destinations = new ArrayList<Sommet>();
			this.destinations.add(destination);//si destinations a un sommet : c'est du 1 vers 1
		}else{
			
			destinations = null;
		}
		this.origine = source;
		this.labels = new LabelList<Sommet, Label>();
		this.tas = new BinaryHeap<Label>();
	}
	public Pcc(Graphe graphe, PrintStream sortie, Sommet source, ArrayList<Sommet> destinations){//constructeur de djikstra de 1 vers n !! pour n vers 1 c'est dans le run
		super(graphe, sortie);
		this.destinations = destinations;
		this.origine = source;
		this.labels = new LabelList<Sommet, Label>();
	}

	private void askSommetsClick()
	{
		destinations = new ArrayList<Sommet>();
		do {
			System.out.println("Clique pour sommet origine...");
			this.origine = this.graphe.situerClick(true);
			System.out.println(this.origine);
		} while(this.origine.getRoutesSortantes().size() == 0);
		
		do {
			System.out.println("Clique pour sommet destination");
			this.destinations.add(this.graphe.situerClick(true));
			System.out.println(this.destinations.get(0));
		} while(this.destinations.get(0).getRoutesSortantes().size() == 0);
	}
	
	protected Label createLabelAndPut(Sommet who, float cout, Sommet father, boolean mark)
	{
		Label label = new Label(mark, cout, father, who, 0);
		this.labels.put(who, label);
		return label;
	}
	
	public Chemin run()
	{
		return this.run(0,0,null,null);
	}
	
	public Chemin run(int vitesse, float coutMax, ArrayList<Label> labelTrue, BinaryHeap<Label> tasRace) {//on run avec vitesse (normal si =0) on renvoit les sommets true dans labelTrue et on part de N si tasrace not null de origine sinon
		if (coutMax ==0){
			coutMax = Integer.MAX_VALUE;
		}
		int nbrSommetMark = 0;
		float cout;
		long start = System.currentTimeMillis();
		Sommet currentSommet;
		Label currentLabel = new Label();
		currentLabel.setCout(0);
		Sommet filsSommet;
		Label filsLabel;
		if(origine !=null){
			System.out.println(origine);
			this.createLabelAndPut(this.origine, 0, this.origine, false);
		}
		/*if(destinations == null){
			destinations = new ArrayList<Sommet>();
			destinations.add(new Sommet());
		}*/
		if(destinations !=null && destinations.size() ==1){//si on va vers 1
			this.createLabelAndPut(this.destinations.get(0), Integer.MAX_VALUE, null, false);//alors le cout de celui vers lequel on va vaut infini + gotas
		}else if(destinations !=null){
			for(Sommet s: destinations){//si on va vers n
				this.createLabelAndPut(s, Integer.MAX_VALUE, null, false);//on ajoute chaque sommet au tas avec un cout infini et une marque false
			}
		}else{// si on va vers tous 
			destinations = new ArrayList<Sommet>();
			destinations.add(new Sommet());
			this.createLabelAndPut(destinations.get(0), Integer.MAX_VALUE, null, false);//on ajoute pour destinations un truc qu'on pourra jamais atteindre 
		}
		if(tasRace ==null && origine ==null){//un tasRace non null signifie que l'on donne pleins de sommets de depart: N vers ? !!! mais si on a ni plein ni 1, on part de ou ? oulala
			System.out.println("Oulala");
		}
		else if(tasRace==null){//si tasRace == null, on part de 1, on suppose qu'on a origine, en avant guinguamp
			tas = new BinaryHeap<Label>();
			tas.insert(this.labels.get(origine));
		}else{// si on prends tasRace, on suppose qu'on part de N et que les marques de tasRace sont a false
			tas = tasRace;
			tas.print();
		}
		System.out.println("ICICICI?");
		while(!this.labels.areMark(destinations) && !tas.isEmpty() && currentLabel.getTotalCout() <=coutMax)//tant que tas non vide + plus petit que coutMax + il reste des false dans nos destinations
		{
			//if(tas.)
			currentLabel = tas.deleteMin();
			currentSommet = currentLabel.getMoi();
			if(labelTrue !=null){
				labelTrue.add(currentLabel);
			}
			currentLabel.setMark(true);
			nbrSommetMark++;
			// Pour le sommet courant, on prend chacun de ses fils
			for(Arc arc: currentSommet.getRoutesSortantes()){
				filsSommet = arc.getDestination();
				filsLabel = this.labels.get(filsSommet);
				cout = currentLabel.getCout() + arc.tempsParcours(vitesse);
				
				if(arc.getVitesse() == 0 || (arc.getVitesse()>=110 && vitesse>0))
				{
					continue;
				}
				
				if(filsLabel == null)
				{
					filsLabel = this.createLabelAndPut(filsSommet, cout, currentSommet, false);
					this.tas.insert(filsLabel);
				}
				else if (!filsLabel.isMark())
				{
					Label tmp = new Label();
					tmp.setCout(cout);
					tmp.setCoutEstimee(filsLabel.getCoutEstimee());
					
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
			//System.out.println(currentSommet);
			this.graphe.getDessin().setColor(Color.blue);
			this.graphe.getDessin().drawPoint(currentSommet.getLongitude(), currentSommet.getLatitude(), 2);
		}
		
		long stop = System.currentTimeMillis();
		if(destinations.size() ==1){
			cout = labels.get(destinations.get(0)).getCout();
			Chemin c = new Chemin();
			if(cout == Integer.MAX_VALUE){
				System.out.println("C'est fini mon gars, on peut pas y aller la ou tu veux aller, essaye la Mongolfiere ou le MH370 !");
			}
			else{
				System.out.println("Ouais c'est cool tiens mon boudin, Cout de "+origine+" à "+destinations.get(0)+" : "+cout);
				c.setSource(this.origine);
				c.setDestination(this.destinations.get(0));
				c.setIdCarte(this.graphe.getIdCarte());
				currentSommet = destinations.get(0);
				do {
					//System.out.println(" -> "+currentSommet.getEntierSommet());
					c.addSommetDebut(currentSommet);
					//this.log(currentSommet.toString());
					currentSommet = this.labels.get(currentSommet).getPadre();
				} while(currentSommet != origine);
				c.addSommetDebut(origine);
				this.log("");
				this.log(c.toString());
				c.dessinChemin(this.graphe.getDessin());
				this.log("");
				this.log("Executé en : "+(stop-start)+"ms");
				this.log("Nbr sommet visite : "+this.labels.size());
				this.log("Nbr sommet mark : "+nbrSommetMark);
				Stats s = new Stats(stop-start, this.labels.size(), nbrSommetMark);
				c.setStats(s);
			}
			
			return c;
		}
		else if (labelTrue != null){
			for(Sommet s: destinations){
				labelTrue.add(this.labels.get(s));
			}
		}
		return null;
	}
	
	public BinaryHeap<Label> getTas() {
		return tas;
	}

	public void setTas(BinaryHeap<Label> tas) {
		this.tas = tas;
	}

	public ArrayList<Sommet> getArround(float cout, Sommet depart){
		ArrayList<Sommet> retour = new ArrayList<Sommet>();
		
		return retour;
	}
	public void log(String str)
	{
		if(this.sortie != null)
		{
			this.sortie.println(str);
		}
		System.out.println("Log : "+str);
	}

	public Sommet getOrigine() {
		return origine;
	}

	public ArrayList<Sommet> getDestinations() {
		return destinations;
	}
	
	public void setOrigine(Sommet s)
	{
		this.origine = s;
	}

	public void setDestinations(ArrayList<Sommet> s)
	{
		this.destinations = s;
	}
	
}
