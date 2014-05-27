package core;

import java.util.ArrayList;
import java.util.HashMap;

public class LabelList<K, V> extends HashMap<K, V> {
	public LabelList(){
		super();
	}
	
	public boolean areMark(ArrayList<K> liste){
		boolean ret = true;
		for(K s : liste){
				if(liste != null){
					if(s == null || !this.containsKey(s)){
						continue;
					}
					else if(!(((Label) this.get(s)).isMark()) && ((Label)this.get(s) !=null)){
						ret = false;
					}
				}else{
				ret = false;
				}
		}
		return ret;
	}
	
}
