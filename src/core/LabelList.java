package core;

import java.util.ArrayList;
import java.util.HashMap;

public class LabelList<K, V> extends HashMap<K, V> {
	public LabelList(){
		super();
	}
	/** on cherche a savoir si les sommets qu'on passe (suppose même composante connexe) sont à true
	 * on veut aussi, quand destinations vaut null : (donc on va vers tous) continuer tt le temps, donc on renvoit false
	 * @param liste : liste de sommet, 
	 **/
	public boolean areMark(ArrayList<K> liste){
		boolean ret = true;
		for(K s : liste){
				if(liste != null){
					if(s == null || !this.containsKey(s)){//si  notre hashmap ne contient pas s, ou si s est null, n'étudie pas s
						continue;
					}
					else if(!(((Label) this.get(s)).isMark()) && ((Label)this.get(s) !=null)){//si s est marqué  ou que s vaut null?, on continue l'algo(donc renvoit false)
						ret = false;
					}
				}else{
				ret = false;
				}
		}
		return ret;
	}
	
}
