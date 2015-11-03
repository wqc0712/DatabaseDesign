import java.util.ArrayList;
import java.util.Arrays;

public class API {
	static API manager = new API();
	private static RM_Manager rm = RM_Manager.getInstance();
	private static CatalogManager cm = CatalogManager.getInstance();
	
	private static byte[] _att;
	private static API Instant = new API();
	public static API getInstance() { return manager;}

	/*table_Name requires */
	public void create_table(String table_Name, String primary_Key, ArrayList<Value> attr) throws Exception{
		if (!cm.isTableExist(table_Name)) {
			try {
				cm.addTable(table_Name, attr.size(), attr, primary_Key);
				rm.createFile(table_Name, attr.size() * 256);
			} catch  (Exception e) {
				throw e;
			} finally {
				/*useless*/
			}
		} else
			throw new Exception("Error: the table name has been already used.");
	}

	public void drop_table(String table_Name) throws Exception{
		if (cm.isTableExist(table_Name)) {
			try {
				rm.destoryFile(table_Name);
				CatalogManager.getInstance().dropTable(table_Name);
			} catch  (Exception e) {
				throw e;
			} finally {
				/*useless*/
			}
		} else
			throw new Exception("Error: do not have this table.");
	}
	
	public void select_from (String table_Name, ArrayList<CondExpr> attr) throws Exception{
		if (cm.isTableExist(table_Name)) {
			try {
				RM_FileHandler rmf = rm.openFile(table_Name);
				if (attr.size() == 1) {
					if (cm.TestAttr(table_Name, attr.get(0).getID(), attr.get(0).getValue().getType(), attr.get(0).getValue().getLength())){ /*The last parameter may have some problem.*/
						Constant.TYPE type = null;
						int length = 0;
						switch (attr.get(0).getValue().getType()) {
						case 0: type = Constant.TYPE.INT; length = 4;break;
						case 1: type = Constant.TYPE.STRING; length = attr.get(0).getValue().getLength();break;	/*This may have some problem about attrbute's length. */
						case 2: type = Constant.TYPE.DOUBLE; length = 8;break;
						}
						
						int offset = cm.findOffset(table_Name, attr.get(0).getID());
						
						Constant.COMP_OP op = null;
						switch (attr.get(0).getOP()) {
						case "=": op = Constant.COMP_OP.EQ_OP; break;
						case ">": op = Constant.COMP_OP.GT_OP; break;
						case "<": op = Constant.COMP_OP.LT_OP; break;
						case "<>": op = Constant.COMP_OP.NE_OP; break;
						case ">=": op = Constant.COMP_OP.GT_OP; break;
						case "<=": op = Constant.COMP_OP.LE_OP; break;
						}
						ArrayList<Value> temp = new ArrayList<>();
						temp.add(attr.get(0).getValue());
						RM_FileScan rmfs = new RM_FileScan(rmf, type, length, offset, op, String2Byte(temp));
						RM_Record rmr = rmfs.getNextRec();
						while (rmr != null) {
							System.out.println(rmr.getData());
							rmr = rmfs.getNextRec();
						}
					} else 
						throw new Exception("Error: the where condition is wrong!");
				} else {
					/*	Use Index...	*/
				}
				
			} catch (Exception e) {
				
			} finally {
				
			}
		} else 
			throw new Exception("do not have this table");
	}

	public void insert_table(String table_Name, ArrayList<Value> attr) throws Exception{
		if (cm.isTableExist(table_Name)) {
			boolean judge = true;
			for (int i = 0; i < attr.size(); i++)
			if (!cm.TestAttrOnOrder(table_Name, i, attr.get(i).getType(), attr.get(i).getLength())) {
				judge = false;
				break;
			}
			if (judge) {
				try {
					RM_FileHandler rmf = rm.openFile(table_Name);
					rmf.insertRec(String2Byte(attr));
					rm.closeFile(rmf);
				} catch (Exception e) {
					throw e;
				} finally {
					/*useless*/
				}
			} else
				throw new Exception("Error: attributes are not correspongding to the table.");
		} else 
			throw new Exception("Error: do not have this table.");
	}
	
	public void delete_record(String table_Name, ArrayList<CondExpr> attr) throws Exception{
		if (cm.isTableExist(table_Name)) {
				try {
					RM_FileHandler rmf = rm.openFile(table_Name);
					boolean judge = true;
					for (int i = 0; i < attr.size(); i++)
					if (!cm.TestAttr(table_Name, attr.get(i).getID(),attr.get(i).getValue().getType(),attr.get(i).getValue().getLength())) {
						judge = false;
						break;
					}
					if (judge) {
						if (attr.size() == 1) {
							if (cm.TestAttr(table_Name, attr.get(0).getID(), attr.get(0).getValue().getType(), attr.get(0).getValue().getLength())){ /*The last parameter may have some problem.*/
								Constant.TYPE type = null;
								int length = 0;
								switch (attr.get(0).getValue().getType()) {
								case 0: type = Constant.TYPE.INT; length = 4;break;
								case 1: type = Constant.TYPE.STRING; length = attr.get(0).getValue().getLength();break;	/*This may have some problem about attrbute's length. */
								case 2: type = Constant.TYPE.DOUBLE; length = 8;break;
								}
								
								int offset = cm.findOffset(table_Name, attr.get(0).getID());
								
								Constant.COMP_OP op = null;
								switch (attr.get(0).getOP()) {
								case "=": op = Constant.COMP_OP.EQ_OP; break;
								case ">": op = Constant.COMP_OP.GT_OP; break;
								case "<": op = Constant.COMP_OP.LT_OP; break;
								case "<>": op = Constant.COMP_OP.NE_OP; break;
								case ">=": op = Constant.COMP_OP.GT_OP; break;
								case "<=": op = Constant.COMP_OP.LE_OP; break;
								}
								ArrayList<Value> temp = new ArrayList<>();
								temp.add(attr.get(0).getValue());
								RM_FileScan rmfs = new RM_FileScan(rmf, type, length, offset, op, String2Byte(temp));
								RM_Record rmr = rmfs.getNextRec();
								while (rmr != null) {
									rmf.deleteRec(rmr.getRid());
									rmr = rmfs.getNextRec();
								}
							} else 
								throw new Exception("Error: the where condition is wrong!");
						} else {
							/*	Use Index...	*/
						}
					} else
						throw new Exception("Error: attributes are not corresponding to the table.");
				} catch (Exception e) {
					throw e;
				} finally {
					/*useless*/
				}
		} else
			throw new Exception("Error: do not have this table. "); 
	}
	
	private static byte[] String2Byte(ArrayList<Value> attr) {
		byte[] a;
		a = new byte[attr.size() * 256];
		for (int i = 0; i < attr.size(); i++){
			String u = attr.get(i).getData();
			switch (attr.get(i).getType()) {
			case 0:{
				int uu = Integer.parseInt(u); 
				byte[] b = PF_Manager.intTobyteArray(uu);
				for (int j = 0; j < b.length; j++)
					a[i * 256 + j] = b[j];
				break;
			}
			case 1:{
				byte[] b = u.getBytes();
				for (int j = 0; j < b.length; j++)
					a[i * 256 + j] = b[j];
				break;
			}
			case 2:{
				double uu = Double.parseDouble(u); 
				byte[] b = PF_Manager.doubleToByteArray(uu);
				for (int j = 0; j < b.length; j++)
					a[i * 256 + j] = b[j];
				break;
			}
			}
		}
		return a;
	}
}
