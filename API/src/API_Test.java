package API.src;

import java.util.ArrayList;

import CatalogManager.src.Value;

public class API_Test {
	public static void main(String arg[]) {
		ArrayList<Value> V = new ArrayList<>();
        V.add(new Value("number", 0, 0, true));
        V.add(new Value("b", 0, 0, true));
        V.add(new Value("name, 1, 20, true));
        
        ArrayList<Value> W = new ArrayList<>();
        W.add(new Value("123, 0, 0, true));
        W.add(new Value(456, 0, 0, true));
        W.add(new Value("Dan", 1, 20, true));
        try {
        	create_table("test", "number", V);
        	insert_table("test", W);
        } catch (Exception e) {
        	
        } finally {
        	
        }
	}
}
