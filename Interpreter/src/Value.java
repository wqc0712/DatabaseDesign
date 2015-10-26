/**
 * Created by tom on 15/10/26.
 */
public class Value {
    private String Data;
    private int Type;
    private int Length;
    private Boolean Unique;

    public Value(String A,int B,int C,Boolean D) {
        Data = A;
        Type = B;
        Length = C;
        Unique = D;
    }

    public Value(Value A) {
        Data = A.getData();
        Type = A.getType();
        Length = A.getLength();
        Unique = A.Unique;
    }

    public String getData(){
        return Data;
    }

    public int getType(){
        return Type;
    }

    public int getLength(){
        return Length;
    }

    public Boolean IsUnique(){
        return Unique;
    }
}
