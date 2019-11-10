import java.util.HashMap;
import java.util.Hashtable;

public class Hash {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String,Integer> hm= new HashMap<>();
		hm.put("A", 1);
		hm.put("b", 2);
		hm.containsKey("A");
		hm.keySet();
		hm.containsValue(1);
		hm.values();
		
		Hashtable<String, Integer> ht= new Hashtable<>();
		ht.put("C", 3);
		ht.put("D", 4);
		ht.put("Z", 5);
		ht.put("A", 6);
		ht.put("a", 7);
		ht.put("m", 5);
		for(String s:ht.keySet()) System.out.println(s);
		for(Integer s:ht.values()) System.out.println(s);
		System.out.print(ht);
	}

}
