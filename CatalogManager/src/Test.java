import java.util.ArrayList;

/**
 * Created by tom on 15/10/31.
 */
public class Test {
    public static void main(String arg[]) {
        ArrayList<Value> V = new ArrayList<>();
        V.add(new Value("a",1,12,false));
        V.add(new Value("b",0,0,true));
        try {
            CatalogManager.getInstance().addTable("T", 2, V, "b");
            CatalogManager.getInstance().WriteBack();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("Wrong!");
        }
    }
}
