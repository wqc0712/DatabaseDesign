import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by tom on 15/10/26.
 */
public class Context {

    private static Context Instance = new Context();
    public static Context getInstance() {
        return Instance;
    }
    private Context(){
    }


    private String Method;
    private String TableID;
    private String IndexID;
    private String ListID;
    private Boolean Index;
    private String PK;
    private Boolean Error;
    private ArrayList<CondExpr> CondTable = new ArrayList<CondExpr>();
    private int CondVisit= 0;
    private ArrayList<Value> ValueTable = new ArrayList();
    private int ValueVisit = 0;

    public void clean(){
        Method = "";
        TableID = "";
        IndexID = "";
        ListID = "";
        Index = false;
        PK = "";
        Error = false;
        CondTable = new ArrayList<>();
        CondVisit = 0;
        ValueTable = new ArrayList<>();
        ValueVisit = 0;
    }

    public void setMethod(String key){
        if (key.equals("")) {
            Method = new String("Error");
        } else {
            Method = new String(key);
        }
    }
    public String getMethod(){
        return Method;
    }

    public void setError(Boolean B) {
        Error = B;
    }

    public Boolean getError() {
        return Error;
    }

    public void setTableID(String key){
        TableID = new String(key);
    }

    public String getTableID(){
        return TableID;
    }

    public void setIndexID(String key){
        IndexID = new String(key);
    }

    public String getIndexID(){
        return IndexID;
    }

    public void setListID(String key){
        ListID = new String(key);
    }

    public String getListID(){
        return ListID;
    }

    public void setPK(String key){
        PK = new String(key);
    }

    public String getPK(){
        return PK;
    }

    public void addCond(String A,String B, String C, int D, int E){
        CondExpr cond = new CondExpr(A,B,C,D,E);
        CondTable.add(cond);
    }

    public boolean isCondEmpty(){
        return CondTable.isEmpty();
    }

    public CondExpr getCond(){
        CondExpr temp;
        if (CondTable.size() > CondVisit){
            temp = CondTable.get(CondVisit);
            CondVisit = CondVisit+1;
            return temp;
        } else {
            return null;
        }
    }

    public Value getValue(){
        Value temp;
        if (ValueTable.size() > ValueVisit) {
            temp = ValueTable.get(ValueVisit);
            ValueVisit = ValueVisit + 1;
            return temp;
        } else {
            return null;
        }
    }

    public void addValue(String S, int A, int B, Boolean C){
        Value temp = new Value(S,A,B,C);
        ValueTable.add(temp);
    }

    public void setIndex(Boolean B){
        Index = B;
    }

    public Boolean IsIndex() {
        return Index;
    }

    public boolean isValueEmpty() { return ValueTable.isEmpty();}



}
