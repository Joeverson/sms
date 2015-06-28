package tools;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Dns {
	private Map<String,String> table = new HashMap<String,String>();
	
	public String getIp(String ip){
		if(this.table.containsKey(ip))
			return this.table.get(ip);
		
		return null;
	}
	
	public void newUser(String ip, String name){
		this.table.put(ip, name);
	}
	
	public boolean del(String ip){
		if(this.table.containsKey(ip)){
			this.table.remove(ip);
			return true;
		}			
			
		return false;
	}
	
	public String[] allNames(){
		String[] str = new String[this.table.size()];
		List<String> t = new ArrayList<String>();
		
		// pega os nomes dentro do array list
		for(String k : this.table.keySet())
			t.add(this.table.get(k));
			
		
		for(int i = 0; i < this.table.size(); i++){
			str[i] = this.table.get(t.get(i));
		}
		
		return str;
	}
}
