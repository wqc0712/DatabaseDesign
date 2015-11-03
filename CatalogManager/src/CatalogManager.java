

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by tom on 15/10/30.
 */
public class CatalogManager {
    private ArrayList<Index> I;
    private ArrayList<Table> T;
    private PF_FileHandler PF;
    private int TableNum = 0;
    private int IndexNum= 0;
    private int MaxIndex = 0;
    private int MaxTable = 0;
    private static CatalogManager Instant = new CatalogManager();
    public static CatalogManager getInstance() { return Instant;}
    private CatalogManager(){
        I = new ArrayList<>();
        T = new ArrayList<>();
        try {
            PF_Manager.creatFile("Catalog");
            PF = PF_Manager.getInstance().openFile("Catalog");
            System.out.println("Create Success");
        } catch (FileExistExpection e) {
            try {
                PF = PF_Manager.getInstance().openFile("Catalog");
            } catch (Exception err) {
                System.err.println("Error in open file Catalog!");
            }
        } catch (Exception error) {
            System.err.println("Error in open file Catalog!");
        }
        BufferBlock B = PF.getFirstPage();
        if (B != null) {

            int BlockIndex = 1;
            byte[] Data = B.getData();
            byte[] Temp;
            int Where = 0;
            Temp = Arrays.copyOfRange(Data,Where,Where+3);
            TableNum = PF_Manager.byteArrayToint(Temp);
            Where = 4;
            for (int i = 0;i < TableNum;i++) {
                if (Where+264 > 8192) {
                    try {
                        B = PF.getBlock(BlockIndex);
                        BlockIndex = BlockIndex + 1;
                        Data = B.getData();
                    } catch (Exception e) {
                        System.err.println("Error when get BlockData.");
                    }
                    Where = 0;
                }
                int id = PF_Manager.byteArrayToint(Arrays.copyOfRange(Data,Where,Where+3));
                Where = Where + 4;
                Temp = Arrays.copyOfRange(Data,Where,Where+255);
                String name = ByteToString(Temp);
                Where = Where + 256;
                int AttrNum;
                AttrNum = PF_Manager.byteArrayToint(Arrays.copyOfRange(Data,Where,Where+3));
                Where = Where + 4;
                Table Ttemp = new Table(id,name,AttrNum);
                for (int j = 0;j < AttrNum;j++) {
                    if (Where +268 > 8192) {
                        try {
                            B = PF.getBlock(BlockIndex);
                            BlockIndex = BlockIndex + 1;
                            Data = B.getData();
                        } catch (Exception e) {
                            System.err.println("Error when get BlockData.");
                        }
                        Where = 0;
                    }
                    String Sname;
                    int type;
                    int length;
                    int uni;
                    Boolean U;
                    Temp = Arrays.copyOfRange(Data,Where,Where+255);
                    Sname = ByteToString(Temp);
                    Where = Where+ 256;
                    type = PF_Manager.byteArrayToint(Arrays.copyOfRange(Data,Where,Where+3));
                    Where = Where + 4;
                    length = PF_Manager.byteArrayToint(Arrays.copyOfRange(Data,Where,Where+3));
                    Where = Where + 4;
                    uni = PF_Manager.byteArrayToint(Arrays.copyOfRange(Data,Where,Where+3));
                    if (uni == 1) U = true; else U = false;
                    Where = Where + 4;
                    Ttemp.addAttr(Sname,type,length,U);
                }
                T.add(Ttemp);
            }
            if (Where + 4 > 8192) {
                try {
                    B = PF.getBlock(BlockIndex);
                    BlockIndex = BlockIndex + 1;
                    Data = B.getData();
                } catch (Exception e) {
                    System.err.println("Error when get BlockData.");
                }
                Where = 0;
            }
            IndexNum = PF_Manager.byteArrayToint(Arrays.copyOfRange(Data,Where,Where+3));
            Where = Where + 4;
            for (int i = 0; i < IndexNum;i++) {
                if (Where + 520 > 8192) {
                    try {
                        B = PF.getBlock(BlockIndex);
                        BlockIndex = BlockIndex + 1;
                        Data = B.getData();
                    } catch (Exception e) {
                        System.err.println("Error when get BlockData.");
                    }
                    Where = 0;
                }
                int ID;
                String name;
                int TableID;
                String key;
                ID = PF_Manager.byteArrayToint(Arrays.copyOfRange(Data,Where,Where+3));
                Where = Where + 4;
                Temp = Arrays.copyOfRange(Data,Where,Where+255);
                name = ByteToString(Temp);
                Where = Where + 256;
                TableID = PF_Manager.byteArrayToint(Arrays.copyOfRange(Data,Where,Where+3));
                Where = Where + 4;
                Temp = Arrays.copyOfRange(Data,Where,Where+255);
                key = ByteToString(Temp);
                Where = Where + 256;
                I.add(new Index(ID,name,TableID,key));
            }
            try {
                PF_Manager.getInstance().closeFile(PF);
            } catch (Exception e) {

            }
            if (IndexNum > 0) MaxIndex = I.get(IndexNum-1).getID()+1;
            if (TableNum > 0) MaxTable = T.get(TableNum-1).getID()+1;
        }
    }

    public void WriteBack (){
        System.out.println("finalize");
        try {
            PF_Manager.destoryFile("Catalog");
            PF_Manager.creatFile("Catalog");
            PF = PF_Manager.getInstance().openFile("Catalog");
        } catch (Exception err) {
            System.err.println("Error in open file Catalog!");
        }

        int Pagenum = 0;
        int Where = 0;
        byte[] Block = new byte[8192];
        byte[] Temp;
        Temp = PF_Manager.intTobyteArray(TableNum);
        System.arraycopy(Temp, 0, Block, Where, 4);
        Where = Where + 4;
        for (int i = 0;i < TableNum;i++) {
            if (Where + 264 > 8192) {
                try {
                    PF.addPage(Block);
                    Block = new byte[8192];
                    Where = 0;
                } catch (Exception e) {
                    System.err.println("Can't Write Back to Catalog!");
                }
            }
            Table TTemp = T.get(i);
            if (TTemp == null) break;
            Temp = PF_Manager.intTobyteArray(TTemp.getID());
            System.arraycopy(Temp,0,Block,Where,4);
            Where = Where + 4;
            Temp = StringToByte(TTemp.getName());
            System.arraycopy(Temp,0,Block,Where,256);
            Where = Where + 256;
            Temp = PF_Manager.intTobyteArray(TTemp.getLength());
            System.arraycopy(Temp,0,Block,Where,4);
            Where = Where + 4;
            for (int j = 0;j <= TTemp.getLength();j++) {
                if (Where + 268 > 8192) {
                    try {
                        PF.addPage(Block);
                        Block = new byte[8192];
                        Where = 0;
                    } catch (Exception e) {
                        System.err.println("Can't Write Back to Catalog!");
                    }
                }
                Value Vtemp = TTemp.getAttr(j);
                if (Vtemp == null) break;
                Temp = StringToByte(Vtemp.getData());
                System.arraycopy(Temp,0,Block,Where,256);
                Where = Where + 256;
                Temp = PF_Manager.intTobyteArray(Vtemp.getType());
                System.arraycopy(Temp,0,Block,Where,4);
                Where = Where + 4;
                Temp = PF_Manager.intTobyteArray(Vtemp.getLength());
                System.arraycopy(Temp,0,Block,Where,4);
                Where = Where + 4;
                int utemp;
                if (Vtemp.IsUnique() == true) utemp = 1; else utemp= 0;
                Temp = PF_Manager.intTobyteArray(utemp);
                System.arraycopy(Temp,0,Block,Where,4);
                Where = Where + 4;
            }
        }
        if (Where + 4 > 8192) {
            try {
                PF.addPage(Block);
                Block = new byte[8192];
                Where = 0;
            } catch (Exception e) {
                System.err.println("Can't Write Back to Catalog!");
            }
        }
        Temp = PF_Manager.intTobyteArray(IndexNum);
        System.arraycopy(Temp,0,Block,Where,4);
        Where = Where + 4;
        for (int i = 0;i < IndexNum;i++) {
            if (Where + 520 > 8192) {
                try {
                    PF.addPage(Block);
                    Block = new byte[8192];
                    Where = 0;
                } catch (Exception e) {
                    System.err.println("Can't Write Back to Catalog!");
                }
            }
            Index Itemp = I.get(i);
            Temp = PF_Manager.intTobyteArray(Itemp.getID());
            System.arraycopy(Temp,0,Block,Where,4);
            Where = Where + 4;
            Temp = StringToByte(Itemp.getIndexName());
            System.arraycopy(Temp,0,Block,Where,256);
            Where = Where + 256;
            Temp = PF_Manager.intTobyteArray(Itemp.getTableID());
            System.arraycopy(Temp,0,Block,Where,4);
            Where = Where + 4;
            Temp = StringToByte(Itemp.getKeyName());
            System.arraycopy(Temp,0,Block,Where,256);
            Where = Where + 256;
        }

        if (Where > 0) {
            try {
                System.out.println(Block);
                PF.addPage(Block);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Can't Write Back to Catalog!");
            }
        }
        try {
            PF_Manager.getInstance().closeFile(PF);
        } catch (Exception e) {
            System.err.println("Can't close Catalog!");

        }
    }

    public void addIndex(String IndexName, String TableName, String KeyName) throws Exception {
        Table Temp = null;
        for (int i = 0;i < TableNum;i++) {
            if (T.get(i).getName().equals((TableName))) {
                Temp = T.get(i);
                break;
            }
        }
        for (int i = 0;i < IndexNum;i++) {
            if (I.get(i).getIndexName().equals(IndexName)) {
                throw new IndexAreadyExist();
            }
        }
        if (Temp == null) {
            throw new TableNoExist();
        }
        int tableID = Temp.getID();
        if (Temp.getAttr(KeyName) == null) {
            throw new AttrNoExist();
        }
        IndexNum = IndexNum+1;
        I.add(new Index(MaxIndex,IndexName,tableID,KeyName));
        MaxIndex = MaxIndex+1;
    }

    public void dropIndex(String IndexName) throws Exception {
        int i = 0;
        for (i = 0; i < IndexNum;i++) {
            if (I.get(i).getIndexName() == IndexName) break;
        }
        if (i >= IndexNum) {
            throw new IndexNoExist();
        }
        I.remove(i);
        IndexNum = IndexNum-1;
    }

    public void dropTable(String TableName) throws Exception {
        int i = 0;
        for (i = 0;i < TableNum;i++) {
            if (T.get(i).getName() == TableName) break;
        }
        if (i >= TableNum) {
            throw new TableNoExist();
        }
        int TableID = T.get(i).getID();
        T.remove(i);
        TableNum = TableNum-1;
        i = 0;
        while (i < IndexNum) {
            if (I.get(i).getID() == TableID) {
                I.remove(i);
                IndexNum = IndexNum - 1;
            }
        }

    }

    public void addTable(String TableName,int num, ArrayList<Value> attr,String PK) throws Exception {
        // Must Run isTableExist First
        TableNum = TableNum+1;
        T.add(new Table(MaxTable,TableName,num,attr));
        addIndex("_"+TableName,TableName,PK);
        MaxTable = MaxTable + 1;
    }

    public boolean isTableExist(String TableName) {
        for (int i = 0;i < TableNum;i++) {
            if (T.get(i).getName() == TableName) {
                return true;
            }
        }
        return false;
    }

    public boolean TestAttr(String TableName,String AttrName,int type,int length) {
        for (int i = 0;i < TableNum;i++) {
            if (T.get(i).getName() == TableName){
                Table Temp = T.get(i);
                Value VTemp = Temp.getAttr(AttrName);
                if (VTemp == null) return false;
                if (VTemp.getType() == type && VTemp.getLength() >= length) return true;
                return false;
            }
        }
        return false;
    }

    public boolean TestAttrOnOrder(String TableName, int num, int type, int length) {
        for (int i = 0;i < TableNum;i++) {
            if (T.get(i).getName() == TableName) {
                Table Temp = T.get(i);
                Value VTemp = Temp.getAttr(num);
                if (VTemp == null) return false;
                if (VTemp.getType() == type && VTemp.getLength() >= length) return true;
            }
        }
        return false;
    }

    public boolean isIndexExist(String IndexName) {
        for (int i = 0;i < IndexNum;i++) {
            if (I.get(i).getIndexName() == IndexName) {
                return true;
            }
        }
        return false;
    }

    public boolean isIndexOnkey(String TableName, String KeyName) {
        int ID = GetTableInformation(TableName).getType();
        for (int i = 0;i < IndexNum;i++) {
            if (I.get(i).getTableID() == ID && I.get(i).getKeyName() == KeyName) {
                return true;
            }
        }
        return false;
    }

    public String GetIndex(String TableName,String Key) throws Exception{
        Value V = GetTableInformation(TableName);
        if (V == null) throw new TableNoExist();
        for (int i = 0;i< IndexNum;i++) {
            if (I.get(i).getTableID() == V.getType() && I.get(i).getKeyName() == Key) {
                return I.get(i).getIndexName();
            }
        }
        return "_"+TableName;
    }



    public boolean findAttr(String TableName,String AttrName) {
        for (int i = 0;i < TableNum;i++) {
            if (T.get(i).getName() == TableName) {
                return true;
            }
        }
        return false;
    }

    public Value GetTableInformation(String TableName) {
        Value Vtemp = null;
        for (int i = 0;i < TableNum;i++) {
            if (T.get(i).getName() == TableName) {
                Vtemp = new Value(T.get(i).getName(),T.get(i).getID(),T.get(i).getLength(),false);
                // Data -> TableName
                // Type -> TableID
                // Length -> AttrNumber
                // UNI -> false
            }
        }
        return Vtemp;
    }

    public byte[] StringToByte(String s){
        String Temp = new String(s);
        int i = Temp.length();
        while (i < 256) {
            Temp = Temp + '*';
            i = i + 1;
        }
        return Temp.getBytes();

    }
    public String ByteToString(byte[] b) {
        String s = new String(b);
        s.replace("*","");
        return s;
    }

}

class Table {
    private ArrayList<Value> attr;
    private String name;
    private int ID;
    private int length;

    public Table(int a,String b,int c) {
        ID = a;
        name = b;
        length = c;
    }

    public Table(int a,String B,int c,ArrayList<Value> d){
        attr = d;
        ID = a;
        name = B;
        length = c;
    }

    public void addAttr(String a,int b,int c,Boolean d) {
        Value temp = new Value(a,b,c,d);
        attr.add(temp);
    }

    public Value getAttr(String name){
        for (int i = 0;i < attr.size();i++) {
            if (attr.get(i).getData() == name)
                return attr.get(i);
        }
        return null;
    }

    public Value getAttr(int i) {
        if (i >= attr.size()) return null;
        return attr.get(i);
    }

    public int getID() {
        return ID;
    }

    public int getLength() {
        return length;
    }

    public String getName() {
        return name;
    }
}

class Index{
    private String IndexName;
    private String KeyName;
    private int ID;
    private int TableID;

    public Index(int a,String b,int c,String d) {
        IndexName = b;
        KeyName = d;
        ID = a;
        TableID = c;
    }

    public int getID() {
        return ID;
    }

    public int getTableID() {
        return TableID;
    }

    public String getIndexName() {
        return IndexName;
    }

    public String getKeyName() {
        return KeyName;
    }

}