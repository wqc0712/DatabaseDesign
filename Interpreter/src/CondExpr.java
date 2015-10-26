/**
 * Created by tom on 15/10/26.
 */
public class CondExpr {
    private String ID;
    private String OP;
    private Value value;

    public CondExpr(String A,String B, String C, int D, int E) {
        ID = new String(A);
        OP = new String(B);
        value = new Value(C,D,E,false);
    }

    public CondExpr(CondExpr C){
        ID = new String(C.getID());
        OP = new String(C.getOP());
        value = new Value(C.getValue());
    }

    public Value getValue(){
        return value;
    }

    public String getOP(){
        return OP;
    }

    public String getID(){
        return ID;
    }
}
