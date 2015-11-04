/**
 * Created by tom on 15/10/26.
 */
public class Value {
    private String Data;						/*	The data.	*/
    private int Type;							/*	The type of the data. */
    private int Length;							/*	The maximum length of the data if its type is string.	*/
    private Boolean Unique;						/*	Weather this attrbute is unique.	*/
    											/*	The primary key must be unique, if not error happens!!!	*/
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

    public void setUnique() {
        Unique = true;
    }

    public Boolean IsUnique(){
        return Unique;
    }
}
